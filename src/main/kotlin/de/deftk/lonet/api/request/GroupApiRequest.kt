package de.deftk.lonet.api.request

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Group
import java.io.Serializable

open class GroupApiRequest(val group: Group) : OperatorApiRequest(group), Serializable {

    fun addGetMembersRequest(login: String = group.getLogin()): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("members", login),
                addRequest("get_users", null)
        )
    }

    fun addGetForumStateRequest(login: String = group.getLogin()): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("forum", login),
                addRequest("get_state", null)
        )
    }

    fun addGetForumPostsRequest(login: String = group.getLogin(), parentId: String? = null): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        if (parentId != null)
            requestProperties.addProperty("parent_id", parentId)
        return listOf(
                addSetFocusRequest("forum", login),
                addRequest("get_entries", requestProperties)
        )
    }

}