package de.deftk.lonet.api.model.feature.files

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Member
import de.deftk.lonet.api.model.User
import de.deftk.lonet.api.model.feature.abstract.IFilePrimitive
import de.deftk.lonet.api.request.MemberApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import java.io.Serializable
import java.util.*


class OnlineFile(id: String, parentId: String, ordinal: Int, name: String, description: String, type: FileType, size: Long, readable: Boolean, writable: Boolean, sparse: Boolean, mine: Boolean, creationDate: Date, creationMember: Member, modificationDate: Date, modificationMember: Member, val member: Member) : IFilePrimitive, Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject, member: Member): OnlineFile {
            //TODO parse "download_notifications" element & implement into api (has "users" array of member & "me" integer)

            val createdObject = jsonObject.get("created").asJsonObject
            val modifiedObject = jsonObject.get("modified").asJsonObject
            return OnlineFile(
                    jsonObject.get("id").asString,
                    jsonObject.get("parent_id").asString,
                    jsonObject.get("ordinal").asInt,
                    jsonObject.get("name").asString,
                    jsonObject.get("description").asString,
                    FileType.getById(jsonObject.get("type").asString),
                    jsonObject.get("size").asLong,
                    jsonObject.get("readable").asInt == 1,
                    jsonObject.get("writable").asInt == 1,
                    jsonObject.get("sparse").asInt == 1,
                    jsonObject.get("mine").asInt == 1,
                    Date(createdObject.get("date").asLong * 1000),
                    Member.fromJson(createdObject.get("user").asJsonObject, null),
                    Date(modifiedObject.get("date").asLong * 1000),
                    Member.fromJson(modifiedObject.get("user").asJsonObject, null),
                    member
            )
        }
    }

    var id = id
        private set
    var parentId = parentId
        private set
    var ordinal = ordinal
        private set
    var name = name
        private set
    var description = description
        private set
    var type = type
        private set
    var size = size
        private set
    var readable = readable
        private set
    var writable = writable
        private set
    var sparse = sparse
        private set
    var mine = mine
        private set
    var creationDate = creationDate
        private set
    var creationMember = creationMember
        private set
    var modificationDate = modificationDate
        private set
    var modificationMember = modificationMember
        private set

    fun getTmpDownloadUrl(user: User, overwriteCache: Boolean = false): FileDownload {
        check(member.responsibleHost != null) { "Can't do API calls for member $member" }
        check(type == FileType.FILE) { "Download urls are only available for files" }
        val request = MemberApiRequest(member.responsibleHost, member.login)
        val id = request.addGetFileDownloadUrl(id)[1]
        val response = request.fireRequest(user, overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return FileDownload.fromJson(subResponse.get("file").asJsonObject)
    }

    fun setName(name: String, user: User) {
        check(member.responsibleHost != null) { "Can't do API calls for member $member" }
        val request = MemberApiRequest(member.responsibleHost, member.login)
        val id = when (type) {
            FileType.FILE -> request.addUpdateFileRequest(id, name = name)[1]
            FileType.FOLDER -> request.addUpdateFolderRequest(id, name = name)[1]
            else -> error("Can't update name")
        }
        val response = request.fireRequest(user, true)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        updateFrom(subResponse.get("file").asJsonObject)
    }

    fun setDescription(description: String, user: User) {
        check(member.responsibleHost != null) { "Can't do API calls for member $member" }
        val request = MemberApiRequest(member.responsibleHost, member.login)
        val id = when (type) {
            FileType.FILE -> request.addUpdateFileRequest(id, description = description)[1]
            FileType.FOLDER -> request.addUpdateFolderRequest(id, description = description)[1]
            else -> error("Can't update description")
        }
        val response = request.fireRequest(user, true)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        updateFrom(subResponse.get("file").asJsonObject)
    }

    fun setReceiveDownloadNotification(receive: Boolean, user: User) {
        check(member.responsibleHost != null) { "Can't do API calls for member $member" }
        check(type == FileType.FILE) { "Function only available for files" }
        val request = MemberApiRequest(member.responsibleHost, member.login)
        val id = when (type) {
            FileType.FILE -> request.addUpdateFileRequest(id, selfDownloadNotification = receive)[1]
            else -> error("Can't update receive update notification state")
        }
        val response = request.fireRequest(user, true)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        updateFrom(subResponse.get("file").asJsonObject)
    }

    fun setReceiveUploadNotification(receive: Boolean, user: User) {
        check(member.responsibleHost != null) { "Can't do API calls for member $member" }
        check(type == FileType.FOLDER) { "Function only available for folders" }
        val request = MemberApiRequest(member.responsibleHost, member.login)
        val id = when (type) {
            FileType.FILE -> request.addUpdateFolderRequest(id, selfUploadNotification = receive)[1]
            else -> error("Can't update receive update notification state")
        }
        val response = request.fireRequest(user, true)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        updateFrom(subResponse.get("file").asJsonObject)
    }

    fun setReadable(readable: Boolean, user: User) {
        check(member.responsibleHost != null) { "Can't do API calls for member $member" }
        check(type == FileType.FOLDER) { "Function only available for folders" }
        val request = MemberApiRequest(member.responsibleHost, member.login)
        val id = when (type) {
            FileType.FILE -> request.addUpdateFolderRequest(id, readable = readable)[1]
            else -> error("Can't update readable state")
        }
        val response = request.fireRequest(user, true)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        updateFrom(subResponse.get("file").asJsonObject)
    }

    fun setWritable(writable: Boolean, user: User) {
        check(member.responsibleHost != null) { "Can't do API calls for member $member" }
        check(type == FileType.FOLDER) { "Function only available for folders" }
        val request = MemberApiRequest(member.responsibleHost, member.login)
        val id = when (type) {
            FileType.FILE -> request.addUpdateFolderRequest(id, writable = writable)[1]
            else -> error("Can't update writable state")
        }
        val response = request.fireRequest(user, true)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        updateFrom(subResponse.get("file").asJsonObject)
    }

    fun delete(user: User) {
        check(member.responsibleHost != null) { "Can't do API calls for member $member" }
        val request = MemberApiRequest(member.responsibleHost, member.login)
        when (type) {
            FileType.FILE -> request.addDeleteFileRequest(id)[1]
            FileType.FOLDER -> request.addDeleteFolderRequest(id)[1]
            else -> error("Can't delete object")
        }
        val response = request.fireRequest(user, true)
        ResponseUtil.checkSuccess(response.toJson())
    }

    //TODO use PrimitiveFileImpl
    override fun createFolder(name: String, description: String?, user: User): OnlineFile {
        check(member.responsibleHost != null) { "Can't do API calls for member $member" }
        val request = MemberApiRequest(member.responsibleHost, member.login)
        val id = request.addAddFolderRequest(id, name, description)[1]
        val response = request.fireRequest(user, true)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return fromJson(subResponse.get("folder").asJsonObject, member)
    }

    //TODO use PrimitiveFileImpl
    override fun getFileStorageFiles(user: User, overwriteCache: Boolean): List<OnlineFile> {
        check(member.responsibleHost != null) { "Can't do API calls for member $member" }
        check(type == FileType.FOLDER) { "File can't have children!" }
        val request = MemberApiRequest(member.responsibleHost, member.login)
        val id = request.addGetFileStorageFilesRequest(id, recursive = false, getFiles = true, getFolders = true)[1]
        val response = request.fireRequest(user, overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("entries")?.asJsonArray?.map { fromJson(it.asJsonObject, member) } ?: emptyList()
    }

    private fun updateFrom(jsonObject: JsonObject) {
        id = jsonObject.get("id").asString
        parentId = jsonObject.get("parent_id").asString
        ordinal = jsonObject.get("ordinal").asInt
        name = jsonObject.get("name").asString
        description = jsonObject.get("description").asString
        type = FileType.getById(jsonObject.get("type").asString)
        size = jsonObject.get("size").asLong
        readable = jsonObject.get("readable").asInt == 1
        writable = jsonObject.get("writable").asInt == 1
        sparse = jsonObject.get("sparse").asInt == 1
        mine = jsonObject.get("mine").asInt == 1

        val createdObject = jsonObject.get("created").asJsonObject
        creationDate = Date(createdObject.get("date").asLong * 1000)
        creationMember = Member.fromJson(createdObject.get("user").asJsonObject, null)

        val modifiedObject = jsonObject.get("modified").asJsonObject
        modificationDate = Date(modifiedObject.get("date").asLong * 1000)
        modificationMember = Member.fromJson(modifiedObject.get("user").asJsonObject, null)

        //TODO parse "download_notifications" element & implement into api (has "users" array of member & "me" integer)
    }

    override fun toString(): String {
        return name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OnlineFile

        if (member != other.member) return false
        if (id != other.id) return false
        if (parentId != other.parentId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = member.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + parentId.hashCode()
        return result
    }

    enum class FileType(val id: String) : Serializable {
        FILE("file"),
        FOLDER("folder"),
        UNKNOWN("");

        companion object {
            fun getById(id: String): FileType {
                return values().firstOrNull { it.id == id } ?: UNKNOWN.apply { println("unknown file type: $id") }
            }
        }
    }


}