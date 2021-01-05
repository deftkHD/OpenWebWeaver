package de.deftk.lonet.api.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiResponse(val text: String, val code: Int) {

    fun toJson(): JsonElement = Json.decodeFromString(text)

}