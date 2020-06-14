package de.deftk.lonet.api.response

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import de.deftk.lonet.api.exception.ApiException

object ResponseUtil {

    fun getSubResponse(response: JsonElement, id: Int): JsonObject {
        checkSuccess(response)
        response.asJsonArray.map { it.asJsonObject }.forEach { subResponse ->
            if (subResponse.get("id").asInt == id)
                return subResponse
        }
        throw ApiException("invalid response (expecting sub response with id $id): $response")
    }

    fun getSubResponseResult(response: JsonElement, id: Int): JsonObject {
        return getSubResponse(response, id).get("result").asJsonObject
    }

    fun getSubResponseResultByMethod(response: JsonElement, method: String): JsonObject {
        checkSuccess(response)
        response.asJsonArray.map { it.asJsonObject }.forEach { subResponse ->
            val result = subResponse.get("result").asJsonObject
            if (result.get("method")?.asString == method) {
                return result
            }
        }
        throw ApiException("invalid response (expecting sub response with method $method): $response")
    }

    fun checkSuccess(response: JsonElement) {
        response.asJsonArray.map { it.asJsonObject }.forEach { subResponse ->
            val result = subResponse.get("result").asJsonObject
            if (result.has("return") && result.get("return").asString != "OK") {
                throw ApiException("Server returned error: ${result.get("error").asString}")
            }
        }
    }

}