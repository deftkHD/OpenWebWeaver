package de.deftk.openww.api.model.feature.board

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.IModifiable
import java.util.*

interface IBoardNotification: IModifiable {

    val id: String

    val title: String
    val text: String
    val color: BoardNotificationColor?

    suspend fun setTitle(title: String, boardType: BoardType = BoardType.ALL, context: IRequestContext)
    suspend fun setText(text: String, boardType: BoardType = BoardType.ALL, context: IRequestContext)
    suspend fun setColor(color: BoardNotificationColor, boardType: BoardType = BoardType.ALL, context: IRequestContext)
    suspend fun setKillDate(killDate: Date, boardType: BoardType = BoardType.ALL, context: IRequestContext)

    suspend fun edit(title: String, text: String, color: BoardNotificationColor, killDate: Date? = null, boardType: BoardType = BoardType.ALL, context: IRequestContext)
    suspend fun delete(board: BoardType = BoardType.ALL, context: IRequestContext)

}