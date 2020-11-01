package de.deftk.lonet.api.model.feature.abstract

import de.deftk.lonet.api.model.feature.Notification

interface INotificationList {

    fun getNotifications(): List<Notification>

}