package de.deftk.lonet.api.model

import com.google.gson.JsonObject
import de.deftk.lonet.api.exception.ApiException
import de.deftk.lonet.api.model.abstract.*
import de.deftk.lonet.api.model.feature.Notification
import de.deftk.lonet.api.model.feature.Quota
import de.deftk.lonet.api.model.feature.files.FileStorageSettings
import de.deftk.lonet.api.model.feature.forum.ForumPost
import de.deftk.lonet.api.model.feature.forum.ForumSettings
import de.deftk.lonet.api.request.GroupApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import java.io.Serializable

open class Group(login: String, name: String, type: ManageableType, val baseUser: IManageable?, val fullName: String?, val passwordMustChange: Boolean, permissions: List<Permission>, val memberPermissions: List<Permission>, val reducedPermissions: List<Permission>, private val context: IContext) : AbstractOperator(login, name, permissions, type), IGroup, Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject, context: IContext): Group {
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

            return Group(
                    jsonObject.get("login").asString,
                    jsonObject.get("name_hr").asString,
                    ManageableType.getById(jsonObject.get("type").asInt),
                    if (jsonObject.has("base_user")) RemoteManageable.fromJson(jsonObject.get("base_user").asJsonObject) else null,
                    jsonObject.get("fullname")?.asString,
                    jsonObject.get("password_must_change")?.asInt == 1,
                    permissions,
                    memberPermissions,
                    reducedMemberPermissions,
                    context
            )
        }
    }

    override fun getMembers(overwriteCache: Boolean): List<IManageable> {
        val request = GroupApiRequest(this)
        val id = request.addGetMembersRequest()[1]
        val response = request.fireRequest(overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("users")?.asJsonArray?.map { getContext().getOrCreateManageable(it.asJsonObject) } ?: emptyList()
    }

    override fun getNotifications(overwriteCache: Boolean): List<Notification> {
        val request = GroupApiRequest(this)
        val id = request.addGetNotificationsRequest()[1]
        val response = request.fireRequest(overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("entries")?.asJsonArray?.map { Notification.fromJson(it.asJsonObject, this) } ?: emptyList()
    }

    override fun getFileStorageState(overwriteCache: Boolean): Pair<FileStorageSettings, Quota> {
        val request = GroupApiRequest(this)
        val id = request.addGetFileStorageStateRequest()[1]
        val response = request.fireRequest(overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Pair(FileStorageSettings.fromJson(subResponse.get("settings").asJsonObject), Quota.fromJson(subResponse.get("quota").asJsonObject))
    }

    override fun getForumState(overwriteCache: Boolean): Pair<Quota, ForumSettings> {
        val request = GroupApiRequest(this)
        val id = request.addGetForumStateRequest()[1]
        val response = request.fireRequest( overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Pair(Quota.fromJson(subResponse.get("quota").asJsonObject), ForumSettings.fromJson(subResponse.get("settings").asJsonObject))
    }

    override fun getForumPosts(parentId: String?, overwriteCache: Boolean): List<ForumPost> {
        val request = GroupApiRequest(this)
        val id = request.addGetForumPostsRequest(parentId = parentId)[1]
        val response = request.fireRequest(overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        val allPosts = subResponse.get("entries").asJsonArray.map { ForumPost.fromJson(it.asJsonObject, this) }
        val rootPosts = mutableListOf<ForumPost>()
        val tmpPosts = mutableMapOf<String, ForumPost>()
        allPosts.forEach { post -> tmpPosts[post.id] = post }
        allPosts.forEach { post ->
            if (post.parentId != "0") {
                val parent = tmpPosts[post.parentId] ?: throw ApiException("Comment has invalid parent!")
                parent.comments.add(post)
            } else {
                rootPosts.add(post)
            }
        }
        return rootPosts
    }

    override fun getContext(): IContext {
        return context
    }

    override fun toString(): String {
        return getLogin()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Group

        if (getLogin() != other.getLogin()) return false
        return true
    }

    override fun hashCode(): Int {
        return getLogin().hashCode()
    }

}