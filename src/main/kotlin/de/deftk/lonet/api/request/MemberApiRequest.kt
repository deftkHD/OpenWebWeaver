package de.deftk.lonet.api.request

import com.google.gson.JsonObject
import java.io.Serializable

open class MemberApiRequest(serverUrl: String, private val login: String) : ApiRequest(serverUrl), Serializable {

    fun addGetTasksRequest(login: String = this.login): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("tasks", login),
                addRequest("get_entries", null)
        )
    }

    fun addGetMembersRequest(login: String = this.login): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("members", login),
                addRequest("get_users", null)
        )
    }

    fun addGetNotificationsRequest(login: String = this.login): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("board", login),
                addRequest("get_entries", null)
        )
    }

    fun addGetFileStorageStateRequest(login: String = this.login): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("files", login),
                addRequest("get_state", null)
        )
    }

    fun addGetForumStateRequest(login: String = this.login): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("forum", login),
                addRequest("get_state", null)
        )
    }

    fun addGetForumPostsRequest(login: String = this.login, parentId: String? = null): List<Int> {
        ensureCapacity(2)
        val requestData = JsonObject()
        if (parentId != null)
            requestData.addProperty("parent_id", parentId)
        return listOf(
                addSetFocusRequest("forum", login),
                addRequest("get_entries", requestData)
        )
    }

}