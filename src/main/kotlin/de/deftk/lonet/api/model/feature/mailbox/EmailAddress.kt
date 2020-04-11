package de.deftk.lonet.api.model.feature.mailbox

import com.google.gson.JsonObject

class EmailAddress(jsonObject: JsonObject) {

    val address = jsonObject.get("addr").asString
    val name = jsonObject.get("name").asString

    override fun toString(): String {
        return address
    }

}