package de.deftk.lonet.api.model.feature.abstract

import de.deftk.lonet.api.model.User
import de.deftk.lonet.api.model.feature.SystemNotification

interface ISystemNotificationList {

    fun getSystemNotifications(overwriteCache: Boolean = false): List<SystemNotification>

}