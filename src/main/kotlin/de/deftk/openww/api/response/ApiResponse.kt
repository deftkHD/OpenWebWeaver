package de.deftk.openww.api.response

import de.deftk.openww.api.WebWeaverClient
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.JsonElement

@Serializable
data class ApiResponse(val text: String, val code: Int) {

    fun toJson(): JsonElement = WebWeaverClient.json.decodeFromString(text)

}