package de.deftk.lonet.api.model.feature

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.RemoteManageable
import de.deftk.lonet.api.model.User
import de.deftk.lonet.api.model.abstract.IManageable
import de.deftk.lonet.api.request.UserApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import java.io.Serializable
import java.util.*

class SystemNotification(val id: String, val messageType: SystemNotificationType, val date: Date, val message: String, val data: String, val member: IManageable, val group: IManageable, val fromId: Any?, val read: Boolean, val obj: String?, val user: User) : Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject, user: User): SystemNotification {
            val fromId = jsonObject.get("from_id")
            return SystemNotification(
                    jsonObject.get("id").asString,
                    SystemNotificationType.getById(jsonObject.get("message").asString),
                    Date(jsonObject.get("date").asLong * 1000),
                    jsonObject.get("message_hr").asString,
                    jsonObject.get("data").asString,
                    user.getContext().getOrCreateManageable(jsonObject.get("from_user").asJsonObject),
                    user.getContext().getOrCreateManageable(jsonObject.get("from_group").asJsonObject),
                    if (fromId.isJsonNull) null else fromId.asString,
                    jsonObject.get("is_unread").asInt == 0,
                    jsonObject.get("object")?.asString,
                    user
            )
        }
    }

    fun delete() {
        val request = UserApiRequest(user)
        request.addDeleteSystemNotificationRequest(this.id.toInt())[1] // response returns id as string, but request wants an integer
        ResponseUtil.checkSuccess(request.fireRequest().toJson())
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
                val type = values().firstOrNull { it.id == id } ?: UNKNOWN
                if (type == UNKNOWN)
                    println("Unknown system notification type $id")
                return type
            }
        }
    }

}