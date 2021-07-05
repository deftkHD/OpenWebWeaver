package de.deftk.openww.api.model.feature.board

import de.deftk.openww.api.model.IRequestContext
import java.util.*

interface IBoard {

    suspend fun getBoardNotifications(context: IRequestContext): List<IBoardNotification>
    suspend fun addBoardNotification(notification: IBoardNotification, context: IRequestContext): IBoardNotification
    suspend fun addBoardNotification(title: String, text: String, color: BoardNotificationColor? = null, killDate: Date? = null, context: IRequestContext): IBoardNotification

    suspend fun getTeacherBoardNotifications(context: IRequestContext): List<IBoardNotification>
    suspend fun addTeacherBoardNotification(notification: IBoardNotification, context: IRequestContext): IBoardNotification
    suspend fun addTeacherBoardNotification(title: String, text: String, color: BoardNotificationColor? = null, killDate: Date? = null, context: IRequestContext): IBoardNotification

    suspend fun getPupilBoardNotifications(context: IRequestContext): List<IBoardNotification>
    suspend fun addPupilBoardNotification(notification: IBoardNotification, context: IRequestContext): IBoardNotification
    suspend fun addPupilBoardNotification(title: String, text: String, color: BoardNotificationColor? = null, killDate: Date? = null, context: IRequestContext): IBoardNotification

}