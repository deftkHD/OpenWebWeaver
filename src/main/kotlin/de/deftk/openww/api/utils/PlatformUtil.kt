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

object PlatformUtil {

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

    fun urlEncodeUTF8(param: String): String {
        return URLEncoder.encode(param, "UTF-8")
    }

    fun base64EncodeToString(data: ByteArray): String {
        return Base64.getEncoder().encodeToString(data)
    }

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