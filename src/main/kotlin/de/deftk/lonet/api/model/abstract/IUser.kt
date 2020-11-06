package de.deftk.lonet.api.model.abstract

import de.deftk.lonet.api.model.feature.board.BoardNotification
import de.deftk.lonet.api.model.feature.Task
import de.deftk.lonet.api.model.feature.abstract.ISystemNotificationList

interface IUser: ISystemNotificationList {

    fun getAutoLoginUrl(): String
    fun logout(removeTrust: Boolean = true)
    fun checkSession(): Boolean

    fun getAllTasks(): List<Task>
    fun getAllBoardNotifications(): List<BoardNotification>
    fun getAllTeacherBoardNotifications(): List<BoardNotification>
    fun getAllPupilBoardNotifications(): List<BoardNotification>

}