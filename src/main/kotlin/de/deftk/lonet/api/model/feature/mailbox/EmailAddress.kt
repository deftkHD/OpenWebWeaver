package de.deftk.lonet.api.model.feature.mailbox

import com.google.gson.JsonObject
import java.io.Serializable

data class EmailAddress(val address: String, val name: String) : Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject): EmailAddress {
            return EmailAddress(
                    jsonObject.get("addr").asString,
                    jsonObject.get("name").asString
            )
        }
    }

    override fun toString(): String {
        return address
    }

}