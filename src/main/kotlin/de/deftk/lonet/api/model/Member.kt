package de.deftk.lonet.api.model

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.feature.Notification
import de.deftk.lonet.api.model.feature.Quota
import de.deftk.lonet.api.model.feature.Task
import de.deftk.lonet.api.model.feature.abstract.*
import de.deftk.lonet.api.model.feature.files.FileStorageSettings
import de.deftk.lonet.api.model.feature.files.PrimitiveFileImpl
import de.deftk.lonet.api.model.feature.forum.ForumPost
import de.deftk.lonet.api.model.feature.forum.ForumSettings
import de.deftk.lonet.api.request.MemberApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import java.io.Serializable

open class Member(val login: String, val name: String?, val type: Int?, val baseUser: Member?, val fullName: String?, val passwordMustChange: Boolean, val isOnline: Boolean?, val permissions: List<Permission>, val memberPermissions: List<Permission>, val reducedPermissions: List<Permission>, val responsibleHost: String?) : IForum, IFileStorage, IFilePrimitive by PrimitiveFileImpl("/", responsibleHost, login), INotificator, IMemberList, ITaskList, Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject, responsibleHost: String?): Member {
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

            return Member(
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
                    responsibleHost
            )
        }
    }

    override fun getTasks(user: User, overwriteCache: Boolean): List<Task> {
        check(responsibleHost != null) { "Can't do API calls for member $login" }
        val request = MemberApiRequest(responsibleHost, login)
        val id = request.addGetTasksRequest()[1]
        val response = request.fireRequest(user, overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("entries")?.asJsonArray?.map { Task.fromJson(it.asJsonObject, this) } ?: emptyList()
    }

    override fun getMembers(user: User, overwriteCache: Boolean): List<Member> {
        check(responsibleHost != null) { "Can't do API calls for member $login" }
        val request = MemberApiRequest(responsibleHost, login)
        val id = request.addGetMembersRequest()[1]
        val response = request.fireRequest(user, overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("users")?.asJsonArray?.map { Member.fromJson(it.asJsonObject, null) } ?: emptyList()
    }

    override fun getNotifications(user: User, overwriteCache: Boolean): List<Notification> {
        check(responsibleHost != null) { "Can't do API calls for member $login" }
        val request = MemberApiRequest(responsibleHost, login)
        val id = request.addGetNotificationsRequest()[1]
        val response = request.fireRequest(user, overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("entries")?.asJsonArray?.map { Notification.fromJson(it.asJsonObject, this) } ?: emptyList()
    }

    override fun getFileStorageState(user: User, overwriteCache: Boolean): Pair<FileStorageSettings, Quota> {
        check(responsibleHost != null) { "Can't do API calls for member $login" }
        val request = MemberApiRequest(responsibleHost, login)
        val id = request.addGetFileStorageStateRequest()[1]
        val response = request.fireRequest(user, overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Pair(FileStorageSettings.fromJson(subResponse.get("settings").asJsonObject), Quota.fromJson(subResponse.get("quota").asJsonObject))
    }

    override fun getForumState(user: User, overwriteCache: Boolean): Pair<Quota, ForumSettings> {
        check(responsibleHost != null) { "Can't do API calls for member $login" }
        val request = MemberApiRequest(responsibleHost, login)
        val id = request.addGetForumStateRequest()[1]
        val response = request.fireRequest(user, overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Pair(Quota.fromJson(subResponse.get("quota").asJsonObject), ForumSettings.fromJson(subResponse.get("settings").asJsonObject))
    }

    override fun getForumPosts(user: User, parentId: String?, overwriteCache: Boolean): List<ForumPost> {
        check(responsibleHost != null) { "Can't do API calls for member $login" }
        val request = MemberApiRequest(responsibleHost, login)
        val id = request.addGetForumPostsRequest(parentId = parentId)[1]
        val response = request.fireRequest(user, overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        val allPosts = subResponse.get("entries").asJsonArray.map { ForumPost.fromJson(it.asJsonObject, this) }
        //TODO build comment tree
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

}