package de.deftk.lonet.api.model.feature

import com.google.gson.JsonObject

class WikiPage(val name: String, val exists: Boolean, val source: String) {

    companion object {
        fun fromJson(jsonObject: JsonObject): WikiPage {
            return WikiPage(
                    jsonObject.get("name").asString,
                    jsonObject.get("exists").asString == "true",
                    jsonObject.get("source").asString
            )
        }
    }

}