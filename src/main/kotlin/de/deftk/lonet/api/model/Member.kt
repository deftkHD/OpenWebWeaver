package de.deftk.lonet.api.model

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.feature.Notification
import de.deftk.lonet.api.model.feature.Quota
import de.deftk.lonet.api.model.feature.Task
import de.deftk.lonet.api.model.feature.abstract.*
import de.deftk.lonet.api.model.feature.files.FileProvider
import de.deftk.lonet.api.model.feature.files.FileStorageSettings
import de.deftk.lonet.api.model.feature.forum.ForumPost
import de.deftk.lonet.api.model.feature.forum.ForumSettings
import de.deftk.lonet.api.request.MemberApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import java.io.Serializable

open class Member(userObject: JsonObject, val responsibleHost: String?): FileProvider("/", responsibleHost, userObject.get("login").asString), IForum, IFileStorage, INotificator, IMemberList, ITaskList, Serializable {

    //TODO check if request is successful

    val login = userObject.get("login").asString
    val name = userObject.get("name_hr")?.asString
    val type = userObject.get("type")?.asInt
    val permissions: List<Permission>
    val baseUser = if (userObject.has("base_user")) Member(userObject.get("base_user").asJsonObject, null) else null
    val fullName = userObject.get("fullname")?.asString
    val passwordMustChange = userObject.get("password_must_change")?.asInt == 1
    val memberPermissions: List<Permission>
    val reducedMemberPermissions: List<Permission>
    val isOnline = userObject.get("is_online")?.asInt == 1

    init {
        userObject.get("base_rights")?.asJsonArray?.add("self") // dirty hack, because too lazy to fix permissions ^^
        val permissions = mutableListOf<Permission>()
        userObject.get("base_rights")?.asJsonArray?.forEach { perm ->
            permissions.addAll(Permission.getByName(perm.asString))
        }
        permissions.addAll(Permission.getByName("self"))
        this.permissions = permissions

        val memberPermissions = mutableListOf<Permission>()
        userObject.get("member_rights")?.asJsonArray?.forEach { perm ->
            memberPermissions.addAll(Permission.getByName(perm.asString))
        }
        this.memberPermissions = memberPermissions

        val reducedMemberPermissions = mutableListOf<Permission>()
        userObject.get("reduced_rights")?.asJsonArray?.forEach { perm ->
            reducedMemberPermissions.addAll(Permission.getByName(perm.asString))
        }
        this.reducedMemberPermissions = reducedMemberPermissions
    }

    override fun getTasks(user: User, overwriteCache: Boolean): List<Task> {
        check(responsibleHost != null) { "Can't do API calls for member $login" }
        val request = MemberApiRequest(responsibleHost, login)
        val id = request.addGetTasksRequest()[1]
        val response = request.fireRequest(user, overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("entries")?.asJsonArray?.map { Task(it.asJsonObject, this) } ?: emptyList()
    }

    override fun getMembers(user: User, overwriteCache: Boolean): List<Member> {
        check(responsibleHost != null) { "Can't do API calls for member $login" }
        val request = MemberApiRequest(responsibleHost, login)
        val id = request.addGetMembersRequest()[1]
        val response = request.fireRequest(user, overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("users")?.asJsonArray?.map { Member(it.asJsonObject, null) } ?: emptyList()
    }

    override fun getNotifications(user: User, overwriteCache: Boolean): List<Notification> {
        check(responsibleHost != null) { "Can't do API calls for member $login" }
        val request = MemberApiRequest(responsibleHost, login)
        val id = request.addGetNotificationsRequest()[1]
        val response = request.fireRequest(user, overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("entries")?.asJsonArray?.map { Notification(it.asJsonObject, this) } ?: emptyList()
    }

    override fun getFileStorageState(user: User, overwriteCache: Boolean): Pair<FileStorageSettings, Quota> {
        check(responsibleHost != null) { "Can't do API calls for member $login" }
        val request = MemberApiRequest(responsibleHost, login)
        val id = request.addGetFileStorageStateRequest()[1]
        val response = request.fireRequest(user, overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Pair(FileStorageSettings(subResponse.get("settings").asJsonObject), Quota(subResponse.get("quota").asJsonObject))
    }

    override fun getForumState(user: User, overwriteCache: Boolean): Pair<Quota, ForumSettings> {
        check(responsibleHost != null) { "Can't do API calls for member $login" }
        val request = MemberApiRequest(responsibleHost, login)
        val id = request.addGetForumStateRequest()[1]
        val response = request.fireRequest(user, overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Pair(Quota(subResponse.get("quota").asJsonObject), ForumSettings(subResponse.get("settings").asJsonObject))
    }

    override fun getForumPosts(user: User, parentId: String?, overwriteCache: Boolean): List<ForumPost> {
        check(responsibleHost != null) { "Can't do API calls for member $login" }
        val request = MemberApiRequest(responsibleHost, login)
        val id = request.addGetForumPostsRequest(parentId = parentId)[1]
        val response = request.fireRequest(user, overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        val allPosts = subResponse.get("entries").asJsonArray.map { ForumPost(it.asJsonObject) }
        //TODO build tree
        return allPosts
    }

    fun isRemote(): Boolean {
        return responsibleHost == null
    }

    override fun toString(): String {
        return login
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Member

        if (login != other.login) return false
        return true
    }

    override fun hashCode(): Int {
        return login.hashCode()
    }

    @Deprecated("not a nice place here")
    enum class FileSearchOption(val id: String): Serializable {
        WORD_EQUALS("word_equals"),
        WORD_STARTS_WITH("word_starts_with"),
        WORD_CONTAINS("word_contains"),
        PHRASE("phrase");
    }

}