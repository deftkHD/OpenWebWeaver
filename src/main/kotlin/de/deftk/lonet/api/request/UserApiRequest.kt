package de.deftk.lonet.api.request

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Feature
import de.deftk.lonet.api.model.User
import de.deftk.lonet.api.response.ApiResponse
import java.io.Serializable

class UserApiRequest(serverUrl: String, private val user: User) : MemberApiRequest(serverUrl, user.login), Serializable {

    fun addGetAutoLoginUrlRequest(): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("trusts", user.login),
                addRequest("get_url_for_autologin", null) //TODO this method allows parameters)
        )
    }

    fun addGetEmailStateRequest(): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("mailbox", user.login),
                addRequest("get_state", null)
        )
    }

    fun addGetEmailFoldersRequest(): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("mailbox", user.login),
                addRequest("get_folders", null)
        )
    }

    fun addSendEmailRequest(to: String, subject: String, plainBody: String, text: String?, bcc: String?, cc: String?): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("to", to)
        requestParams.addProperty("subject", subject)
        requestParams.addProperty("body_plain", plainBody)
        if (text != null)
            requestParams.addProperty("text", text)
        if (bcc != null)
            requestParams.addProperty("bcc", bcc)
        if (cc != null)
            requestParams.addProperty("cc", cc)
        return listOf(
                addSetFocusRequest("mailbox", user.login),
                addRequest("send_mail", requestParams)
        )
    }

    fun addGetSystemNotificationsRequest(): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("messages", user.login),
                addRequest("get_messages", null)
        )
    }

    fun addGetAllTasksRequest(): List<Int> {
        val ids = mutableListOf<Int>()
        if (Feature.TASKS.isAvailable(user.permissions)) {
            ids.addAll(addGetTasksRequest())
        }
        user.memberships.forEach { membership ->
            if (Feature.TASKS.isAvailable(membership.memberPermissions)) {
                ids.addAll(addGetTasksRequest(membership.login))
            }
        }
        return ids
    }

    fun addGetAllNotificationsRequest(): List<Int> {
        val ids = mutableListOf<Int>()
        if (Feature.BOARD.isAvailable(user.permissions)) {
            ids.addAll(addGetNotificationsRequest())
        }
        user.memberships.forEach { membership ->
            if (Feature.BOARD.isAvailable(membership.memberPermissions)) {
                ids.addAll(addGetNotificationsRequest(membership.login))
            }
        }
        return ids
    }

    fun fireRequest(overwriteCache: Boolean = false): ApiResponse {
        return super.fireRequest(user, overwriteCache)
    }

}