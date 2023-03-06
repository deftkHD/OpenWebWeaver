package de.deftk.openww.api.implementation.feature.systemnotification

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.RemoteScope
import de.deftk.openww.api.model.feature.systemnotification.ISystemNotification
import de.deftk.openww.api.model.feature.systemnotification.SystemNotificationType
import de.deftk.openww.api.request.UserApiRequest
import de.deftk.openww.api.response.ResponseUtil
import de.deftk.openww.api.serialization.BooleanFromIntSerializer
import de.deftk.openww.api.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
class SystemNotification(
    override val id: String,
    @SerialName("message")
    override val messageType: SystemNotificationType,
    @Serializable(with = DateSerializer::class)
    override val date: Date?,
    @SerialName("message_hr")
    override val message: String,
    override val data: String?,
    @SerialName("from_user")
    override val member: RemoteScope,
    @SerialName("from_group")
    override val group: RemoteScope,
    @SerialName("from_id")
    override val fromId: Int? = null,
    @SerialName("is_unread")
    @Serializable(with = BooleanFromIntSerializer::class)
    override val isUnread: Boolean,
    @SerialName("object")
    override val obj: String?
): ISystemNotification {

    override suspend fun delete(context: IRequestContext) {
        val request = UserApiRequest(context)
        request.addDeleteSystemNotificationRequest(this.id.toInt()) // response returns string, but request requires an int
        ResponseUtil.checkSuccess(request.fireRequest().toJson())
    }

    override fun toString(): String {
        return "SystemNotification(messageType=$messageType, message='$message')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SystemNotification) return false

        if (id != other.id) return false
        if (messageType != other.messageType) return false
        if (date != other.date) return false
        if (message != other.message) return false
        if (data != other.data) return false
        if (member != other.member) return false
        if (group != other.group) return false
        if (fromId != other.fromId) return false
        if (isUnread != other.isUnread) return false
        if (obj != other.obj) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + messageType.hashCode()
        result = 31 * result + (date?.hashCode() ?: 0)
        result = 31 * result + message.hashCode()
        result = 31 * result + (data?.hashCode() ?: 0)
        result = 31 * result + member.hashCode()
        result = 31 * result + group.hashCode()
        result = 31 * result + (fromId ?: 0)
        result = 31 * result + isUnread.hashCode()
        result = 31 * result + (obj?.hashCode() ?: 0)
        return result
    }

}