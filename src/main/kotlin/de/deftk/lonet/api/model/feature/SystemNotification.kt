package de.deftk.lonet.api.model.feature

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.User
import de.deftk.lonet.api.model.abstract.IManageable
import de.deftk.lonet.api.request.UserApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import de.deftk.lonet.api.utils.getApiDate
import de.deftk.lonet.api.utils.getBoolOrNull
import de.deftk.lonet.api.utils.getStringOrNull
import java.io.Serializable
import java.util.*

class SystemNotification(val id: String, val messageType: SystemNotificationType?, val date: Date, val message: String, val data: String, val member: IManageable, val group: IManageable, val fromId: Any?, val read: Boolean, val obj: String?, val user: User) : Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject, user: User): SystemNotification {
            return SystemNotification(
                    jsonObject.get("id").asString,
                    SystemNotificationType.getById(jsonObject.getStringOrNull("message")),
                    jsonObject.getApiDate("date"),
                    jsonObject.get("message_hr").asString,
                    jsonObject.get("data").asString,
                    user.getContext().getOrCreateManageable(jsonObject.get("from_user").asJsonObject),
                    user.getContext().getOrCreateManageable(jsonObject.get("from_group").asJsonObject),
                    jsonObject.getStringOrNull("from_id"),
                    !jsonObject.getBoolOrNull("is_unread")!!,
                    jsonObject.getStringOrNull("object"),
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

        other as SystemNotification

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
        UNAUTHORIZED_LOGIN_LOCATION("35"),
        NEW_TASK("46");

        companion object {
            @JvmStatic
            fun getById(id: String?): SystemNotificationType? {
                val type = values().firstOrNull { it.id == id }
                if (type == null)
                    println("Unknown system notification type $id")
                return type
            }
        }
    }

}