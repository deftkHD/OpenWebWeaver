package de.deftk.lonet.api.model

import de.deftk.lonet.api.LoNet
import de.deftk.lonet.api.model.feature.Notification
import de.deftk.lonet.api.model.feature.Quota
import de.deftk.lonet.api.model.feature.SystemNotification
import de.deftk.lonet.api.model.feature.Task
import de.deftk.lonet.api.model.feature.abstract.IEmailController
import de.deftk.lonet.api.model.feature.abstract.ISystemNotificationList
import de.deftk.lonet.api.model.feature.abstract.IUserController
import de.deftk.lonet.api.model.feature.files.FileStorageSettings
import de.deftk.lonet.api.model.feature.forum.ForumPost
import de.deftk.lonet.api.model.feature.forum.ForumSettings
import de.deftk.lonet.api.model.feature.mailbox.EmailFolder
import de.deftk.lonet.api.request.ApiRequest
import de.deftk.lonet.api.request.UserApiRequest
import de.deftk.lonet.api.response.ApiResponse
import de.deftk.lonet.api.response.ResponseUtil
import java.io.Serializable

class User(login: String, name: String?, type: Int?, baseUser: Member?, fullName: String?, passwordMustChange: Boolean, isOnline: Boolean?, permissions: List<Permission>, memberPermissions: List<Permission>, reducedPermissions: List<Permission>, responsibleHost: String?, val authKey: String, val sessionId: String, val memberships: List<Member>) : Member(login, name, type, baseUser, fullName, passwordMustChange, isOnline, permissions, memberPermissions, reducedPermissions, responsibleHost), IEmailController, ISystemNotificationList, IUserController, Serializable {

    companion object {
        fun fromResponse(response: ApiResponse, responsibleHost: String, authKey: String): User {
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

            return User(
                    jsonObject.get("login").asString,
                    jsonObject.get("name_hr")?.asString,
                    jsonObject.get("type")?.asInt,
                    if (jsonObject.has("base_user")) Member.fromJson(jsonObject.get("base_user").asJsonObject, null) else null,
                    jsonObject.get("fullname")?.asString,
                    jsonObject.get("password_must_change")?.asInt == 1,
                    jsonObject.get("is_online")?.asInt == 1,
                    permissions,
                    memberPermissions,
                    reducedMemberPermissions,
                    responsibleHost,
                    authKey,
                    informationResponse.get("session_id").asString,
                    loginResponse.get("member").asJsonArray.map { Member.fromJson(it.asJsonObject, responsibleHost) }
            )
        }
    }

    //TODO shadow methods from member

    fun getMembers(overwriteCache: Boolean = false): List<Member> {
        return super.getMembers(this, overwriteCache)
    }

    fun getFileStorageState(overwriteCache: Boolean = false): Pair<FileStorageSettings, Quota> {
        return super.getFileStorageState(this, overwriteCache)
    }

    fun getForumState(overwriteCache: Boolean = false): Pair<Quota, ForumSettings> {
        return super.getForumState(this, overwriteCache)
    }

    fun getForumPosts(parentId: String?, overwriteCache: Boolean = false): List<ForumPost> {
        return super.getForumPosts(this, parentId, overwriteCache)
    }

    fun getAllTasks(overwriteCache: Boolean = false): List<Task> {
        check(responsibleHost != null) { "Can't do API calls for user $login" }
        val request = UserApiRequest(responsibleHost, this)
        val taskIds = request.addGetAllTasksRequest()
        val response = request.fireRequest(overwriteCache).toJson().asJsonArray
        val tasks = mutableListOf<Task>()
        val responses = response.filter { taskIds.contains(it.asJsonObject.get("id").asInt) }.map { it.asJsonObject }
        responses.withIndex().forEach { (index, subResponse) ->
            if (index % 2 == 1) {
                val focus = responses[index - 1].get("result").asJsonObject
                check(focus.get("method").asString == "set_focus")
                val memberLogin = focus.get("user").asJsonObject.get("login").asString
                val member = if (memberLogin == this.login) this else memberships.first { it.login == memberLogin }
                subResponse.get("result").asJsonObject.get("entries").asJsonArray.forEach { taskResponse ->
                    tasks.add(Task.fromJson(taskResponse.asJsonObject, member))
                }
            }
        }
        return tasks
    }

