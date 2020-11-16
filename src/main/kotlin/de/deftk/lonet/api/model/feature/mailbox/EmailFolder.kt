package de.deftk.lonet.api.model.feature.mailbox

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.abstract.AbstractOperator
import de.deftk.lonet.api.request.OperatorApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import de.deftk.lonet.api.utils.getApiDate
import java.io.Serializable
import java.util.*

class EmailFolder(val id: String, name: String, val type: EmailFolderType, val mDate: Date, val operator: AbstractOperator) : Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject, operator: AbstractOperator): EmailFolder {
            val type = when {
                jsonObject.get("is_inbox").asBoolean -> EmailFolderType.INBOX
                jsonObject.get("is_trash").asBoolean -> EmailFolderType.TRASH
                jsonObject.get("is_drafts").asBoolean -> EmailFolderType.DRAFTS
                jsonObject.get("is_sent").asBoolean -> EmailFolderType.SENT
                else -> EmailFolderType.OTHER
            }
            return EmailFolder(
                    jsonObject.get("id").asString,
                    jsonObject.get("name").asString,
                    type,
                    jsonObject.getApiDate("m_date"),
                    operator
            )
        }
    }

    var name = name
        private set

    fun getEmails(limit: Int? = null, offset: Int? = null): List<Email> {
        val request = OperatorApiRequest(operator)
        val id = request.addGetEmailsRequest(id, limit, offset)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("messages").asJsonArray.map { Email.fromJson(it.asJsonObject, this, operator) }
    }

    fun edit(name: String) {
        val request = OperatorApiRequest(operator)
        request.addSetEmailFolderRequest(id, name)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
        this.name = name
    }

    //FIXME for some reason fails
    fun delete() {
        val request = OperatorApiRequest(operator)
        request.addDeleteEmailFolderRequest(id)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun toString(): String {
        return "$name:$id"
    }

    enum class EmailFolderType : Serializable {
        INBOX,
        TRASH,
        DRAFTS,
        SENT,
        OTHER
    }

}