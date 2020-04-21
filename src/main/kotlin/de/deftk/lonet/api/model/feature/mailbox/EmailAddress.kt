package de.deftk.lonet.api.model.feature.mailbox

import com.google.gson.JsonObject
import java.io.Serializable

class EmailAddress(jsonObject: JsonObject): Serializable {

    val address = jsonObject.get("addr").asString
    val name = jsonObject.get("name").asString

    override fun toString(): String {
        return address
    }

}