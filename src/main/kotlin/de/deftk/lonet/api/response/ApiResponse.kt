package de.deftk.lonet.api.response

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import java.io.Serializable

class ApiResponse(val raw: String, val code: Int): Serializable {

    companion object {
        val gson = GsonBuilder().create()
    }

    fun toJson(): JsonElement {
        return gson.fromJson(raw, JsonElement::class.java)
    }

}