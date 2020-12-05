package de.deftk.lonet.api.request.handler

import com.google.gson.JsonArray
import de.deftk.lonet.api.exception.ApiException
import de.deftk.lonet.api.model.abstract.IContext
import de.deftk.lonet.api.request.ApiRequest
import de.deftk.lonet.api.response.ApiResponse
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

abstract class AbstractRequestHandler: IRequestHandler {

    protected fun performJsonApiRequestIntern(request: ApiRequest, context: IContext): ApiResponse {
        val responses = mutableListOf<ApiResponse>()
        request.requests.forEach { requestBlock ->
            val serverUrl = context.getRequestUrl()
            val requestStr = requestBlock.toString()
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
            outputStream.flush()
            outputStream.close()

            val sb = StringBuilder()
            val responseCode = connection.responseCode
            val br = BufferedReader(InputStreamReader(connection.inputStream, "UTF-8"))
            while (true) {
                val ln = br.readLine() ?: break
                sb.append(ln)
            }
            responses.add(ApiResponse(sb.toString(), responseCode))
        }
        val remappedResponses = responses.withIndex().map { (index, response) ->
            val responseJson = response.toJson()
            if (!responseJson.isJsonArray) {
                val errorObject = responseJson.asJsonObject.get("error")?.asJsonObject
                if (errorObject != null) {
                    throw ApiException("Internal error (${errorObject.get("code").asInt}): ${errorObject.get("message").asString}")
                } else {
                    throw ApiException("Internal error: No error object, but failure")
                }
            }
            responseJson.asJsonArray.map { json ->
                // remap id
                val obj = json.asJsonObject
                val newId = index * (ApiRequest.SUB_REQUESTS_PER_REQUEST + 1) + obj.get("id").asInt
                obj.remove("id")
                obj.addProperty("id", newId)
                json
            }
        }.flatten()
        val dstResponse = JsonArray()
        remappedResponses.forEach { response ->
            dstResponse.add(response)
        }
        return ApiResponse(dstResponse.toString(), responses.last().code)
    }

}