package de.deftk.lonet.api.model

import com.google.gson.JsonObject
import de.deftk.lonet.api.LoNet
import de.deftk.lonet.api.model.abstract.AbstractOperator
import de.deftk.lonet.api.model.abstract.IContext
import de.deftk.lonet.api.model.abstract.IManageable
import de.deftk.lonet.api.model.abstract.IUser
import de.deftk.lonet.api.model.feature.Notification
import de.deftk.lonet.api.model.feature.SystemNotification
import de.deftk.lonet.api.model.feature.Task
import de.deftk.lonet.api.request.UserApiRequest
import de.deftk.lonet.api.response.ApiResponse
import de.deftk.lonet.api.response.ResponseUtil
import java.io.Serializable

class User(login: String, name: String, type: Int, val baseUser: IManageable?, val fullName: String?, val passwordMustChange: Boolean, permissions: List<Permission>, val memberPermissions: List<Permission>, val reducedPermissions: List<Permission>, val authKey: String, private val context: IContext) : AbstractOperator(login, name, permissions, type), IUser, Serializable {

    companion object {
        fun fromResponse(response: ApiResponse, apiUrl: String, authKey: String): User {
            val jsonResponse = response.toJson().asJsonArray
            val loginResponse = ResponseUtil.getSubResponseResultByMethod(jsonResponse, "login")
            val informationResponse = ResponseUtil.getSubResponseResultByMethod(jsonResponse, "get_information")

            val jsonObject = loginResponse.get("user").asJsonObject

            jsonObject.get("base_rights")?.asJsonArray?.add("self") // dirty hack, because too lazy to fix permissions ^^
            val permissions = mutableListOf<Permission>()
            jsonObject.get("base_rights")?.asJsonArray?.forEach { perm ->
                permissions.addAll(Permission.getByName(perm.asString))
            }
            permissions.addAll(Permission.getByName("self"))
            val memberPermissions = mutableListOf<Permission>()
            jsonObject.get("member_rights")?.asJsonArray?.forEach { perm ->
                memberPermissions.addAll(Permission.getByName(perm.asString))
            }
            val reducedMemberPermissions = mutableListOf<Permission>()
            jsonObject.get("reduced_rights")?.asJsonArray?.forEach { perm ->
                reducedMemberPermissions.addAll(Permission.getByName(perm.asString))
            }

            val context = UserContext(informationResponse.get("session_id").asString, apiUrl)
            context.groups = loginResponse.get("member").asJsonArray.map { Group.fromJson(it.asJsonObject, context) }
            context.user = User(
                    jsonObject.get("login").asString,
                    jsonObject.get("name_hr").asString,
                    jsonObject.get("type").asInt,
                    if (jsonObject.has("base_user")) RemoteManageable.fromJson(jsonObject.get("base_user").asJsonObject) else null,
                    jsonObject.get("fullname")?.asString,
                    jsonObject.get("password_must_change")?.asInt == 1,
                    permissions,
                    memberPermissions,
                    reducedMemberPermissions,
                    authKey,
                    context
            )
            return context.getUser()
        }
    }

    override fun getAllTasks(overwriteCache: Boolean): List<Task> {
        val request = UserApiRequest(this)
        val taskIds = request.addGetAllTasksRequest()
        val response = request.fireRequest(overwriteCache).toJson().asJsonArray
        val tasks = mutableListOf<Task>()
        val responses = response.filter { taskIds.contains(it.asJsonObject.get("id").asInt) }.map { it.asJsonObject }
        responses.withIndex().forEach { (index, subResponse) ->
            if (index % 2 == 1) {
                val focus = responses[index - 1].get("result").asJsonObject
                check(focus.get("method").asString == "set_focus")
                val memberLogin = focus.get("user").asJsonObject.get("login").asString
                val member = getContext().getOperator(memberLogin)!!
                subResponse.get("result").asJsonObject.get("entries").asJsonArray.forEach { taskResponse ->
                    tasks.add(Task.fromJson(taskResponse.asJsonObject, member))
                }
            }
        }
        return tasks
    }

    override fun getAllNotifications(overwriteCache: Boolean): List<Notification> {
        val request = UserApiRequest(this)
        val notificationIds = request.addGetAllNotificationsRequest()
        val response = request.fireRequest(overwriteCache).toJson().asJsonArray
        val notifications = mutableListOf<Notification>()
        val responses = response.filter { notificationIds.contains(it.asJsonObject.get("id").asInt) }.map { it.asJsonObject }
        responses.withIndex().forEach { (index, subResponse) ->
            if (index % 2 == 1) {
                val focus = responses[index - 1].get("result").asJsonObject
                check(focus.get("method").asString == "set_focus")
                val memberLogin = focus.get("user").asJsonObject.get("login").asString
                val member = getContext().getOperator(memberLogin)!!
                subResponse.get("result").asJsonObject.get("entries").asJsonArray.forEach { taskResponse ->
                    notifications.add(Notification.fromJson(taskResponse.asJsonObject, member))
                }
            }
        }
        return notifications
    }

    override fun logout(removeTrust: Boolean) {
        val request = UserApiRequest(this)
        request.addRequest("logout", null)
        if (removeTrust) {
            // WHAT HAVE YOU DONE?!
            val tmpUser = LoNet.loginToken(getLogin(), authKey, true)
            tmpUser.logout(false)
        }
        val response = request.fireRequest(getContext(), true)
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun getAutoLoginUrl(): String {
        val request = UserApiRequest(this)
        val id = request.addGetAutoLoginUrlRequest()[1]
        val response = request.fireRequest(true)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("url").asString
    }

    override fun getSystemNotifications(overwriteCache: Boolean): List<SystemNotification> {
        val request = UserApiRequest(this)
        val id = request.addGetSystemNotificationsRequest()[1]
        val response = request.fireRequest(overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("messages")?.asJsonArray?.map { SystemNotification.fromJson(it.asJsonObject, this) }
                ?: emptyList()
    }

    override fun checkSession(): Boolean {
        val request = UserApiRequest(this)
        val response = request.fireRequest(true)
        return runCatching { ResponseUtil.checkSuccess(response.toJson()) }.isSuccess
    }

    override fun getContext(): IContext {
        return context
    }

    class UserContext(private var sessionId: String, private val requestUrl: String): IContext {

        internal lateinit var user: User
        internal lateinit var groups: List<Group> //TODO should be inside user

        override fun getSessionId(): String {
            return sessionId
        }

        override fun setSessionId(sessionId: String) {
            this.sessionId = sessionId
        }

        override fun getUser(): User {
            return user
        }

        override fun getGroups(): List<Group> {
            return groups
        }

        override fun getOperator(login: String): AbstractOperator? {
            if (login == user.getLogin()) return user
            return groups.firstOrNull { it.getLogin() == login }
        }

        override fun getOrCreateManageable(jsonObject: JsonObject): IManageable {
            val local = getOperator(jsonObject.get("login").asString)
            if (local != null) return local
            return RemoteManageable.fromJson(jsonObject)
        }

        override fun getRequestUrl(): String {
            return requestUrl
        }
    }

}