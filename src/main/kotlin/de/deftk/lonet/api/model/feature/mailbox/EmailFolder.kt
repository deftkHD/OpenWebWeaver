package de.deftk.lonet.api.model.feature.mailbox

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Member
import de.deftk.lonet.api.model.User
import de.deftk.lonet.api.request.ApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import java.io.Serializable

class EmailFolder(val id: String, val name: String, val type: EmailFolderType, val member: Member) : Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject, member: Member): EmailFolder {
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
                    type, member
            )
        }
    }

    fun getEmails(user: User, overwriteCache: Boolean = false): List<Email> {
        check(member.responsibleHost != null) { "Can't do API calls for member $member" }
        val request = ApiRequest(member.responsibleHost)
        request.addSetFocusRequest("mailbox", member.login)
        val requestParams = JsonObject()
        requestParams.addProperty("folder_id", id)
        request.addRequest("get_messages", requestParams)
        val response = request.fireRequest(user, overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), 3)
        return subResponse.get("messages").asJsonArray.map { Email.fromJson(it.asJsonObject, this, member) }
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