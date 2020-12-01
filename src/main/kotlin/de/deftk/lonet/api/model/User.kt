package de.deftk.lonet.api.model

import com.google.gson.JsonObject
import de.deftk.lonet.api.LoNet
import de.deftk.lonet.api.model.abstract.*
import de.deftk.lonet.api.model.feature.SystemNotification
import de.deftk.lonet.api.model.feature.Task
import de.deftk.lonet.api.model.feature.board.BoardNotification
import de.deftk.lonet.api.model.feature.board.BoardType
import de.deftk.lonet.api.model.feature.files.session.SessionFile
import de.deftk.lonet.api.request.UserApiRequest
import de.deftk.lonet.api.response.ApiResponse
import de.deftk.lonet.api.response.ResponseUtil
import java.io.Serializable

class User(login: String, name: String, type: ManageableType, val baseUser: IManageable?, val fullName: String?, val groups: List<Group>, val passwordMustChange: Boolean, baseRights: List<Permission>, effectiveRights: List<Permission>, val authKey: String, private val context: IContext) : AbstractOperator(login, name, baseRights, effectiveRights, type), IUser, Serializable {

    companion object {
        fun fromResponse(response: ApiResponse, apiUrl: String, authKey: String): User {
            val jsonResponse = response.toJson().asJsonArray
            val loginResponse = ResponseUtil.getSubResponseResultByMethod(jsonResponse, "login")
            val informationResponse = ResponseUtil.getSubResponseResultByMethod(jsonResponse, "get_information")

            val jsonObject = loginResponse.get("user").asJsonObject

            val baseRights = mutableListOf<Permission>()
            jsonObject.get("base_rights")?.asJsonArray?.forEach { perm ->
                baseRights.add(Permission.getByName(perm.asString))
            }
            baseRights.add(Permission.SELF)

            val context = UserContext(informationResponse.get("session_id").asString, apiUrl)
            context.user = User(
                    jsonObject.get("login").asString,
                    jsonObject.get("name_hr").asString,
                    ManageableType.getById(jsonObject.get("type").asInt),
                    if (jsonObject.has("base_user")) RemoteManageable.fromJson(jsonObject.get("base_user").asJsonObject) else null,
                    jsonObject.get("fullname")?.asString,
                    loginResponse.get("member").asJsonArray.map { Group.fromJson(it.asJsonObject, context) },
                    jsonObject.get("password_must_change")?.asInt == 1,
                    baseRights,
                    jsonObject.get("effective_rights").asJsonArray.map { Permission.getByName(it.asString) },
                    authKey,
                    context
            )
            return context.getUser()
        }
    }

    override fun getAllTasks(): List<Task> {
        val request = UserApiRequest(this)
        val taskIds = request.addGetAllTasksRequest()
        val response = request.fireRequest().toJson().asJsonArray
        val tasks = mutableListOf<Task>()
        val responses = response.filter { taskIds.contains(it.asJsonObject.get("id").asInt) }.map { it.asJsonObject }
        responses.withIndex().forEach { (index, subResponse) ->
            if (index % 2 == 1) {
                val focus = responses[index - 1].get("result").asJsonObject
                assert(focus.get("method").asString == "set_focus")
                val memberLogin = focus.get("user").asJsonObject.get("login").asString
                val member = getContext().getOperator(memberLogin)!!
                subResponse.get("result").asJsonObject.get("entries").asJsonArray.forEach { taskResponse ->
                    tasks.add(Task.fromJson(taskResponse.asJsonObject, member))
                }
            }
        }
        return tasks
    }

    override fun getAllBoardNotifications(): List<BoardNotification> {
        val request = UserApiRequest(this)
        val notificationIds = request.addGetAllNotificationsRequest()
        val response = request.fireRequest().toJson().asJsonArray
        val notifications = mutableListOf<BoardNotification>()
        val responses = response.filter { notificationIds.contains(it.asJsonObject.get("id").asInt) }.map { it.asJsonObject }
        responses.withIndex().forEach { (index, subResponse) ->
            if (index % 2 == 1) {
                val focus = responses[index - 1].get("result").asJsonObject
                assert(focus.get("method").asString == "set_focus")
                val memberLogin = focus.get("user").asJsonObject.get("login").asString
                val member = getContext().getOperator(memberLogin)!!
                subResponse.get("result").asJsonObject.get("entries").asJsonArray.forEach { taskResponse ->
                    notifications.add(BoardNotification.fromJson(taskResponse.asJsonObject, member, BoardType.ALL))
                }
            }
        }
        return notifications
    }

