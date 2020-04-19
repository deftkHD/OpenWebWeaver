package de.deftk.lonet.api.model

import com.google.gson.JsonObject
import de.deftk.lonet.api.LoNet
import de.deftk.lonet.api.model.feature.*
import de.deftk.lonet.api.model.feature.files.FileProvider
import de.deftk.lonet.api.model.feature.forum.ForumMessage
import de.deftk.lonet.api.model.feature.forum.ForumSettings
import de.deftk.lonet.api.request.ApiRequest
import de.deftk.lonet.api.response.ResponseUtil

open class Member(userObject: JsonObject, val responsibleHost: String?): FileProvider("/", responsibleHost, userObject.get("login").asString) {

    //TODO check if request is successful

    val login = userObject.get("login").asString
    val name = userObject.get("name_hr")?.asString
    val type = userObject.get("type")?.asInt
    val permissions: List<Permission>
    val baseUser = if (userObject.has("base_user")) Member(userObject.get("base_user").asJsonObject, responsibleHost) else null
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

    open fun getTasks(sessionId: String, overwriteCache: Boolean = false): List<Task> {
        check(responsibleHost != null) { "Can't do API calls for this member" }
        val request = ApiRequest(responsibleHost)
        request.addSetSessionRequest(sessionId)
        request.addSetFocusRequest("tasks", login)
        request.addRequest("get_entries", null)
        val response = LoNet.requestHandler.performRequest(request, !overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), 3)
        return subResponse.get("entries")?.asJsonArray?.map { Task(it.asJsonObject, this) } ?: emptyList()
    }

    open fun getMembers(sessionId: String, overwriteCache: Boolean = false): List<Member> {
        val request = ApiRequest(responsibleHost!!)
        request.addSetSessionRequest(sessionId)
        request.addSetFocusRequest("members", login)
        request.addRequest("get_users", null)
        val response = LoNet.requestHandler.performRequest(request, !overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), 3)
        return subResponse.get("users")?.asJsonArray?.map { Member(it.asJsonObject, responsibleHost) } ?: emptyList()
    }

    open fun getNotifications(sessionId: String, overwriteCache: Boolean = false): List<Notification> {
        val request = ApiRequest(responsibleHost!!)
        request.addSetSessionRequest(sessionId)
        request.addSetFocusRequest("board", login)
        request.addRequest("get_entries", null)
        val response = LoNet.requestHandler.performRequest(request, !overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), 3)
        return subResponse.get("entries")?.asJsonArray?.map { Notification(it.asJsonObject, this) } ?: emptyList()
    }

    fun getFileQuota(sessionId: String, overwriteCache: Boolean = false): Quota {
        val request = ApiRequest(responsibleHost!!)
        request.addSetSessionRequest(sessionId)
        request.addSetFocusRequest("files", login)
        request.addRequest("get_state", null)
        val response = LoNet.requestHandler.performRequest(request, !overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), 3)
        return Quota(subResponse.get("quota").asJsonObject)
    }

    fun getForumState(sessionId: String, overwriteCache: Boolean = false): Pair<Quota, ForumSettings> {
        val request = ApiRequest(responsibleHost!!)
        request.addSetSessionRequest(sessionId)
        request.addSetFocusRequest("forum", login)
        request.addRequest("get_state", null)
        val response = LoNet.requestHandler.performRequest(request, !overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), 3)
        return Pair(Quota(subResponse.get("quota").asJsonObject), ForumSettings(subResponse.get("settings").asJsonObject))
    }

    fun getForumEntries(sessionId: String, parentId: String? = null, overwriteCache: Boolean = false): List<ForumMessage> {
        val request = ApiRequest(responsibleHost!!)
        request.addSetSessionRequest(sessionId)
        request.addSetFocusRequest("forum", login)
        val requestData = JsonObject()
        if (parentId != null)
            requestData.addProperty("parent_id", parentId)
        request.addRequest("get_entries", requestData)
        val response = LoNet.requestHandler.performRequest(request, !overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), 3)
        return subResponse.get("entries").asJsonArray.map { ForumMessage(it.asJsonObject) }
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

    enum class FileSearchOption(val id: String) {
        WORD_EQUALS("word_equals"),
        WORD_STARTS_WITH("word_starts_with"),
        WORD_CONTAINS("word_contains"),
        PHRASE("phrase");
    }

}