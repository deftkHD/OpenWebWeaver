package de.deftk.lonet.api.request

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Feature
import de.deftk.lonet.api.model.Locale
import de.deftk.lonet.api.model.User
import java.io.Serializable

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

}