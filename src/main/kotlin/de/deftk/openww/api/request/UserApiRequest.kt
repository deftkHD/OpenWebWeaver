package de.deftk.openww.api.request

import de.deftk.openww.api.WebWeaverClient
import de.deftk.openww.api.model.Feature
import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.IUser
import de.deftk.openww.api.model.Locale
import de.deftk.openww.api.model.feature.ServiceType
import de.deftk.openww.api.model.feature.filestorage.session.ISessionFile
import de.deftk.openww.api.model.feature.notes.NoteColor
import de.deftk.openww.api.utils.PlatformUtil
import kotlinx.serialization.json.*

class UserApiRequest(context: IRequestContext): OperatingScopeApiRequest(context) {

    fun addGetAutoLoginUrlRequest(disableLogout: Boolean? = null, disableReceptionOfQuickMessages: Boolean? = null, enslaveSession: Boolean? = null, locale: Locale? = null, pingMaster: Boolean? = null, sessionTimeout: Int? = null, targetData: JsonElement? = null, targetIframes: Boolean? = null, targetUrlPath: String? = null): List<Int> {
        ensureCapacity(2)
        val requestParams = buildJsonObject {
            if (disableLogout != null) put("disable_logout", disableLogout)
            if (disableReceptionOfQuickMessages != null) put("disable_reception_of_quick_messages", disableReceptionOfQuickMessages)
            if (enslaveSession != null) put("enslave_session", enslaveSession)
            if (locale != null) put("locale", WebWeaverClient.json.encodeToJsonElement(locale))
            if (pingMaster != null) put("ping_master", pingMaster)
            if (sessionTimeout != null) put("session_timeout", sessionTimeout)
            if (targetData != null) put("target_data", targetData)
            if (targetIframes != null) put("target_iframes", targetIframes)
            if (targetUrlPath != null) put("target_url_path", targetUrlPath)
        }
        return listOf(
            addSetFocusRequest(Focusable.TRUSTS, context.login),
            addRequest("get_url_for_autologin", requestParams)
        )
    }

    fun addGetSystemNotificationsRequest(startId: Int? = null): List<Int> {
        ensureCapacity(2)
        val requestParams = buildJsonObject {
            if (startId != null) put("start_id", startId)
        }
        return listOf(
            addSetFocusRequest(Focusable.MESSAGES, context.login),
            addRequest("get_messages", requestParams)
        )
    }

    fun addDeleteSystemNotificationRequest(id: Int): List<Int> {
        ensureCapacity(2)
        val requestParams = buildJsonObject {
            put("id", id)
        }
        return listOf(
            addSetFocusRequest(Focusable.MESSAGES, context.login),
            addRequest("delete_message", requestParams)
        )
    }

    fun addAddSessionFileRequest(name: String, data: ByteArray): List<Int> {
        ensureCapacity(2)
        val requestParams = buildJsonObject {
            put("name", name)
            put("data", PlatformUtil.base64EncodeToString(data))
        }
        return listOf(
            addSetFocusRequest(Focusable.SESSION_FILES, context.login),
            addRequest("add_file", requestParams)
        )
    }

    fun addAppendSessionFileRequest(id: String, data: ByteArray): List<Int> {
        ensureCapacity(2)
        val requestParams = buildJsonObject {
            put("id", id)
            put("data", PlatformUtil.base64EncodeToString(data))
        }
        return listOf(
            addSetFocusRequest(Focusable.SESSION_FILES, context.login),
            addRequest("append_file", requestParams)
        )
    }

    fun addDeleteSessionFileRequest(id: String): List<Int> {
        ensureCapacity(2)
        val requestParams = buildJsonObject {
            put("id", id)
        }
        return listOf(
            addSetFocusRequest(Focusable.SESSION_FILES, context.login),
            addRequest("delete_file", requestParams)
        )
    }

