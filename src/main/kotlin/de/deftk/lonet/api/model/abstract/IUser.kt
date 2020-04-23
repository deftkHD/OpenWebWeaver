package de.deftk.lonet.api.model.abstract

import de.deftk.lonet.api.model.feature.Notification
import de.deftk.lonet.api.model.feature.Task
import de.deftk.lonet.api.model.feature.abstract.ISystemNotificationList

interface IUser: ISystemNotificationList {

    fun getAutoLoginUrl(): String
    fun logout(removeTrust: Boolean = true)

    fun getAllTasks(overwriteCache: Boolean = false): List<Task>
    fun getAllNotifications(overwriteCache: Boolean = false): List<Notification>
}