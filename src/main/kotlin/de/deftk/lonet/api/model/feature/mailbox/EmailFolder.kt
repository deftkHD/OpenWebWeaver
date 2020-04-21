package de.deftk.lonet.api.model.feature.mailbox

import com.google.gson.JsonObject
import de.deftk.lonet.api.LoNet
import de.deftk.lonet.api.model.User
import de.deftk.lonet.api.request.ApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import java.io.Serializable

class EmailFolder(jsonObject: JsonObject, private val responsibleHost: String): Serializable {

    val id = jsonObject.get("id").asString
    val name = jsonObject.get("name").asString
    val type = when {
        jsonObject.get("is_inbox").asBoolean -> EmailFolderType.INBOX
        jsonObject.get("is_trash").asBoolean -> EmailFolderType.TRASH
        jsonObject.get("is_drafts").asBoolean -> EmailFolderType.DRAFTS
        jsonObject.get("is_sent").asBoolean -> EmailFolderType.SENT
        else -> EmailFolderType.OTHER
    }

    fun getEmails(user: User, overwriteCache: Boolean = false): List<Email> {
        val request = ApiRequest(responsibleHost)
        request.addSetFocusRequest("mailbox", user.login)
        val requestParams = JsonObject()
        requestParams.addProperty("folder_id", id)
        request.addRequest("get_messages", requestParams)
        val response = request.fireRequest(user, overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), 3)
        return subResponse.get("messages").asJsonArray.map { Email(it.asJsonObject, this, responsibleHost) }
    }

    override fun toString(): String {
        return "$name:$id"
    }

    enum class EmailFolderType: Serializable {
        INBOX,
        TRASH,
        DRAFTS,
        SENT,
        OTHER
    }

}