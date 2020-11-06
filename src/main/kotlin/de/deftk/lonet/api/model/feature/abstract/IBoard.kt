package de.deftk.lonet.api.model.feature.abstract

import de.deftk.lonet.api.model.feature.board.BoardNotification
import de.deftk.lonet.api.model.feature.board.BoardNotificationColor
import java.util.*

interface IBoard {

    fun getBoardNotifications(): List<BoardNotification>
    fun addBoardNotification(title: String, text: String, color: BoardNotificationColor? = null, killDate: Date? = null): BoardNotification

    fun getTeacherBoardNotifications(): List<BoardNotification>
    fun addTeacherBoardNotification(title: String, text: String, color: BoardNotificationColor? = null, killDate: Date? = null): BoardNotification

    fun getPupilBoardNotifications(): List<BoardNotification>
    fun addPupilBoardNotification(title: String, text: String, color: BoardNotificationColor? = null, killDate: Date? = null): BoardNotification

}