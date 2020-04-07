package de.deftk.lonet.api

import de.deftk.lonet.api.model.User
import de.deftk.lonet.api.request.ApiRequest
import de.deftk.lonet.api.request.AuthRequest
import de.deftk.lonet.api.response.ApiResponse
import de.deftk.lonet.api.response.ResponseUtil
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import javax.net.ssl.HttpsURLConnection

object LoNet {

    fun login(username: String, password: String, createTrust: Boolean = false): User {
        val responsibleHost = getResponsibleHost(username)
        val authRequest = AuthRequest(responsibleHost)
        authRequest.addLoginPasswordRequest(username, password)
        if (createTrust) {
            authRequest.addSetFocusRequest("trusts")
            authRequest.addRegisterMasterRequest()
        }
        authRequest.addGetInformationRequest()
        val response = performJsonApiRequest(authRequest)
        ResponseUtil.checkSuccess(response.toJson())
        return User(
            username,
            if (createTrust) ResponseUtil.getSubResponseResultByMethod(response.toJson(), "register_master")
                .get("trust").asJsonObject.get("token").asString else password,
            responsibleHost,
            response
        )
    }

    fun loginToken(username: String, token: String, removeTrust: Boolean = false): User {
        val responsibleHost = getResponsibleHost(username)
        val nonceRequest = AuthRequest(responsibleHost)
        nonceRequest.addGetNonceRequest()
        val nonceResponse = performJsonApiRequest(nonceRequest)
        val nonceJson =
            nonceResponse.toJson().asJsonArray.get(0).asJsonObject.get("result").asJsonObject.get("nonce").asJsonObject
        val nonceId = nonceJson.get("id").asString
        val nonceKey = nonceJson.get("key").asString

        val authRequest = AuthRequest(responsibleHost)
        authRequest.addLoginNonceRequest(username, token, nonceId, nonceKey)
        if (removeTrust) {
            authRequest.addSetFocusRequest("trusts")
            authRequest.addUnregisterMasterRequest()
        }
        authRequest.addGetInformationRequest()
        val response = performJsonApiRequest(authRequest)
        ResponseUtil.checkSuccess(response.toJson())
        return User(username, token, responsibleHost, response)
    }

    fun performJsonApiRequest(request: ApiRequest): ApiResponse {
        val serverUrl = request.serverUrl
        val requestStr = request.requests.toString()
        val url = URL(serverUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.connectTimeout = 15000
        connection.readTimeout = 15000
        connection.requestMethod = "POST"
        connection.addRequestProperty("Content-Type", "application/json")
        connection.doOutput = true
        val outputStream = connection.outputStream
        val requestData = requestStr.toByteArray(Charsets.UTF_8)

        var offset = 0
        while (true) {
            if (offset >= requestData.size) break
            if (requestData.size - offset < 1024) {
                outputStream.write(requestData, offset, requestData.size - offset)
            } else {
                outputStream.write(requestData, offset, 1024)
            }
            offset += 1024
        }
        outputStream.close()
        outputStream.flush() // doesn't make sense tf?

        val sb = StringBuilder()
        val responseCode = connection.responseCode
        val br = BufferedReader(InputStreamReader(connection.inputStream, "UTF-8"))
        while (true) {
            val ln = br.readLine() ?: break
            sb.append(ln)
        }
        return ApiResponse(sb.toString(), responseCode)
    }

    private fun getResponsibleHost(address: String): String {
        val request = AuthRequest("https://fork.webweaver.de/service/get_responsible_host.php")
        val url = URL(request.serverUrl)
        val connection = url.openConnection() as HttpsURLConnection
        connection.connectTimeout = 15000
        connection.requestMethod = "POST"
        connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        connection.doOutput = true
        connection.doInput = true

        val content = serializeContent(mapOf(Pair("login", address)))
        connection.outputStream.write(content)
        connection.outputStream.flush()
        connection.outputStream.close()

        val reader = BufferedReader(InputStreamReader(connection.inputStream, "UTF-8"))
        val sb = StringBuilder()
        while (true) {
            val ln = reader.readLine() ?: break
            sb.append("$ln\n")
        }
        val response = sb.toString()
        val indexOf: Int = response.indexOf("<host>") + 6
        val indexOf2: Int = response.indexOf("</host>")
        if (indexOf >= 0 && indexOf2 >= 0) {
            val urlBuilder = StringBuilder()
            urlBuilder.append("https://")
            urlBuilder.append(response.substring(indexOf, indexOf2))
            urlBuilder.append("/jsonrpc.php")
            return urlBuilder.toString()
        }
        error("invalid get_responsible_host response")
    }

    fun serializeContent(params: Map<String, String>): ByteArray {
        val sb = StringBuilder()
        var addAndOperator = false
        params.forEach { (key, value) ->
            if (addAndOperator)
                sb.append("&")
            else
                addAndOperator = true

            sb.append(URLEncoder.encode(key, "UTF-8"))
            sb.append("=")
            sb.append(URLEncoder.encode(value, "UTF-8"))
        }
        return sb.toString().toByteArray()
    }

}