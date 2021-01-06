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
    private val id: String,
    @SerialName("message")
    private val type: SystemNotificationType,
    @Serializable(with = DateSerializer::class)
    private val date: Date,
    @SerialName("message_hr")
    private val message: String,
    private val data: String,
    @SerialName("from_user")
    private val member: RemoteScope,
    @SerialName("from_group")
    private val group: RemoteScope,
    @SerialName("from_id")
    private val fromId: Int? = null,
    @SerialName("is_unread")
    @Serializable(with = BooleanFromIntSerializer::class)
    private val unread: Boolean,
    @SerialName("object")
    private val obj: String
): ISystemNotification {

    override fun getId(): String = id
    override fun getMessageType(): SystemNotificationType = type
    override fun getDate(): Date = date
    override fun getMessage(): String = message
    override fun getData(): String = data
    override fun getMember(): RemoteScope = member
    override fun getGroup(): RemoteScope = group
    override fun getFromId(): Int? = fromId
    override fun isRead(): Boolean = !unread
    override fun getObj(): String = obj

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

}