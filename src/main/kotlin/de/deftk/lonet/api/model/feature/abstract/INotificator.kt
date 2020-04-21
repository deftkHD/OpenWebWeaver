package de.deftk.lonet.api.model.feature.abstract

import de.deftk.lonet.api.model.User
import de.deftk.lonet.api.model.feature.Notification

interface INotificator {

    fun getNotifications(user: User, overwriteCache: Boolean = false): List<Notification>

}