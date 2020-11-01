package de.deftk.lonet.api.model.feature.mailbox

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.abstract.AbstractOperator
import de.deftk.lonet.api.request.ApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import java.io.Serializable

class EmailFolder(val id: String, val name: String, val type: EmailFolderType, val operator: AbstractOperator) : Serializable {

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
                    operator
            )
        }
    }

    fun getEmails(): List<Email> {
        val request = ApiRequest()
        request.addSetFocusRequest("mailbox", operator.getLogin())
        val requestParams = JsonObject()
        requestParams.addProperty("folder_id", id)
        request.addRequest("get_messages", requestParams)
        val response = request.fireRequest(operator.getContext())
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), 3)
        return subResponse.get("messages").asJsonArray.map { Email.fromJson(it.asJsonObject, this, operator) }
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