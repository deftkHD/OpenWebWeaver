package de.deftk.openww.api.utils

import de.deftk.openww.api.response.ApiResponse
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.security.MessageDigest
import java.util.*
import javax.net.ssl.HttpsURLConnection

/**
 * Platform specific code (networking, crypto, string encoding)
 * Code of this kind should be placed inside this file to allow an easy port of this library to other platforms
 * by using kotlin-multiplatform
 */
object PlatformUtil {

    /**
     * Perform a post request to the given url
     * @param requestUrl: Target url
     * @param timeout: Request timeout
     * @param contentType: HTTP content type header attribute
     * @param data: Encoded POST data
     * @return: Wrapped API response object
     */
    suspend fun postRequest(requestUrl: String, timeout: Int, contentType: String, data: ByteArray): ApiResponse {
        val url = URL(requestUrl)
        val connection = if (requestUrl.startsWith("https://")) url.openConnection() as HttpsURLConnection else url.openConnection() as HttpURLConnection
        connection.connectTimeout = timeout
        connection.requestMethod = "POST"
        connection.addRequestProperty("Content-Type", contentType)
        connection.doInput = true
        connection.doOutput = true

        connection.outputStream.write(data)
        connection.outputStream.flush()
        connection.outputStream.close()

        val reader = BufferedReader(InputStreamReader(connection.inputStream, "UTF-8"))
        val sb = StringBuilder()
        while (true) {
            val ln = reader.readLine() ?: break
            sb.append("$ln\n")
        }
        return ApiResponse(sb.toString(), connection.responseCode)
    }

    /**
     * Encode illegal characters inside url
     * @param param: Raw url
     * @return: Encoded/escaped url
     */
    fun urlEncodeUTF8(param: String): String {
        return URLEncoder.encode(param, "UTF-8")
    }

    /**
     * Encode data into base64 string
     * @param data: Data to encode
     * @return: Encoded base64 string
     */
    fun base64EncodeToString(data: ByteArray): String {
        return Base64.getEncoder().encodeToString(data)
    }

    /**
     * @return: sha256 hash of input string
     */
    fun sha256Hash(str: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        digest.reset()
        digest.update(str.toByteArray(Charsets.UTF_8))
        val formatter = Formatter()
        digest.digest().forEach { byte ->
            formatter.format("%02x", byte)
        }
        return formatter.toString()
    }

}