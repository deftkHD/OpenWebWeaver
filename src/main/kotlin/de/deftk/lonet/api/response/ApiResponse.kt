package de.deftk.lonet.api.response

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement

class ApiResponse(val raw: String, val code: Int) {

    companion object {
        val gson = GsonBuilder().create()
    }

    fun toJson(): JsonElement {
        return gson.fromJson(raw, JsonElement::class.java)
    }

}