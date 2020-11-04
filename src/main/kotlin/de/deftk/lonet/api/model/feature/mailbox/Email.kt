package de.deftk.lonet.api.model.feature.mailbox

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.abstract.AbstractOperator
import de.deftk.lonet.api.request.OperatorApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import java.io.Serializable
import java.util.*

class Email(val id: Int, val subject: String, isRead: Boolean?, isFlagged: Boolean?, val answered: Boolean, val date: Date, val size: Long, val from: List<EmailAddress>?, folder: EmailFolder, val operator: AbstractOperator) : Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject, folder: EmailFolder, operator: AbstractOperator): Email {
            val email = Email(
                    jsonObject.get("id").asInt,
                    jsonObject.get("subject").asString,
                    null,
                    null,
                    jsonObject.get("is_answered").asInt == 1,
                    Date(jsonObject.get("date").asLong * 1000),
                    jsonObject.get("size").asLong,
                    jsonObject.get("from")?.asJsonArray?.map { EmailAddress.fromJson(it.asJsonObject) },
                    folder,
                    operator
            )
            email.readFrom(jsonObject)
            return email
        }
    }

    var isRead: Boolean?
        private set

    var isFlagged: Boolean?
        private set

    var folder: EmailFolder
        private set

    init {
        this.isRead = isRead
        this.isFlagged = isFlagged
        this.folder = folder
    }

    fun read(peek: Boolean? = null): EmailContent {
        val request = OperatorApiRequest(operator)
        val id = request.addReadEmailRequest(folder.id, id, peek)[1]
        val response = request.fireRequest(operator.getContext())
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return EmailContent.fromJson(subResponse.get("message").asJsonObject)
    }

    fun edit(isFlagged: Boolean? = null, isUnread: Boolean? = null) {
        val request = OperatorApiRequest(operator)
        request.addSetEmailRequest(folder.id, id, isFlagged, isUnread)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
        if (isFlagged != null)
            this.isFlagged = isFlagged
        if (isUnread != null)
            this.isRead = !isUnread
    }

    fun move(to: EmailFolder) {
        val request = OperatorApiRequest(operator)
        request.addMoveEmailRequest(folder.id, id, to.id)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
        this.folder = to
    }

    fun delete() {
        val request = OperatorApiRequest(operator)
        request.addDeleteEmailRequest(folder.id, id)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    private fun readFrom(jsonObject: JsonObject) {
        isRead = jsonObject.get("is_unread").asInt == 0
        isFlagged = jsonObject.get("is_flagged").asInt == 1
    }

    override fun toString(): String {
        return subject
    }

    data class EmailContent(val plainBody: String, val text: String?, val to: List<EmailAddress>) : Serializable {

        companion object {
            fun fromJson(jsonObject: JsonObject): EmailContent {
                return EmailContent(
                        jsonObject.get("body_plain").asString,
                        jsonObject.get("text")?.asString,
                        jsonObject.get("to").asJsonArray.map { EmailAddress.fromJson(it.asJsonObject) }
                )
            }
        }
    }

}