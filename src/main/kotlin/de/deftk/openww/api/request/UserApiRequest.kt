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

    fun addGetAutoLoginUrlRequest(disableLogout: Boolean? = null, disableReceptionOfQuickMessages: Boolean? = null, enslaveSession: Boolean? = null, locale: Locale? = null, pingMaster: Boolean? = null, sessionTimeout: Int? = null, targetData: JsonElement? = null, targetIframes: Boolean? = null, targetUrlPath: String? = null): Int {
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
        ensureFocus(Focusable.TRUSTS, context.login)
        return addRequest("get_url_for_autologin", requestParams)
    }

    fun addGetProfileRequest(exportImage: Boolean?): Int {
        val params = buildJsonObject {
            if (exportImage != null)
                put("export_image", asApiBoolean(exportImage))
        }
        ensureFocus(Focusable.PROFILE, context.login)
        return addRequest("get_profile", params)
    }

    fun addSetProfileRequest(fullName: String?, firstName: String?, lastName: String?, homePostalCode: String?, homeCity: String?, homeState: String?, birthday: String?, emailAddress: String?, gender: Gender?, hobbies: String?, notes: String?, website: String?, company: String?, companyType: String?, subjects: String?, jobTitle: String?, visible: Boolean?, jobTitle2: String?, homePhone: String?, homeFax: String?, mobilePhone: String?, title: String?, image: ISessionFile?): Int {
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
        ensureFocus(Focusable.PROFILE, context.login)
        return addRequest("set_profile", params)
    }

    fun addExportProfileImageRequest(): Int {
        ensureFocus(Focusable.PROFILE, context.login)
        return addRequest("export_image", null)
    }

    fun addDeleteProfileImageRequest(): Int {
        ensureFocus(Focusable.PROFILE, context.login)
        return addRequest("delete_image", null)
    }

    fun addImportProfileImageRequest(id: String): Int {
        val params = buildJsonObject {
            put("id", id)
        }
        ensureFocus(Focusable.PROFILE, context.login)
        return addRequest("import_image", params)
    }

    fun addGetSystemNotificationsRequest(startId: Int? = null): Int {
        val requestParams = buildJsonObject {
            if (startId != null) put("start_id", startId)
        }
        ensureFocus(Focusable.MESSAGES, context.login)
        return addRequest("get_messages", requestParams)
    }

    fun addGetSystemNotificationSettingsRequest(): Int {
        ensureFocus(Focusable.MESSAGES, context.login)
        return addRequest("get_settings", null)
    }

    fun addSetSystemNotificationFacilitiesRequest(type: Int, digest: Boolean?, digestWeekly: Boolean?, mail: Boolean?, normal: Boolean?, push: Boolean?, qm: Boolean?, sms: Boolean?): Int {
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
        ensureFocus(Focusable.MESSAGES, context.login)
        return addRequest("set_facilities", requestParams)
    }

    fun addDeleteSystemNotificationRequest(id: Int): Int {
        val requestParams = buildJsonObject {
            put("id", id)
        }
        ensureFocus(Focusable.MESSAGES, context.login)
        return addRequest("delete_message", requestParams)
    }

    fun addAddSessionFileRequest(name: String, data: ByteArray): Int {
        val requestParams = buildJsonObject {
            put("name", name)
            put("data", PlatformUtil.base64EncodeToString(data))
        }
        ensureFocus(Focusable.SESSION_FILES, context.login)
        return addRequest("add_file", requestParams)
    }

    fun addAppendSessionFileRequest(id: String, data: ByteArray): Int {
        val requestParams = buildJsonObject {
            put("id", id)
            put("data", PlatformUtil.base64EncodeToString(data))
        }
        ensureFocus(Focusable.SESSION_FILES, context.login)
        return addRequest("append_file", requestParams)
    }

    fun addDeleteSessionFileRequest(id: String): Int {
        val requestParams = buildJsonObject {
            put("id", id)
        }
        ensureFocus(Focusable.SESSION_FILES, context.login)
        return addRequest("delete_file", requestParams)
    }

    fun addGetSessionFileRequest(id: String, limit: Int? = null, offset: Int? = null): Int {
        val requestParams = buildJsonObject {
            put("id", id)
            if (limit != null)
                put("limit", limit)
            if (offset != null)
                put("offset", offset)
        }
        ensureFocus(Focusable.SESSION_FILES, context.login)
        return addRequest("get_file", requestParams)
    }

    fun addGetSessionFileDownloadUrlRequest(id: String): Int {
        val requestParams = buildJsonObject {
            put("id", id)
        }
        ensureFocus(Focusable.SESSION_FILES, context.login)
        return addRequest("get_file_download_url", requestParams)
    }

    fun addGetSessionFileUploadUrlRequest(name: String): Int {
        val requestParams = buildJsonObject {
            put("name", name)
        }
        ensureFocus(Focusable.SESSION_FILES, context.login)
        return addRequest("get_file_upload_url", requestParams)
    }

    fun addRegisterServiceRequest(type: ServiceType, token: String, application: String? = null, generateSecret: String? = null, isOnline: Boolean? = null, managedObjects: String? = null, unmanagedPriority: Int? = null): Int {
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
        ensureFocus(Focusable.SETTINGS, context.login)
        return addRequest("register_service", requestParams)
    }

    fun addUnregisterServiceRequest(type: ServiceType, token: String): Int {
        val requestParams = buildJsonObject {
            put("service", WebWeaverClient.json.encodeToJsonElement(type))
            put("token", token)
        }
        ensureFocus(Focusable.SETTINGS, context.login)
        return addRequest("unregister_service", requestParams)
    }

    fun addGetAllTasksRequest(user: IUser): List<Int> {
        val ids = mutableListOf<Int>()
        if (Feature.TASKS.isAvailable(user.effectiveRights)) {
            ids.add(addGetTasksRequest())
        }
        user.getGroups().filter { Feature.TASKS.isAvailable(it.effectiveRights) }.forEach { group ->
            ids.add(addGetTasksRequest(group.login))
        }

        return ids
    }

    fun addGetAllBoardNotificationsRequest(user: IUser): List<Int> {
        val ids = mutableListOf<Int>()
        if (Feature.BOARD.isAvailable(user.effectiveRights)) {
            ids.add(addGetBoardNotificationsRequest())
        }
        user.getGroups().filter { Feature.BOARD.isAvailable(it.effectiveRights) }.forEach { group ->
            ids.add(addGetBoardNotificationsRequest(group.login))
        }

        return ids
    }

    fun addGetAllPupilBoardNotificationsRequest(user: IUser): List<Int> {
        val ids = mutableListOf<Int>()
        if (Feature.BOARD_PUPIL.isAvailable(user.effectiveRights)) {
            ids.add(addGetPupilBoardNotificationsRequest())
        }
        user.getGroups().filter { Feature.BOARD_PUPIL.isAvailable(it.effectiveRights) }.forEach { group ->
            ids.add(addGetPupilBoardNotificationsRequest(group.login))
        }

        return ids
    }

    fun addGetAllTeacherBoardNotificationsRequest(user: IUser): List<Int> {
        val ids = mutableListOf<Int>()
        if (Feature.BOARD_TEACHER.isAvailable(user.effectiveRights)) {
            ids.add(addGetTeacherBoardNotificationsRequest())
        }
        user.getGroups().filter { Feature.BOARD_TEACHER.isAvailable(it.effectiveRights) }.forEach { group ->
            ids.add(addGetTeacherBoardNotificationsRequest(group.login))
        }

        return ids
    }

    fun addGetMessengerUsersRequest(getMiniatures: Boolean? = null, onlineOnly: Boolean? = null): Int {
        val params = buildJsonObject {
            if (getMiniatures != null)
                put("get_miniatures", asApiBoolean(getMiniatures))
            if (onlineOnly != null)
                put("only_online", asApiBoolean(onlineOnly))
        }
        ensureFocus(Focusable.MESSENGER, context.login)
        return addRequest("get_users", params)
    }

    fun addSendQuickMessageRequest(login: String, importSessionFile: ISessionFile? = null, text: String? = null): Int {
        val params = buildJsonObject {
            put("login", login)
            if (importSessionFile != null)
                put("import_session_file", importSessionFile.id)
            if (text != null)
                put("text", text)
        }
        ensureFocus(Focusable.MESSENGER, context.login)
        return addRequest("send_quick_message", params)
    }

    fun addAddChatRequest(login: String): Int {
        val params = buildJsonObject {
            put("login", login)
        }
        ensureFocus(Focusable.MESSENGER, context.login)
        return addRequest("join_user", params)
    }

    fun addRemoveChatRequest(login: String): Int {
        val params = buildJsonObject {
            put("login", login)
        }
        ensureFocus(Focusable.MESSENGER, context.login)
        return addRequest("leave_user", params)
    }

    fun addGetHistoryRequest(exportSessionFile: Boolean? = null, startId: Int? = null): Int {
        val params = buildJsonObject {
            if (exportSessionFile != null)
                put("export_session_file", asApiBoolean(exportSessionFile))
            if (startId != null)
                put("start_id", startId)
        }
        ensureFocus(Focusable.MESSENGER, context.login)
        return addRequest("get_history", params)
    }

    fun addBlockMessengerUserRequest(login: String): Int {
        val params = buildJsonObject {
            put("login", login)
        }
        ensureFocus(Focusable.MESSENGER, context.login)
        return addRequest("block_user", params)
    }

    fun addUnblockMessengerUserRequest(login: String): Int {
        val params = buildJsonObject {
            put("login", login)
        }
        ensureFocus(Focusable.MESSENGER, context.login)
        return addRequest("unblock_user", params)
    }

    fun addGetMessengerBlocklistRequest(): Int {
        ensureFocus(Focusable.MESSENGER, context.login)
        return addRequest("get_blocklist", null)
    }

    fun addGetNotesRequest(): Int {
        ensureFocus(Focusable.NOTES, context.login)
        return addRequest("get_entries", null)
    }

    fun addAddNoteRequest(text: String, title: String, color: NoteColor? = null): Int {
        val params = buildJsonObject {
            put("text", text)
            put("title", title)
            if (color != null)
                put("color", WebWeaverClient.json.encodeToJsonElement(color))
        }
        ensureFocus(Focusable.NOTES, context.login)
        return addRequest("add_entry", params)
    }

    fun addSetNoteRequest(id: String, text: String, title: String, color: NoteColor): Int {
        val params = buildJsonObject {
            put("id", id)
            put("text", text)
            put("title", title)
            put("color", WebWeaverClient.json.encodeToJsonElement(color))
        }
        ensureFocus(Focusable.NOTES, context.login)
        return addRequest("set_entry", params)
    }

    fun addDeleteNoteRequest(id: String): Int {
        val params = buildJsonObject {
            put("id", id)
        }
        ensureFocus(Focusable.NOTES, context.login)
        return addRequest("delete_entry", params)
    }

}