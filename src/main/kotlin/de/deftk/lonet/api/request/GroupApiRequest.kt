package de.deftk.lonet.api.request

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Group
import de.deftk.lonet.api.model.feature.courselets.TemplatePackage
import de.deftk.lonet.api.model.feature.forum.ForumPostIcon
import java.io.Serializable
import java.util.*

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

    fun addGetWikiPageRequest(name: String? = null, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        if (name != null)
            requestParams.addProperty("name", name)
        return listOf(
                addSetFocusRequest("wiki", login),
                addRequest("get_page", requestParams)
        )
    }

    fun addGetCourseletsStateRequest(login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("courselets", login),
                addRequest("get_state", null)
        )
    }

    fun addGetCourseletsConfigurationRequest(login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("courselets", login),
                addRequest("get_configuration", null)
        )
    }

    fun addGetCourseletProgressRequest(ids: List<Int>? = null, since: Date? = null, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        if (ids != null) {
            val array = JsonArray()
            ids.forEach { array.add(it) }
            requestParams.add("ids", array)
        }
        if (since != null)
            requestParams.addProperty("since", since.time / 1000)
        return listOf(
                addSetFocusRequest("courselets", login),
                addRequest("get_progress", requestParams)
        )
    }

    fun addAddCourseletResultRequest(id: Int, pageId: String, score: Int? = null, time: Date? = null, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("id", id)
        requestParams.addProperty("page_id", pageId)
        if (score != null)
            requestParams.addProperty("score", score)
        if (time != null)
            requestParams.addProperty("time", time.time / 1000)
        return listOf(
                addSetFocusRequest("courselets", login),
                addRequest("add_result", requestParams)
        )
    }

    fun addSetCourseletSuspendDataRequest(id: Int, suspendData: String, ifLatest: Int? = null, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("id", id)
        requestParams.addProperty("suspend_data", suspendData)
        if (ifLatest != null)
            requestParams.addProperty("if_latest", ifLatest)
        return listOf(
                addSetFocusRequest("courselets", login),
                addRequest("set_suspend_data", requestParams)
        )
    }

    fun addGetCourseletResultsRequest(id: Int, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("id", id)
        return listOf(
                addSetFocusRequest("courselets", login),
                addRequest("get_results", requestParams)
        )
    }

    fun addDeleteCourseletResultsRequest(id: Int, pageId: String? = null, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("id", id)
        if (pageId != null)
            requestParams.addProperty("page_id", pageId)
        return listOf(
                addSetFocusRequest("courselets", login),
                addRequest("delete_results", requestParams)
        )
    }

    fun addAddCourseletBookmarkRequest(id: Int, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("id", id)
        return listOf(
                addSetFocusRequest("courselets", login),
                addRequest("add_bookmark", requestParams)
        )
    }

    fun addDeleteCourseletBookmarkRequest(id: Int, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("id", id)
        return listOf(
                addSetFocusRequest("courselets", login),
                addRequest("delete_bookmark", requestParams)
        )
    }

    fun addExportCourseletRuntimeRequest(login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("courselets", login),
                addRequest("export_runtime", null)
        )
    }

    fun addGetTemplatesRequest(login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("courselets", login),
                addRequest("get_templates", null)
        )
    }

    fun addExportTemplateRequest(id: String, pkg: TemplatePackage? = null, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("id", id)
        if (pkg != null)
            requestParams.addProperty("package", pkg.id)
        return listOf(
                addSetFocusRequest("courselets", login),
                addRequest("export_template", requestParams)
        )
    }

    fun addGetCourseletMappingsRequest(login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("courselets", login),
                addRequest("get_mappings", null)
        )
    }

    fun addAddCourseletMappingRequest(name: String, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("name", name)
        return listOf(
                addSetFocusRequest("courselets", login),
                addRequest("add_mapping", requestParams)
        )
    }

    fun addSetCourseletMappingRequest(id: Int, name: String? = null, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("id", id)
        requestParams.addProperty("name", name)
        return listOf(
                addSetFocusRequest("courselets", login),
                addRequest("set_mapping", requestParams)
        )
    }

    fun addDeleteCourseletMappingRequest(id: Int, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("id", id)
        return listOf(
                addSetFocusRequest("courselets", login),
                addRequest("delete_mapping", requestParams)
        )
    }

    fun addGetCourseletsRequest(login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("courselets", login),
                addRequest("get_courselets", null)
        )
    }

    fun addImportCourseletRequest(id: String, isVisible: Boolean? = null, mapping: Int? = null, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("id", id)
        if (isVisible != null)
            requestParams.addProperty("is_visible", asApiBoolean(isVisible))
        if (mapping != null)
            requestParams.addProperty("mapping", mapping)
        return listOf(
                addSetFocusRequest("courselets", login),
                addRequest("import_courselet", requestParams)
        )
    }

    fun addExportCourseletRequest(id: String, pkg: TemplatePackage? = null, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("id", id)
        if (pkg != null)
            requestParams.addProperty("pkg", pkg.id)
        return listOf(
                addSetFocusRequest("courselets", login),
                addRequest("export_courselet", requestParams)
        )
    }

    fun addDeleteCourseletRequest(id: String, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("id", id)
        return listOf(
                addSetFocusRequest("courselets", login),
                addRequest("delete_courselet", requestParams)
        )
    }


}