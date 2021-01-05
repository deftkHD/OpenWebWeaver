package de.deftk.lonet.api.model.feature.board

import de.deftk.lonet.api.model.IRequestContext
import java.util.*

interface IBoard {

    fun getBoardNotifications(context: IRequestContext): List<IBoardNotification>
    fun addBoardNotification(notification: IBoardNotification, context: IRequestContext): IBoardNotification
    fun addBoardNotification(title: String, text: String, color: BoardNotificationColor? = null, killDate: Date? = null, context: IRequestContext): IBoardNotification

    fun getTeacherBoardNotifications(context: IRequestContext): List<IBoardNotification>
    fun addTeacherBoardNotification(notification: IBoardNotification, context: IRequestContext): IBoardNotification
    fun addTeacherBoardNotification(title: String, text: String, color: BoardNotificationColor? = null, killDate: Date? = null, context: IRequestContext): IBoardNotification

    fun getPupilBoardNotifications(context: IRequestContext): List<IBoardNotification>
    fun addPupilBoardNotification(notification: IBoardNotification, context: IRequestContext): IBoardNotification
    fun addPupilBoardNotification(title: String, text: String, color: BoardNotificationColor? = null, killDate: Date? = null, context: IRequestContext): IBoardNotification

}