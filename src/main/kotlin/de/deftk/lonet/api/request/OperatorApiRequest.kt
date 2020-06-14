package de.deftk.lonet.api.request

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.abstract.AbstractOperator
import de.deftk.lonet.api.model.feature.files.SearchMode
import de.deftk.lonet.api.response.ApiResponse

open class OperatorApiRequest(val operator: AbstractOperator) : ApiRequest() {

    fun addGetFileStorageStateRequest(login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("files", login),
                addRequest("get_state", null)
        )
    }

    //TODO implement
    fun addGetFileStorageSettingsRequest(login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("files", login),
                addRequest("get_settings", null)
        )
    }

    //TODO implement
    fun addSetFileStorageSettingsRequest(selfUploadNotification: Boolean? = null, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        if (selfUploadNotification != null) requestProperties.addProperty("upload_notification_me", asApiBoolean(selfUploadNotification))
        return listOf(
                addSetFocusRequest("files", login),
                addRequest("set_settings", requestProperties)
        )
    }

    fun addGetFileStorageFilesRequest(folderId: String? = null, getFiles: Boolean? = null, getFolders: Boolean? = null, getRoot: Boolean? = null, limit: Int? = null, offset: Int? = null, recursive: Boolean? = null, searchMode: SearchMode? = null, searchString: String? = null, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        if (folderId != null) requestProperties.addProperty("folder_id", folderId)
        requestProperties.addProperty("get_file_download_url", 0)
        if (getFiles != null) requestProperties.addProperty("get_files", asApiBoolean(getFiles))
        if (getFolders != null) requestProperties.addProperty("get_folders", asApiBoolean(getFolders))
        if (getRoot != null) requestProperties.addProperty("get_root", asApiBoolean(getRoot))
        if (limit != null) requestProperties.addProperty("limit", limit)
        if (offset != null) requestProperties.addProperty("offset", offset)
        if (recursive != null) requestProperties.addProperty("recursive", asApiBoolean(recursive))
        if (searchMode != null) requestProperties.addProperty("search_option", searchMode.id)
        if (searchString != null) requestProperties.addProperty("search_string", searchString)
        return listOf(
                addSetFocusRequest("files", login),
                addRequest("get_entries", requestProperties)
        )
    }

    fun addGetFileDownloadUrl(fileId: String, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        requestProperties.addProperty("id", fileId)
        return listOf(
                addSetFocusRequest("files", login),
                addRequest("get_file_download_url", requestProperties)
        )
    }

    fun addUpdateFileRequest(fileId: String, description: String? = null, name: String? = null, parentId: String? = null, selfDownloadNotification: Boolean? = null, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        requestProperties.addProperty("id", fileId)
        if (description != null) requestProperties.addProperty("description", description)
        if (name != null) requestProperties.addProperty("name", name)
        if (parentId != null) requestProperties.addProperty("folder_id", name)
        if (selfDownloadNotification != null) requestProperties.addProperty("download_notification_me", asApiBoolean(selfDownloadNotification))
        return listOf(
                addSetFocusRequest("files", login),
                addRequest("set_file", requestProperties)
        )
    }

    fun addDeleteFileRequest(fileId: String, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        requestProperties.addProperty("id", fileId)
        return listOf(
                addSetFocusRequest("files", login),
                addRequest("delete_file", requestProperties)
        )
    }

    fun addAddFolderRequest(parentId: String, name: String, description: String? = null, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        requestProperties.addProperty("folder_id", parentId)
        requestProperties.addProperty("name", name)
        if (description != null) requestProperties.addProperty("description", description)
        return listOf(
                addSetFocusRequest("files", login),
                addRequest("add_folder", requestProperties)
        )
    }

    fun addUpdateFolderRequest(folderId: String, description: String? = null, name: String? = null, readable: Boolean? = null, writable: Boolean? = null, selfUploadNotification: Boolean? = null, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        requestProperties.addProperty("id", folderId)
        if (description != null) requestProperties.addProperty("description", description)
        if (name != null) requestProperties.addProperty("name", name)
        if (readable != null) requestProperties.addProperty("readable", asApiBoolean(readable))
        if (writable != null) requestProperties.addProperty("writable", asApiBoolean(writable))
        if (selfUploadNotification != null) requestProperties.addProperty("upload_notification_me", asApiBoolean(selfUploadNotification))
        return listOf(
                addSetFocusRequest("files", login),
                addRequest("set_folder", requestProperties)
        )
    }

    fun addDeleteFolderRequest(folderId: String, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        requestProperties.addProperty("id", folderId)
        return listOf(
                addSetFocusRequest("files", login),
                addRequest("delete_folder", requestProperties)
        )
    }

    fun addGetEmailStateRequest(login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("mailbox", login),
                addRequest("get_state", null)
        )
    }

    fun addGetEmailFoldersRequest(login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("mailbox", login),
                addRequest("get_folders", null)
        )
    }

    fun addSendEmailRequest(to: String, subject: String, plainBody: String, text: String?, bcc: String?, cc: String?, login: String = operator.getLogin()): List<Int> {
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
                addSetFocusRequest("mailbox", login),
                addRequest("send_mail", requestParams)
        )
    }

    fun addGetTasksRequest(login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("tasks", login),
                addRequest("get_entries", null)
        )
    }

    fun addGetNotificationsRequest(login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("board", login),
                addRequest("get_entries", null)
        )
    }

    fun fireRequest(overwriteCache: Boolean = false): ApiResponse {
        return fireRequest(operator.getContext(), overwriteCache)
    }

}