package de.deftk.openww.api.request.handler

import de.deftk.openww.api.exception.ApiException
import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.request.ApiRequest
import de.deftk.openww.api.response.ApiResponse
import de.deftk.openww.api.utils.PlatformUtil
import kotlinx.serialization.json.*

abstract class AbstractRequestHandler: IRequestHandler {

    protected suspend fun performApiRequestIntern(request: ApiRequest, context: IRequestContext): ApiResponse {
        val responses = mutableListOf<ApiResponse>()
        val bundle = request.packRequestsIntoBundle(context)
        bundle.forEach { requestBlock ->
            val response = PlatformUtil.postRequest(
                context.requestUrl,
                15000,
                "application/json",
                JsonArray(requestBlock).toString().toByteArray()
            )
            responses.add(response)
        }
        val remappedResponses = responses.map { response ->
            val responseJson = response.toJson()
            if (responseJson !is JsonArray) {
                val errorObject = responseJson.jsonObject["error"]?.jsonObject
                if (errorObject != null) {
                    throw ApiException("Internal error (${errorObject["code"]?.jsonPrimitive?.int}): ${errorObject["message"]?.jsonPrimitive}")
                } else {
                    throw ApiException("Internal error: No error object, but failure")
                }
            }
            responseJson
        }.flatten()
        val dstResponse = buildJsonArray {
            remappedResponses.forEach { response ->
                add(response)
            }
        }
        return ApiResponse(dstResponse.toString(), responses.last().code)
    }

}