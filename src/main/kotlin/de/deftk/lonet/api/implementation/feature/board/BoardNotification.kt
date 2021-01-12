package de.deftk.lonet.api.implementation.feature.board

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.IModifiable
import de.deftk.lonet.api.model.feature.Modification
import de.deftk.lonet.api.model.feature.board.BoardNotificationColor
import de.deftk.lonet.api.model.feature.board.BoardType
import de.deftk.lonet.api.model.feature.board.IBoardNotification
import de.deftk.lonet.api.request.GroupApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import de.deftk.lonet.api.serialization.DateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import java.util.*

@Serializable
class BoardNotification(
    override val id: String,
    private var title: String,
    private var text: String,
    private var color: BoardNotificationColor? = null,
    @Serializable(with = DateSerializer::class)
    private var killDate: Date? = null,
    override val created: Modification,
    private var modified: Modification
) : IBoardNotification, IModifiable {

    var deleted = false
        private set

    override fun getTitle(): String = title
    override fun getText(): String = text
    override fun getColor(): BoardNotificationColor? = color
    override fun getKillDate(): Date? = killDate
    override fun getModified(): Modification = modified

    override fun setTitle(title: String, boardType: BoardType, context: IRequestContext) {
        edit(title = title, boardType = boardType, context = context)
    }

    override fun setText(text: String, boardType: BoardType, context: IRequestContext) {
        edit(text = text, boardType = boardType, context = context)
    }

    override fun setColor(color: BoardNotificationColor, boardType: BoardType, context: IRequestContext) {
        edit(color = color, boardType = boardType, context = context)
    }

    override fun setKillDate(killDate: Date, boardType: BoardType, context: IRequestContext) {
        edit(killDate = killDate, boardType = boardType, context = context)
    }

    override fun edit(title: String?, text: String?, color: BoardNotificationColor?, killDate: Date?, boardType: BoardType, context: IRequestContext
    ) {
        val request = GroupApiRequest(context)
        val id = when (boardType) {
            BoardType.ALL -> request.addSetBoardNotificationRequest(id, title, text, color, killDate?.time)[1]
            BoardType.TEACHER -> request.addSetTeacherBoardNotificationRequest(id, title, text, color, killDate?.time)[1]
            BoardType.PUPIL -> request.addSetPupilBoardNotificationRequest(id, title, text, color, killDate?.time)[1]
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(Json.decodeFromJsonElement(subResponse["entry"]!!.jsonObject))
    }

    override fun delete(board: BoardType, context: IRequestContext) {
        val request = GroupApiRequest(context)
        when (board) {
            BoardType.ALL -> request.addDeleteBoardNotificationRequest(id)
            BoardType.TEACHER -> request.addDeleteTeacherBoardNotificationRequest(id)
            BoardType.PUPIL -> request.addDeletePupilBoardNotificationRequest(id)
        }
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
        deleted = true
    }

    private fun readFrom(notification: BoardNotification) {
        title = notification.title
        text = notification.text
        color = notification.color
        killDate = notification.killDate
        modified = notification.modified
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BoardNotification

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "BoardNotification(title='$title')"
    }

}