package de.deftk.openww.api.request

import de.deftk.openww.api.WebWeaverClient
import de.deftk.openww.api.model.Feature
import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.IUser
import de.deftk.openww.api.model.Locale
import de.deftk.openww.api.model.feature.ServiceType
import de.deftk.openww.api.model.feature.contacts.Gender
import de.deftk.openww.api.model.feature.filestorage.session.ISessionFile
import de.deftk.openww.api.model.feature.notes.NoteColor
import de.deftk.openww.api.utils.PlatformUtil
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

    fun addGetProfileRequest(exportImage: Boolean?): List<Int> {
        ensureCapacity(2)
        val params = buildJsonObject {
            if (exportImage != null)
                put("export_image", asApiBoolean(exportImage))
        }
        return listOf(
            addSetFocusRequest(Focusable.PROFILE, context.login),
            addRequest("get_profile", params)
        )
    }

    fun addSetProfileRequest(fullName: String?, firstName: String?, lastName: String?, homePostalCode: String?, homeCity: String?, homeState: String?, birthday: String?, emailAddress: String?, gender: Gender?, hobbies: String?, notes: String?, website: String?, company: String?, companyType: String?, subjects: String?, jobTitle: String?, visible: Boolean?, jobTitle2: String?, homePhone: String?, homeFax: String?, mobilePhone: String?, title: String?, image: ISessionFile?): List<Int> {
        ensureCapacity(2)
        val params = buildJsonObject {
            if (fullName != null)
                put("fullname", fullName)
            if (firstName != null)
                put("firstname", firstName)
            if (lastName != null)
                put("lastname", lastName)
            if (homePostalCode != null)
                put("homepostalcode", homePostalCode)
            if (homeCity != null)
                put("homecity", homeCity)
            if (homeState != null)
                put("homestate", homeState)
            if (birthday != null)
                put("birthday", birthday)
            if (emailAddress != null)
                put("emailaddress", emailAddress)
            if (gender != null)
                put("gender", WebWeaverClient.json.encodeToJsonElement(gender))
            if (hobbies != null)
                put("hobby", hobbies)
            if (notes != null)
                put("notes", notes)
            if (website != null)
                put("webpage", website)
            if (company != null)
                put("company", company)
            if (companyType != null)
                put("companytype", companyType)
            if (subjects != null)
                put("subjects", subjects)
            if (jobTitle != null)
                put("jobtitle", jobTitle)
            if (visible != null)
                put("visible", asApiBoolean(visible))
            if (jobTitle2 != null)
                put("jobtitle2", jobTitle2)
            if (homePhone != null)
                put("homephone", homePhone)
            if (homeFax != null)
                put("homefax", homeFax)
            if (mobilePhone != null)
                put("mobilephone", mobilePhone)
            if (title != null)
                put("title", title)
            if (image != null)
                put("image", image.id)
        }
        return listOf(
            addSetFocusRequest(Focusable.PROFILE, context.login),
            addRequest("set_profile", params)
        )
    }

    fun addExportProfileImageRequest(): List<Int> {
        ensureCapacity(2)
        return listOf(
            addSetFocusRequest(Focusable.PROFILE, context.login),
            addRequest("export_image", null)
        )
    }

    fun addDeleteProfileImageRequest(): List<Int> {
        ensureCapacity(2)
        return listOf(
            addSetFocusRequest(Focusable.PROFILE, context.login),
            addRequest("delete_image", null)
        )
    }

    fun addImportProfileImageRequest(id: String): List<Int> {
        ensureCapacity(2)
        val params = buildJsonObject {
            put("id", id)
        }
        return listOf(
            addSetFocusRequest(Focusable.PROFILE, context.login),
            addRequest("import_image", params)
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

    fun addGetSystemNotificationSettingsRequest(): List<Int> {
        ensureCapacity(2)
        return listOf(
            addSetFocusRequest(Focusable.MESSAGES, context.login),
            addRequest("get_settings", null)
        )
    }

    fun addSetSystemNotificationFacilitiesRequest(type: Int, digest: Boolean?, digestWeekly: Boolean?, mail: Boolean?, normal: Boolean?, push: Boolean?, qm: Boolean?, sms: Boolean?): List<Int> {
        ensureCapacity(2)
        val requestParams = buildJsonObject {
            put("type", type)
            if (digest != null)
                put("digest", asApiBoolean(digest))
            if (digestWeekly != null)
                put("digest_weekly", asApiBoolean(digestWeekly))
            if (mail != null)
                put("mail", asApiBoolean(mail))
            if (normal != null)
                put("normal", asApiBoolean(normal))
            if (push != null)
                put("push", asApiBoolean(push))
            if (qm != null)
                put("qm", asApiBoolean(qm))
            if (sms != null)
                put("sms", asApiBoolean(sms))
        }
        return listOf(
            addSetFocusRequest(Focusable.MESSAGES, context.login),
            addRequest("set_facilities", requestParams)
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

    fun addSetNoteRequest(id: String, text: String, title: String, color: NoteColor): List<Int> {
        ensureCapacity(2)
        val params = buildJsonObject {
            put("id", id)
            put("text", text)
            put("title", title)
            put("color", WebWeaverClient.json.encodeToJsonElement(color))
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