    fun addGetSessionFileRequest(id: String, limit: Int? = null, offset: Int? = null): List<Int> {
        ensureCapacity(2)
        val requestParams = buildJsonObject {
            put("id", id)
            if (limit != null)
                put("limit", limit)
            if (offset != null)
                put("offset", offset)
        }
        return listOf(
            addSetFocusRequest(Focusable.SESSION_FILES, context.login),
            addRequest("get_file", requestParams)
        )
    }

    fun addGetSessionFileDownloadUrlRequest(id: String): List<Int> {
        ensureCapacity(2)
        val requestParams = buildJsonObject {
            put("id", id)
        }
        return listOf(
            addSetFocusRequest(Focusable.SESSION_FILES, context.login),
            addRequest("get_file_download_url", requestParams)
        )
    }

    fun addGetSessionFileUploadUrlRequest(name: String): List<Int> {
        ensureCapacity(2)
        val requestParams = buildJsonObject {
            put("name", name)
        }
        return listOf(
            addSetFocusRequest(Focusable.SESSION_FILES, context.login),
            addRequest("get_file_upload_url", requestParams)
        )
    }

    fun addRegisterServiceRequest(type: ServiceType, token: String, application: String? = null, generateSecret: String? = null, isOnline: Boolean? = null, managedObjects: String? = null, unmanagedPriority: Int? = null): List<Int> {
        ensureCapacity(2)
        val requestParams = buildJsonObject {
            put("service", WebWeaverClient.json.encodeToJsonElement(type))
            put("token", token)
            if (application != null)
                put("application", application)
            if (generateSecret != null)
                put("generate_secret", generateSecret)
            if (isOnline != null)
                put("is_online", isOnline)
            if (managedObjects != null)
                put("managed_objects", managedObjects)
            if (unmanagedPriority != null)
                put("unmanaged_priority", unmanagedPriority)
        }
        return listOf(
            addSetFocusRequest(Focusable.SETTINGS, context.login),
            addRequest("register_service", requestParams)
        )
    }

    fun addUnregisterServiceRequest(type: ServiceType, token: String): List<Int> {
        ensureCapacity(2)
        val requestParams = buildJsonObject {
            put("service", WebWeaverClient.json.encodeToJsonElement(type))
            put("token", token)
        }
        return listOf(
            addSetFocusRequest(Focusable.SETTINGS, context.login),
            addRequest("unregister_service", requestParams)
        )
    }

    fun addGetAllTasksRequest(user: IUser): List<Int> {
        val ids = mutableListOf<Int>()
        if (Feature.TASKS.isAvailable(user.effectiveRights)) {
            ids.addAll(addGetTasksRequest())
        }
        user.getGroups().filter { Feature.TASKS.isAvailable(it.effectiveRights) }.forEach { group ->
            ids.addAll(addGetTasksRequest(group.login))
        }

        return ids
    }

    fun addGetAllBoardNotificationsRequest(user: IUser): List<Int> {
        val ids = mutableListOf<Int>()
        if (Feature.BOARD.isAvailable(user.effectiveRights)) {
            ids.addAll(addGetBoardNotificationsRequest())
        }
        user.getGroups().filter { Feature.BOARD.isAvailable(it.effectiveRights) }.forEach { group ->
            ids.addAll(addGetBoardNotificationsRequest(group.login))
        }

        return ids
    }

    fun addGetAllPupilBoardNotificationsRequest(user: IUser): List<Int> {
        val ids = mutableListOf<Int>()
        if (Feature.BOARD_PUPIL.isAvailable(user.effectiveRights)) {
            ids.addAll(addGetPupilBoardNotificationsRequest())
        }
        user.getGroups().filter { Feature.BOARD_PUPIL.isAvailable(it.effectiveRights) }.forEach { group ->
            ids.addAll(addGetPupilBoardNotificationsRequest(group.login))
        }

        return ids
    }

