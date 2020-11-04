package de.deftk.lonet.api.request

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Group
import de.deftk.lonet.api.model.feature.forum.ForumPostIcon
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

    fun addGetForumPostRequest(id: String? = null, login: String = group.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        requestProperties.addProperty("id", id)
        return listOf(
                addSetFocusRequest("forum", login),
                addRequest("get_entry", requestProperties)
        )
    }

    fun addGetForumPostsRequest(parentId: String? = null, login: String = group.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        if (parentId != null)
            requestProperties.addProperty("parent_id", parentId)
        return listOf(
                addSetFocusRequest("forum", login),
                addRequest("get_entries", requestProperties)
        )
    }

    fun addAddForumPostRequest(title: String, text: String, icon: ForumPostIcon, parentId: String = "0", importSessionFile: String? = null, importSessionFiles: Array<String>? = null, replyNotificationMe: Boolean? = null, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("title", title)
        requestParams.addProperty("text", text)
        requestParams.addProperty("icon", icon.id)
        requestParams.addProperty("parent_id", parentId)
        if (importSessionFile != null) requestParams.addProperty("import_session_file", importSessionFile)
        if (importSessionFiles != null) {
            val sessionFiles = JsonArray()
            importSessionFiles.forEach { sessionFiles.add(it) }
            requestParams.add("import_session_files", sessionFiles)
        }
        if (replyNotificationMe != null) requestParams.addProperty("reply_notification_me", replyNotificationMe)
        return listOf(
                addSetFocusRequest("forum", login),
                addRequest("add_entry", requestParams)
        )
    }

    fun addDeleteForumPostRequest(id: String, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("id", id)
        return listOf(
                addSetFocusRequest("forum", login),
                addRequest("delete_entry", requestParams)
        )
    }

    fun addForumExportSessionFileRequest(fileId: String, id: String, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("file_id", fileId)
        requestParams.addProperty("id", id)
        return listOf(
                addSetFocusRequest("forum", login),
                addRequest("export_session_file", requestParams)
        )
    }

}