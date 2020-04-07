package de.deftk.lonet.api.response

import com.google.gson.JsonElement
import com.google.gson.JsonObject

object ResponseUtil {

    fun getSubResponse(response: JsonElement, id: Int): JsonObject {
        check(response.isJsonArray)
        response.asJsonArray.map { it.asJsonObject }.forEach { subResponse ->
            if (subResponse.get("id").asInt == id)
                return subResponse
        }
        error("invalid response (expecting sub response with id $id): $response")
    }

    fun getSubResponseResult(response: JsonElement, id: Int): JsonObject {
        return getSubResponse(response, id).get("result").asJsonObject
    }

    fun getSubResponseResultByMethod(response: JsonElement, method: String): JsonObject {
        check(response.isJsonArray)
        var sub: JsonObject? = null
        response.asJsonArray.map { it.asJsonObject }.forEach { subResponse ->
            val result = subResponse.get("result").asJsonObject
            if (result.get("method")?.asString == method) {
                sub = result
            }
        }
        return sub ?: error("invalid response (expecting sub response with method $method): $response")
    }

    fun checkSuccess(response: JsonElement) {
        response.asJsonArray.map { it.asJsonObject }.forEach { subResponse ->
            val result = subResponse.get("return")?.asString
            if (result != null && result != "OK") {
                throw IllegalStateException("Server returned error: ")
            }
        }
    }

}