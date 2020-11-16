package de.deftk.lonet.api.model.feature.mailbox

import com.google.gson.JsonObject

class Attachment(val id: String, val name: String, val size: Int) {

    companion object {
        fun fromJson(jsonObject: JsonObject): Attachment {
            return Attachment(
                    jsonObject.get("id").asString,
                    jsonObject.get("name").asString,
                    jsonObject.get("size").asInt
            )
        }
    }

}