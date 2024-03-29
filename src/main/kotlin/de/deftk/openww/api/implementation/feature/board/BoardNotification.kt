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
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import java.util.*

@Serializable
class BoardNotification(
    override val id: String,
    @SerialName("title")
    private val _title: String,
    @SerialName("text")
    private val _text: String,
    @SerialName("color")
    private val _color: BoardNotificationColor? = null,
    override val created: Modification,
    @SerialName("modified")
    private var _modified: Modification
) : IBoardNotification, IModifiable {

    var deleted = false
        private set

    @SerialName("_title")
    override var title: String = _title
        private set

    @SerialName("_text")
    override var text: String = _text
        private set

    @SerialName("_color")
    override var color: BoardNotificationColor? = _color
        private set

    @SerialName("_modified")
    override var modified: Modification = _modified
            private set

    override suspend fun setTitle(title: String, boardType: BoardType, context: IRequestContext) {
        edit(title, text, color ?: BoardNotificationColor.BLUE, null, boardType, context)
    }

    override suspend fun setText(text: String, boardType: BoardType, context: IRequestContext) {
        edit(title, text, color ?: BoardNotificationColor.BLUE, null, boardType, context)
    }

    override suspend fun setColor(color: BoardNotificationColor, boardType: BoardType, context: IRequestContext) {
        edit(title, text, color, null, boardType, context)
    }

    override suspend fun setKillDate(killDate: Date, boardType: BoardType, context: IRequestContext) {
        edit(title, text, color ?: BoardNotificationColor.BLUE, killDate, boardType, context)
    }

    // killDate == null -> ignore killDate, not reset killDate
    override suspend fun edit(title: String, text: String, color: BoardNotificationColor, killDate: Date?, boardType: BoardType, context: IRequestContext) {
        val request = GroupApiRequest(context)
        val id = when (boardType) {
            BoardType.ALL -> request.addSetBoardNotificationRequest(id, title, text, color, killDate?.time)
            BoardType.TEACHER -> request.addSetTeacherBoardNotificationRequest(id, title, text, color, killDate?.time)
            BoardType.PUPIL -> request.addSetPupilBoardNotificationRequest(id, title, text, color, killDate?.time)
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse["entry"]!!.jsonObject))
    }

    override suspend fun delete(board: BoardType, context: IRequestContext) {
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
        modified = notification.modified
    }

    override fun toString(): String {
        return "BoardNotification(title='$title')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BoardNotification) return false

        if (id != other.id) return false
        if (_title != other._title) return false
        if (_text != other._text) return false
        if (_color != other._color) return false
        if (created != other.created) return false
        if (_modified != other._modified) return false
        if (deleted != other.deleted) return false
        if (title != other.title) return false
        if (text != other.text) return false
        if (color != other.color) return false
        if (modified != other.modified) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + _title.hashCode()
        result = 31 * result + _text.hashCode()
        result = 31 * result + (_color?.hashCode() ?: 0)
        result = 31 * result + created.hashCode()
        result = 31 * result + _modified.hashCode()
        result = 31 * result + deleted.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + (color?.hashCode() ?: 0)
        result = 31 * result + modified.hashCode()
        return result
    }

}