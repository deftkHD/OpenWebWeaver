package de.deftk.lonet.api.model

import com.google.gson.JsonObject
import de.deftk.lonet.api.LoNet
import de.deftk.lonet.api.model.feature.Notification
import de.deftk.lonet.api.model.feature.OnlineFile
import de.deftk.lonet.api.model.feature.Quota
import de.deftk.lonet.api.model.feature.Task
import de.deftk.lonet.api.request.ApiRequest
import de.deftk.lonet.api.response.ResponseUtil

open class Member(userObject: JsonObject, protected val responsibleHost: String?) {

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
        userObject.get("base_rights")?.asJsonArray?.add("self") // dirty hack
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

    open fun getTasks(sessionId: String): List<Task> {
        check(responsibleHost != null) { "Can't do API calls for this member" }
        val request = ApiRequest(responsibleHost)
        request.addSetSessionRequest(sessionId)
        request.addSetFocusRequest("tasks", login)
        request.addRequest("get_entries", null)
        val response = LoNet.performJsonApiRequest(request)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), 3)
        return subResponse.get("entries")?.asJsonArray?.map { Task(it.asJsonObject, this) } ?: emptyList()
    }

    fun getMembers(sessionId: String): List<Member> {
        val request = ApiRequest(responsibleHost!!)
        request.addSetSessionRequest(sessionId)
        request.addSetFocusRequest("members", login)
        request.addRequest("get_users", null)
        val response = LoNet.performJsonApiRequest(request)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), 3)
        return subResponse.get("users")?.asJsonArray?.map { Member(it.asJsonObject, responsibleHost) } ?: emptyList()
    }

    fun getNotifications(sessionId: String): List<Notification> {
        val request = ApiRequest(responsibleHost!!)
        request.addSetSessionRequest(sessionId)
        request.addSetFocusRequest("board", login)
        request.addRequest("get_entries", null)
        val response = LoNet.performJsonApiRequest(request)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), 3)
        return subResponse.get("entries")?.asJsonArray?.map { Notification(it.asJsonObject) } ?: emptyList()
    }

    fun getFiles(
        sessionId: String,
        folderId: String = "/",
        getFileDownloadUrl: Boolean = true,
        getFiles: Boolean = true,
        getFolders: Boolean = false,
        getRoot: Boolean = false,
        limit: Int = 2,
        offset: Int = 0,
        recursive: Boolean = false,
        searchOption: FileSearchOption = FileSearchOption.WORD_CONTAINS,
        searchString: String = ""
    ): List<OnlineFile> {
        val request = ApiRequest(responsibleHost!!)
        request.addSetSessionRequest(sessionId)
        request.addSetFocusRequest("files", login)
        val json = JsonObject()
        json.addProperty("folder_id", folderId)
        json.addProperty("get_file_download_url", if (getFileDownloadUrl) 1 else 0)
        json.addProperty("get_files", if (getFiles) 1 else 0)
        json.addProperty("get_folders", if (getFolders) 1 else 0)
        json.addProperty("get_root", if (getRoot) 1 else 0)
        json.addProperty("limit", limit)
        json.addProperty("offset", offset)
        json.addProperty("recursive", if (recursive) 1 else 0)
        json.addProperty("search_option", searchOption.id)
        json.addProperty("search_string", searchString)
        request.addRequest("get_entries", json)
        val response = LoNet.performJsonApiRequest(request)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), 3)
        return subResponse.get("entries")?.asJsonArray?.map { OnlineFile(it.asJsonObject) } ?: emptyList()
    }

    fun getFileQuota(sessionId: String): Quota {
        val request = ApiRequest(responsibleHost!!)
        request.addSetSessionRequest(sessionId)
        request.addSetFocusRequest("files", login)
        request.addRequest("get_state", null)
        val response = LoNet.performJsonApiRequest(request)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), 3)
        return Quota(subResponse.get("quota").asJsonObject)
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