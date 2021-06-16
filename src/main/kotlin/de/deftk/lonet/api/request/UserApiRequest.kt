package de.deftk.lonet.api.request

import de.deftk.lonet.api.LoNetClient
import de.deftk.lonet.api.model.Feature
import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.IUser
import de.deftk.lonet.api.model.Locale
import de.deftk.lonet.api.model.feature.ServiceType
import de.deftk.lonet.api.model.feature.filestorage.session.ISessionFile
import de.deftk.lonet.api.utils.PlatformUtil
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.put

class UserApiRequest(context: IRequestContext): OperatingScopeApiRequest(context) {

    fun addGetAutoLoginUrlRequest(disableLogout: Boolean? = null, disableReceptionOfQuickMessages: Boolean? = null, enslaveSession: Boolean? = null, locale: Locale? = null, pingMaster: Boolean? = null, sessionTimeout: Int? = null, targetData: JsonElement? = null, targetIframes: Boolean? = null, targetUrlPath: String? = null): List<Int> {
        ensureCapacity(2)
        val requestParams = buildJsonObject {
            if (disableLogout != null) put("disable_logout", disableLogout)
            if (disableReceptionOfQuickMessages != null) put("disable_reception_of_quick_messages", disableReceptionOfQuickMessages)
            if (enslaveSession != null) put("enslave_session", enslaveSession)
            if (locale != null) put("locale", LoNetClient.json.encodeToJsonElement(locale))
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
            put("service", LoNetClient.json.encodeToJsonElement(type))
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
            put("service", LoNetClient.json.encodeToJsonElement(type))
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

}