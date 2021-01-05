package de.deftk.lonet.api.model.feature.systemnotification

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.RemoteScope
import java.util.*

interface ISystemNotification {

    fun getId(): String
    fun getMessageType(): SystemNotificationType
    fun getDate(): Date
    fun getMessage(): String
    fun getData(): String
    fun getMember(): RemoteScope
    fun getGroup(): RemoteScope
    fun getFromId(): Int?
    fun isRead(): Boolean
    fun getObj(): String

    fun delete(context: IRequestContext)

}