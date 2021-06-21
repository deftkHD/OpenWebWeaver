package de.deftk.openww.api.model.feature.board

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.IModifiable
import java.util.*

interface IBoardNotification: IModifiable {

    val id: String

    fun getTitle(): String
    fun getText(): String
    fun getColor(): BoardNotificationColor?
    fun getKillDate(): Date?

    fun setTitle(title: String, boardType: BoardType = BoardType.ALL, context: IRequestContext)
    fun setText(text: String, boardType: BoardType = BoardType.ALL, context: IRequestContext)
    fun setColor(color: BoardNotificationColor, boardType: BoardType = BoardType.ALL, context: IRequestContext)
    fun setKillDate(killDate: Date, boardType: BoardType = BoardType.ALL, context: IRequestContext)

    fun edit(title: String? = null, text: String? = null, color: BoardNotificationColor? = null, killDate: Date? = null, boardType: BoardType = BoardType.ALL, context: IRequestContext)
    fun delete(board: BoardType = BoardType.ALL, context: IRequestContext)

}