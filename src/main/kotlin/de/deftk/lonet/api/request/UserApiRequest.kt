package de.deftk.lonet.api.request

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Feature
import de.deftk.lonet.api.model.Locale
import de.deftk.lonet.api.model.User
import de.deftk.lonet.api.model.abstract.IUser
import java.io.Serializable
import java.util.*

class UserApiRequest(private val user: User) : OperatorApiRequest(user), Serializable {

    fun addGetAutoLoginUrlRequest(disableLogout: Boolean? = null, disableReceptionOfQuickMessages: Boolean? = null, enslaveSession: Boolean? = null, locale: Locale? = null, pingMaster: Boolean? = null, sessionTimeout: Int? = null, targetData: Any? = null, targetIframes: Boolean? = null, targetUrlPath: String? = null): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        if (disableLogout != null) requestProperties.addProperty("disable_logout", disableLogout)
        if (disableReceptionOfQuickMessages != null) requestProperties.addProperty("disable_reception_of_quick_messages", disableReceptionOfQuickMessages)
        if (enslaveSession != null) requestProperties.addProperty("enslave_session", enslaveSession)
        if (locale != null) requestProperties.addProperty("locale", locale.code)
        if (pingMaster != null) requestProperties.addProperty("ping_master", pingMaster)
        if (sessionTimeout != null) requestProperties.addProperty("session_timeout", sessionTimeout)
        if (targetData != null) TODO("don't know how to handle mixed data")
        if (targetIframes != null) requestProperties.addProperty("target_iframes", targetIframes)
        if (targetUrlPath != null) requestProperties.addProperty("target_url_path", targetUrlPath)
        return listOf(
                addSetFocusRequest("trusts", user.getLogin()),
                addRequest("get_url_for_autologin", requestProperties)
        )
    }

    fun addGetSystemNotificationsRequest(startId: Int? = null): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        if (startId != null) requestProperties.addProperty("start_id", startId)
        return listOf(
                addSetFocusRequest("messages", user.getLogin()),
                addRequest("get_messages", requestProperties)
        )
    }

    fun addDeleteSystemNotificationRequest(id: Int): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        requestProperties.addProperty("id", id)
        return listOf(
                addSetFocusRequest("messages", user.getLogin()),
                addRequest("delete_message", requestProperties)
        )
    }

    fun addGetAllTasksRequest(): List<Int> {
        val ids = mutableListOf<Int>()
        if (Feature.TASKS.isAvailable(user.effectiveRights)) {
            ids.addAll(addGetTasksRequest())
        }
        user.getContext().getGroups().forEach { membership ->
            if (Feature.TASKS.isAvailable(membership.effectiveRights)) {
                ids.addAll(addGetTasksRequest(membership.getLogin()))
            }
        }
        return ids
    }

    fun addGetAllNotificationsRequest(): List<Int> {
        val ids = mutableListOf<Int>()
        if (Feature.BOARD.isAvailable(user.effectiveRights)) {
            ids.addAll(addGetBoardNotificationsRequest())
        }
        user.getContext().getGroups().forEach { membership ->
            if (Feature.BOARD.isAvailable(membership.effectiveRights)) {
                ids.addAll(addGetBoardNotificationsRequest(membership.getLogin()))
            }
        }
        return ids
    }

    fun addGetAllTeacherNotificationsRequest(): List<Int> {
        val ids = mutableListOf<Int>()
        if (Feature.BOARD.isAvailable(user.effectiveRights)) {
            ids.addAll(addGetTeacherBoardNotificationsRequest())
        }
        user.getContext().getGroups().forEach { membership ->
            if (Feature.BOARD.isAvailable(membership.effectiveRights)) {
                ids.addAll(addGetTeacherBoardNotificationsRequest(membership.getLogin()))
            }
        }
        return ids
    }

    fun addGetAllPupilNotificationsRequest(): List<Int> {
        val ids = mutableListOf<Int>()
        if (Feature.BOARD.isAvailable(user.effectiveRights)) {
            ids.addAll(addGetPupilBoardNotificationsRequest())
        }
        user.getContext().getGroups().forEach { membership ->
            if (Feature.BOARD.isAvailable(membership.effectiveRights)) {
                ids.addAll(addGetPupilBoardNotificationsRequest(membership.getLogin()))
            }
        }
        return ids
    }

    fun addAddSessionFileRequest(name: String, data: ByteArray): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("name", name)
        requestParams.addProperty("data", Base64.getEncoder().encodeToString(data))
        return listOf(
                addSetFocusRequest("session_files", user.getLogin()),
                addRequest("add_file", requestParams)
        )
    }

    fun addAppendSessionFileRequest(id: String, data: ByteArray): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("id", id)
        requestParams.addProperty("data", Base64.getEncoder().encodeToString(data))
        return listOf(
                addSetFocusRequest("session_files", user.getLogin()),
                addRequest("append_file", requestParams)
        )
    }

    fun addDeleteSessionFileRequest(id: String): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("id", id)
        return listOf(
                addSetFocusRequest("session_files", user.getLogin()),
                addRequest("delete_file", requestParams)
        )
    }

    fun addGetSessionFileRequest(id: String, limit: Int? = null, offset: Int? = null): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("id", id)
        if (limit != null)
            requestParams.addProperty("limit", limit)
        if (offset != null)
            requestParams.addProperty("offset", offset)
        return listOf(
                addSetFocusRequest("session_files", user.getLogin()),
                addRequest("get_file", requestParams)
        )
    }

    fun addGetSessionFileDownloadUrlRequest(id: String): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("id", id)
        return listOf(
                addSetFocusRequest("session_files", user.getLogin()),
                addRequest("get_file_download_url", requestParams)
        )
    }

    fun addGetSessionFileUploadUrlRequest(name: String): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("name", name)
        return listOf(
                addSetFocusRequest("session_files", user.getLogin()),
                addRequest("get_file_upload_url", requestParams)
        )
    }

    fun addRegisterServiceRequest(type: IUser.ServiceType, token: String, application: String? = null, generateSecret: String? = null, isOnline: Boolean? = null, managedObjects: String? = null, unmanagedPriority: Int? = null): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("service", type.id)
        requestParams.addProperty("token", token)
        if (application != null)
            requestParams.addProperty("application", application)
        if (generateSecret != null)
            requestParams.addProperty("generate_secret", generateSecret)
        if (isOnline != null)
            requestParams.addProperty("is_online", isOnline)
        if (managedObjects != null)
            requestParams.addProperty("managed_objects", managedObjects)
        if (unmanagedPriority != null)
            requestParams.addProperty("unmanaged_priority", unmanagedPriority)
        return listOf(
                addSetFocusRequest("settings", user.getLogin()),
                addRequest("register_service", requestParams)
        )
    }

    fun addUnregisterServiceRequest(type: IUser.ServiceType, token: String): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("service", type.id)
        requestParams.addProperty("token", token)
        return listOf(
                addSetFocusRequest("settings", user.getLogin()),
                addRequest("unregister_service", requestParams)
        )
    }

}