package de.deftk.openww.api.implementation.feature.board

import de.deftk.openww.api.WebWeaverClient
import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.IModifiable
import de.deftk.openww.api.model.feature.Modification
import de.deftk.openww.api.model.feature.board.BoardNotificationColor
import de.deftk.openww.api.model.feature.board.BoardType
import de.deftk.openww.api.model.feature.board.IBoardNotification
import de.deftk.openww.api.request.GroupApiRequest
import de.deftk.openww.api.response.ResponseUtil
import de.deftk.openww.api.serialization.DateSerializer
import kotlinx.serialization.Serializable
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
        edit(title, text, color, killDate, boardType, context)
    }

    override fun setText(text: String, boardType: BoardType, context: IRequestContext) {
        edit(title, text, color, killDate, boardType, context)
    }

    override fun setColor(color: BoardNotificationColor, boardType: BoardType, context: IRequestContext) {
        edit(title, text, color, killDate, boardType, context)
    }

    override fun setKillDate(killDate: Date, boardType: BoardType, context: IRequestContext) {
        edit(title, text, color, killDate, boardType, context)
    }

    override fun edit(title: String, text: String, color: BoardNotificationColor?, killDate: Date?, boardType: BoardType, context: IRequestContext) {
        val request = GroupApiRequest(context)
        val id = when (boardType) {
            BoardType.ALL -> request.addSetBoardNotificationRequest(id, title, text, color, killDate?.time)[1]
            BoardType.TEACHER -> request.addSetTeacherBoardNotificationRequest(id, title, text, color, killDate?.time)[1]
            BoardType.PUPIL -> request.addSetPupilBoardNotificationRequest(id, title, text, color, killDate?.time)[1]
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse["entry"]!!.jsonObject))
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