    fun getAllNotifications(overwriteCache: Boolean = false): List<Notification> {
        check(responsibleHost != null) { "Can't do API calls for user $login" }
        val request = UserApiRequest(responsibleHost, this)
        val notificationIds = request.addGetAllNotificationsRequest()
        val response = request.fireRequest(overwriteCache).toJson().asJsonArray
        val notifications = mutableListOf<Notification>()
        val responses = response.filter { notificationIds.contains(it.asJsonObject.get("id").asInt) }.map { it.asJsonObject }
        responses.withIndex().forEach { (index, subResponse) ->
            if (index % 2 == 1) {
                val focus = responses[index - 1].get("result").asJsonObject
                check(focus.get("method").asString == "set_focus")
                val memberLogin = focus.get("user").asJsonObject.get("login").asString
                val member = if (memberLogin == this.login) this else memberships.first { it.login == memberLogin }
                subResponse.get("result").asJsonObject.get("entries").asJsonArray.forEach { taskResponse ->
                    notifications.add(Notification.fromJson(taskResponse.asJsonObject, member))
                }
            }
        }
        return notifications
    }

    override fun logout(removeTrust: Boolean) {
        check(responsibleHost != null) { "Can't do API calls for user $login" }
        val request = ApiRequest(responsibleHost)
        request.addRequest("logout", null)
        if (removeTrust) {
            // WHAT HAVE YOU DONE?!
            val tmpUser = LoNet.loginToken(login, authKey, true)
            tmpUser.logout(false)
        }
        val response = request.fireRequest(this, true)
        println(response.raw)
    }

    override fun getAutoLoginUrl(): String {
        check(responsibleHost != null) { "Can't do API calls for user $login" }
        val request = UserApiRequest(responsibleHost, this)
        val id = request.addGetAutoLoginUrlRequest()[1]
        val response = request.fireRequest(true)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("url").asString
    }

    override fun getEmailStatus(overwriteCache: Boolean): Pair<Quota, Int> {
        check(responsibleHost != null) { "Can't do API calls for user $login" }
        val request = UserApiRequest(responsibleHost, this)
        val id = request.addGetEmailStateRequest()[1]
        val response = request.fireRequest(overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Pair(Quota.fromJson(subResponse.get("quota").asJsonObject), subResponse.get("unread_messages").asInt)
    }

    override fun getEmailQuota(overwriteCache: Boolean): Quota {
        return getEmailStatus(overwriteCache).first
    }

    override fun getUnreadEmailCount(overwriteCache: Boolean): Int {
        return getEmailStatus(overwriteCache).second
    }

    override fun getEmailFolders(overwriteCache: Boolean): List<EmailFolder> {
        check(responsibleHost != null) { "Can't do API calls for user $login" }
        val request = UserApiRequest(responsibleHost, this)
        val id = request.addGetEmailFoldersRequest()[1]
        val response = request.fireRequest(overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("folders").asJsonArray.map { EmailFolder.fromJson(it.asJsonObject, this) }
    }

    //TODO attachments
    override fun sendEmail(to: String, subject: String, plainBody: String, text: String?, bcc: String?, cc: String?, overwriteCache: Boolean) {
        check(responsibleHost != null) { "Can't do API calls for user $login" }
        val request = UserApiRequest(responsibleHost, this)
        request.addSendEmailRequest(to, subject, plainBody, text, bcc, cc) // don't need the id
        val response = request.fireRequest(overwriteCache)
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun getSystemNotifications(overwriteCache: Boolean): List<SystemNotification> {
        check(responsibleHost != null) { "Can't do API calls for user $login" }
        val request = UserApiRequest(responsibleHost, this)
        val id = request.addGetSystemNotificationsRequest()[1]
        val response = request.fireRequest(overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("messages")?.asJsonArray?.map { SystemNotification.fromJson(it.asJsonObject) } ?: emptyList()
    }

    fun findMember(login: String): Member? {
        if (login == this.login) return this
        return memberships.firstOrNull { it.login == login }
    }

}