    fun addGetAllTeacherBoardNotificationsRequest(user: IUser): List<Int> {
        val ids = mutableListOf<Int>()
        if (Feature.BOARD_TEACHER.isAvailable(user.effectiveRights)) {
            ids.addAll(addGetTeacherBoardNotificationsRequest())
        }
        user.getGroups().filter { Feature.BOARD_TEACHER.isAvailable(it.effectiveRights) }.forEach { group ->
            ids.addAll(addGetTeacherBoardNotificationsRequest(group.login))
        }

        return ids
    }

    fun addGetMessengerUsersRequest(getMiniatures: Boolean? = null, onlineOnly: Boolean? = null): List<Int> {
        ensureCapacity(2)
        val params = buildJsonObject {
            if (getMiniatures != null)
                put("get_miniatures", asApiBoolean(getMiniatures))
            if (onlineOnly != null)
                put("only_online", asApiBoolean(onlineOnly))
        }
        return listOf(
            addSetFocusRequest(Focusable.MESSENGER, context.login),
            addRequest("get_users", params)
        )
    }

    fun addSendQuickMessageRequest(login: String, importSessionFile: ISessionFile? = null, text: String? = null): List<Int> {
        ensureCapacity(2)
        val params = buildJsonObject {
            put("login", login)
            if (importSessionFile != null)
                put("import_session_file", importSessionFile.id)
            if (text != null)
                put("text", text)
        }
        return listOf(
            addSetFocusRequest(Focusable.MESSENGER, context.login),
            addRequest("send_quick_message", params)
        )
    }

    fun addAddChatRequest(login: String): List<Int> {
        ensureCapacity(2)
        val params = buildJsonObject {
            put("login", login)
        }
        return listOf(
            addSetFocusRequest(Focusable.MESSENGER, context.login),
            addRequest("join_user", params)
        )
    }

    fun addRemoveChatRequest(login: String): List<Int> {
        ensureCapacity(2)
        val params = buildJsonObject {
            put("login", login)
        }
        return listOf(
            addSetFocusRequest(Focusable.MESSENGER, context.login),
            addRequest("leave_user", params)
        )
    }

    fun addGetHistoryRequest(exportSessionFile: Boolean? = null, startId: Int? = null): List<Int> {
        ensureCapacity(2)
        val params = buildJsonObject {
            if (exportSessionFile != null)
                put("export_session_file", asApiBoolean(exportSessionFile))
            if (startId != null)
                put("start_id", startId)
        }
        return listOf(
            addSetFocusRequest(Focusable.MESSENGER, context.login),
            addRequest("get_history", params)
        )
    }

    fun addGetNotesRequest(): List<Int> {
        ensureCapacity(2)
        return listOf(
            addSetFocusRequest(Focusable.NOTES, context.login),
            addRequest("get_entries", null)
        )
    }

    fun addAddNoteRequest(text: String, title: String, color: NoteColor? = null): List<Int> {
        ensureCapacity(2)
        val params = buildJsonObject {
            put("text", text)
            put("title", title)
            if (color != null)
                put("color", WebWeaverClient.json.encodeToJsonElement(color))
        }
        return listOf(
            addSetFocusRequest(Focusable.NOTES, context.login),
            addRequest("add_entry", params)
        )
    }

    fun addSetNoteRequest(id: String, text: String, title: String, color: NoteColor?): List<Int> {
        ensureCapacity(2)
        val params = buildJsonObject {
            put("id", id)
            put("text", text)
            put("title", title)
            put("color", if (color != null) WebWeaverClient.json.encodeToJsonElement(color) else JsonNull)
        }
        return listOf(
            addSetFocusRequest(Focusable.NOTES, context.login),
            addRequest("set_entry", params)
        )
    }

    fun addDeleteNoteRequest(id: String): List<Int> {
        ensureCapacity(2)
        val params = buildJsonObject {
            put("id", id)
        }
        return listOf(
            addSetFocusRequest(Focusable.NOTES, context.login),
            addRequest("delete_entry", params)
        )
    }

}