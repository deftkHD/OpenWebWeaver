package de.deftk.lonet.api.model.feature

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Member
import de.deftk.lonet.api.model.User
import de.deftk.lonet.api.request.ApiRequest
import java.io.Serializable
import java.util.*

class SystemNotification(val id: String, val messageType: SystemNotificationType, val date: Date, val message: String, val data: String, val member: Member, val group: Member, val fromId: Any?, val read: Boolean, val obj: String?) : Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject): SystemNotification {
            val fromId = jsonObject.get("from_id")
            return SystemNotification(
                    jsonObject.get("id").asString,
                    SystemNotificationType.getById(jsonObject.get("message").asString),
                    Date(jsonObject.get("date").asLong * 1000),
                    jsonObject.get("message_hr").asString,
                    jsonObject.get("data").asString,
                    Member.fromJson(jsonObject.get("from_user").asJsonObject, null),
                    Member.fromJson(jsonObject.get("from_group").asJsonObject, null),
                    if (fromId.isJsonNull) null else fromId.asString,
                    jsonObject.get("is_unread").asInt == 0,
                    jsonObject.get("object")?.asString
            )
        }
    }

    //FIXME this is not even correct implemented
    fun delete(user: User, responsibleHost: String) {
        val request = ApiRequest(responsibleHost)
        request.addSetFocusRequest("messages", user.login)
        val json = JsonObject()
        json.addProperty("id", id)
        request.addRequest("delete_message", json)
    }

    override fun toString(): String {
        return messageType.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    enum class SystemNotificationType(val id: String) : Serializable {
        FILE_UPLOAD("7"),
        FILE_DOWNLOAD("8"),
        NEW_NOTIFICATION("29"),
        NEW_TRUST("33"),
        NEW_TASK("46"),
        UNKNOWN("");

        companion object {
            @JvmStatic
            fun getById(id: String): SystemNotificationType {
                return values().firstOrNull { it.id == id }
                        ?: UNKNOWN.apply { println("Unknown system notification $id") } //TODO print title
            }
        }
    }

}