    override fun getAllTeacherBoardNotifications(): List<BoardNotification> {
        val request = UserApiRequest(this)
        val notificationIds = request.addGetAllTeacherNotificationsRequest()
        val response = request.fireRequest().toJson().asJsonArray
        val notifications = mutableListOf<BoardNotification>()
        val responses = response.filter { notificationIds.contains(it.asJsonObject.get("id").asInt) }.map { it.asJsonObject }
        responses.withIndex().forEach { (index, subResponse) ->
            if (index % 2 == 1) {
                val focus = responses[index - 1].get("result").asJsonObject
                assert(focus.get("method").asString == "set_focus")
                val memberLogin = focus.get("user").asJsonObject.get("login").asString
                val member = getContext().getOperator(memberLogin)!!
                subResponse.get("result").asJsonObject.get("entries").asJsonArray.forEach { taskResponse ->
                    notifications.add(BoardNotification.fromJson(taskResponse.asJsonObject, member, BoardType.TEACHER))
                }
            }
        }
        return notifications
    }

    override fun getAllPupilBoardNotifications(): List<BoardNotification> {
        val request = UserApiRequest(this)
        val notificationIds = request.addGetAllPupilNotificationsRequest()
        val response = request.fireRequest().toJson().asJsonArray
        val notifications = mutableListOf<BoardNotification>()
        val responses = response.filter { notificationIds.contains(it.asJsonObject.get("id").asInt) }.map { it.asJsonObject }
        responses.withIndex().forEach { (index, subResponse) ->
            if (index % 2 == 1) {
                val focus = responses[index - 1].get("result").asJsonObject
                assert(focus.get("method").asString == "set_focus")
                val memberLogin = focus.get("user").asJsonObject.get("login").asString
                val member = getContext().getOperator(memberLogin)!!
                subResponse.get("result").asJsonObject.get("entries").asJsonArray.forEach { taskResponse ->
                    notifications.add(BoardNotification.fromJson(taskResponse.asJsonObject, member, BoardType.PUPIL))
                }
            }
        }
        return notifications
    }

    override fun logout(removeTrust: Boolean) {
        val request = UserApiRequest(this)
        request.addRequest("logout", null)
        if (removeTrust) {
            val tmpUser = LoNet.loginToken(getLogin(), authKey, true)
            tmpUser.logout(false)
        }
        val response = request.fireRequest(getContext())
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun getAutoLoginUrl(): String {
        val request = UserApiRequest(this)
        val id = request.addGetAutoLoginUrlRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("url").asString
    }

    override fun getSystemNotifications(): List<SystemNotification> {
        val request = UserApiRequest(this)
        val id = request.addGetSystemNotificationsRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("messages")?.asJsonArray?.map { SystemNotification.fromJson(it.asJsonObject, this) }
                ?: emptyList()
    }

    override fun checkSession(): Boolean {
        val request = UserApiRequest(this)
        val response = request.fireRequest()
        return runCatching { ResponseUtil.checkSuccess(response.toJson()) }.isSuccess
    }

    override fun registerService(type: IUser.ServiceType, token: String, application: String?, generateSecret: String?, isOnline: Boolean?, managedObjects: String?, unmanagedPriority: Int?) {
        val request = UserApiRequest(this)
        request.addRegisterServiceRequest(type, token, application, generateSecret, isOnline, managedObjects, unmanagedPriority)[1]
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun unregisterService(type: IUser.ServiceType, token: String) {
        val request = UserApiRequest(this)
        request.addUnregisterServiceRequest(type, token)[1]
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun addSessionFile(name: String, data: ByteArray): SessionFile {
        val request = UserApiRequest(this)
        val id = request.addAddSessionFileRequest(name, data)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return SessionFile.fromJson(subResponse.get("file").asJsonObject, this)
    }

    override fun getContext(): IContext {
        return context
    }

    class UserContext(private var sessionId: String, private val requestUrl: String): IContext {

        internal lateinit var user: User

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
            return user.groups
        }

        override fun getOperator(login: String): AbstractOperator? {
            if (login == user.getLogin()) return user
            return getGroups().firstOrNull { it.getLogin() == login }
        }

        override fun getOrCreateManageable(jsonObject: JsonObject): IManageable {
            return getOperator(jsonObject.get("login").asString) ?: RemoteManageable.fromJson(jsonObject)
        }

        override fun getRequestUrl(): String {
            return requestUrl
        }
    }

}