package de.deftk.lonet.api.model.feature

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Member
import de.deftk.lonet.api.request.ApiRequest
import java.util.*

class SystemNotification(jsonObject: JsonObject) {

    val id: String = jsonObject.get("id").asString
    val messageType: SystemNotificationType
    val date: Date
    val message: String
    val data: String
    val member: Member
    val group: Member
    val fromId: Any?
    val read: Boolean
    val obj: String?

    init {
        messageType = SystemNotificationType.getById(jsonObject.get("message").asString)
        date = Date(jsonObject.get("date").asLong * 1000)
        message = jsonObject.get("message_hr").asString
        data = jsonObject.get("data").asString
        member = Member(jsonObject.get("from_user").asJsonObject, null)
        group = Member(jsonObject.get("from_group").asJsonObject, null)
        val id = jsonObject.get("from_id")
        fromId = if (id.isJsonNull) null else id.asString
        read = jsonObject.get("is_unread").asInt == 0
        obj = jsonObject.get("object")?.asString
    }

    //TODO this is not even correct implemented
    fun delete(sessionId: String, responsibleHost: String) {
        val request = ApiRequest(responsibleHost)
        request.addSetSessionRequest(sessionId)
        request.addSetFocusRequest("messages")
        val json = JsonObject()
        json.addProperty("id", id)
        request.addRequest("delete_message", json)
    }

    override fun toString(): String {
        return messageType.toString() ?: id
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

    enum class SystemNotificationType(val id: String) {
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