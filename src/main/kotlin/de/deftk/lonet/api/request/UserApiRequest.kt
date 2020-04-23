package de.deftk.lonet.api.request

import de.deftk.lonet.api.model.Feature
import de.deftk.lonet.api.model.User
import java.io.Serializable

class UserApiRequest(private val user: User) : OperatorApiRequest(user), Serializable {

    fun addGetAutoLoginUrlRequest(): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("trusts", user.getLogin()),
                addRequest("get_url_for_autologin", null) //TODO this method allows parameters
        )
    }

    fun addGetSystemNotificationsRequest(): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("messages", user.getLogin()),
                addRequest("get_messages", null)
        )
    }

    fun addGetAllTasksRequest(): List<Int> {
        val ids = mutableListOf<Int>()
        if (Feature.TASKS.isAvailable(user.permissions)) {
            ids.addAll(addGetTasksRequest())
        }
        user.getContext().getGroups().forEach { membership ->
            if (Feature.TASKS.isAvailable(membership.memberPermissions)) {
                ids.addAll(addGetTasksRequest(membership.getLogin()))
            }
        }
        return ids
    }

    fun addGetAllNotificationsRequest(): List<Int> {
        val ids = mutableListOf<Int>()
        if (Feature.BOARD.isAvailable(user.permissions)) {
            ids.addAll(addGetNotificationsRequest())
        }
        user.getContext().getGroups().forEach { membership ->
            if (Feature.BOARD.isAvailable(membership.memberPermissions)) {
                ids.addAll(addGetNotificationsRequest(membership.getLogin()))
            }
        }
        return ids
    }

}