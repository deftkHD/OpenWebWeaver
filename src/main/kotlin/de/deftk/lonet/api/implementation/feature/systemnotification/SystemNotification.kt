package de.deftk.lonet.api.implementation.feature.systemnotification

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.RemoteScope
import de.deftk.lonet.api.model.feature.systemnotification.ISystemNotification
import de.deftk.lonet.api.model.feature.systemnotification.SystemNotificationType
import de.deftk.lonet.api.request.UserApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import de.deftk.lonet.api.serialization.BooleanFromIntSerializer
import de.deftk.lonet.api.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
class SystemNotification(
    override val id: String,
    @SerialName("message")
    override val messageType: SystemNotificationType,
    @Serializable(with = DateSerializer::class)
    override val date: Date,
    @SerialName("message_hr")
    override val message: String,
    override val data: String,
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

    override fun delete(context: IRequestContext) {
        val request = UserApiRequest(context)
        request.addDeleteSystemNotificationRequest(this.id.toInt())[1] // response returns string, but request requires an int
        ResponseUtil.checkSuccess(request.fireRequest().toJson())
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

    override fun toString(): String {
        return "SystemNotification(messageType=$messageType, message='$message')"
    }

}