package de.deftk.lonet.api.model.feature.board

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.abstract.AbstractOperator
import de.deftk.lonet.api.model.abstract.IManageable
import de.deftk.lonet.api.model.feature.Task
import de.deftk.lonet.api.request.OperatorApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import java.io.Serializable
import java.util.*

class BoardNotification(val id: String, title: String?, text: String?, color: BoardNotificationColor?, killDate: Date?, creationDate: Date, creationMember: IManageable, modificationDate: Date, modificationMember: IManageable, val board: BoardType, val operator: AbstractOperator) : Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject, operator: AbstractOperator, board: BoardType): BoardNotification {
            val createdObject = jsonObject.get("created").asJsonObject
            val modifiedObject = jsonObject.get("modified").asJsonObject
            val notification = BoardNotification(
                    jsonObject.get("id").asString,
                    jsonObject.get("title")?.asString,
                    jsonObject.get("text")?.asString,
                    BoardNotificationColor.getById(jsonObject.get("color").asInt),
                    null,
                    Date(createdObject.get("date").asLong * 1000),
                    operator.getContext().getOrCreateManageable(createdObject.get("user").asJsonObject),
                    Date(modifiedObject.get("date").asLong * 1000),
                    operator.getContext().getOrCreateManageable(modifiedObject.get("user").asJsonObject),
                    board,
                    operator
            )
            notification.readFrom(jsonObject)
            return notification
        }
    }

    var title = title
        private set
    var text = text
        private set
    var color = color
        private set
    var killDate = killDate
        private set
    var creationDate = creationDate
        private set
    var creationMember = creationMember
        private set
    var modificationDate = modificationDate
        private set
    var modificationMember = modificationMember
        private set

    fun edit(title: String? = null, text: String? = null, color: BoardNotificationColor? = null, killDate: Date? = null) {
        val request = OperatorApiRequest(operator)
        val id = when (board) {
            BoardType.ALL -> request.addSetBoardNotificationRequest(id, title, text, color, killDate)[1]
            BoardType.TEACHER -> request.addSetTeacherBoardNotificationRequest(id, title, text, color, killDate)[1]
            BoardType.PUPIL -> request.addSetPupilBoardNotificationRequest(id, title, text, color, killDate)[1]
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(subResponse.get("entry").asJsonObject)
    }

    fun delete() {
        val request = OperatorApiRequest(operator)
        when (board) {
            BoardType.ALL -> request.addDeleteBoardNotificationRequest(id)
            BoardType.TEACHER -> request.addDeleteTeacherBoardNotificationRequest(id)
            BoardType.PUPIL -> request.addDeletePupilBoardNotificationRequest(id)
        }
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    private fun readFrom(jsonObject: JsonObject) {
        title = jsonObject.get("title")?.asString
        text = jsonObject.get("text")?.asString
        color = if (jsonObject.has("color")) BoardNotificationColor.getById(jsonObject.get("color").asInt) else null
        killDate = if (jsonObject.has("kill_date")) Date(jsonObject.get("kill_date").asInt * 1000L) else null

        val createdObject = jsonObject.get("created").asJsonObject
        val modifiedObject = jsonObject.get("modified").asJsonObject
        creationDate = Date(createdObject.get("date").asLong * 1000)
        creationMember = operator.getContext().getOrCreateManageable(createdObject.get("user").asJsonObject)
        modificationDate = Date(modifiedObject.get("date").asLong * 1000)
        modificationMember = operator.getContext().getOrCreateManageable(modifiedObject.get("user").asJsonObject)
    }

    override fun toString(): String {
        return title ?: id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}