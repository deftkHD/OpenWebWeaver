package de.deftk.lonet.api.model.abstract

import de.deftk.lonet.api.model.feature.board.BoardNotification
import de.deftk.lonet.api.model.feature.Task
import de.deftk.lonet.api.model.feature.abstract.ISessionFileHolder
import de.deftk.lonet.api.model.feature.abstract.ISystemNotificationList

interface IUser: ISystemNotificationList, ISessionFileHolder {

    fun getAutoLoginUrl(): String
    fun logout(removeTrust: Boolean = true)
    fun checkSession(): Boolean
    fun registerService(type: ServiceType, token: String, application: String? = null, generateSecret: String? = null, isOnline: Boolean? = null, managedObjects: String? = null, unmanagedPriority: Int? = null)
    fun unregisterService(type: ServiceType, token: String)

    fun getAllTasks(): List<Task>
    fun getAllBoardNotifications(): List<BoardNotification>
    fun getAllTeacherBoardNotifications(): List<BoardNotification>
    fun getAllPupilBoardNotifications(): List<BoardNotification>

    enum class ServiceType(val id: String) {
        APPLE_PUSH_NOTIFICATION_SERVICE("apns"),
        FIREBASE_CLOUD_MESSAGING("fcm"),
        @Deprecated("Google Cloud Messaging is officially deprecated. Consider using Firebase Cloud Messaging")
        GOOGLE_CLOUD_MESSAGING("gcm"),
        TEST("test")
    }

}