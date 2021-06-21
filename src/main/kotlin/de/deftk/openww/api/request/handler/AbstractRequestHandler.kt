package de.deftk.openww.api.request.handler

import de.deftk.openww.api.exception.ApiException
import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.request.ApiRequest
import de.deftk.openww.api.response.ApiResponse
import de.deftk.openww.api.utils.PlatformUtil
import kotlinx.serialization.json.*

abstract class AbstractRequestHandler: IRequestHandler {

    protected fun performApiRequestIntern(request: ApiRequest, context: IRequestContext): ApiResponse {
        val responses = mutableListOf<ApiResponse>()
        request.requests.forEach { requestBlock ->
            val response = PlatformUtil.postRequest(
                context.requestUrl,
                15000,
                "application/json",
                JsonArray(requestBlock).toString().toByteArray()
            )
            responses.add(response)
        }
        val remappedResponses = responses.withIndex().map { (index, response) ->
            val responseJson = response.toJson()
            if (responseJson !is JsonArray) {
                val errorObject = responseJson.jsonObject["error"]?.jsonObject
                if (errorObject != null) {
                    throw ApiException("Internal error (${errorObject["code"]?.jsonPrimitive?.int}): ${errorObject["message"]?.jsonPrimitive}")
                } else {
                    throw ApiException("Internal error: No error object, but failure")
                }
            }
            JsonArray(responseJson.jsonArray.map { json ->
                // remap id
                val obj = json.jsonObject
                val newId = index * (ApiRequest.METHODS_PER_REQUEST + 1) + obj["id"]!!.jsonPrimitive.int
                buildJsonObject {
                    obj.forEach { (key, value) ->
                        if (key != "id") {
                            put(key, value)
                        } else {
                            put("id", newId)
                        }
                    }
                }
            })
        }.flatten()
        val dstResponse = buildJsonArray {
            remappedResponses.forEach { response ->
                add(response)
            }
        }
        return ApiResponse(dstResponse.toString(), responses.last().code)
    }

}