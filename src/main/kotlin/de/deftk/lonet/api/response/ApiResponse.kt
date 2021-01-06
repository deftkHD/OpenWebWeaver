package de.deftk.lonet.api.response

import de.deftk.lonet.api.LoNetClient
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiResponse(val text: String, val code: Int) {

    fun toJson(): JsonElement = LoNetClient.json.decodeFromString(text)

}