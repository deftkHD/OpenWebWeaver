package de.deftk.lonet.api.model.feature.files

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.abstract.AbstractOperator
import de.deftk.lonet.api.model.abstract.IManageable
import de.deftk.lonet.api.model.feature.abstract.IFilePrimitive
import de.deftk.lonet.api.model.feature.files.filters.FileFilter
import de.deftk.lonet.api.request.OperatorApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import java.io.Serializable
import java.util.*


class OnlineFile(id: String, parentId: String?, ordinal: Int?, name: String, description: String?, type: FileType?, size: Long, readable: Boolean?, writable: Boolean?, sparse: Boolean?, mine: Boolean?, shared: Boolean?, creationDate: Date, creationMember: IManageable, modificationDate: Date, modificationMember: IManageable, effectiveRead: Boolean?, effectiveWrite: Boolean?, effectiveModify: Boolean?, effectiveDelete: Boolean?, preview: Boolean?, val operator: AbstractOperator) : IFilePrimitive, Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject, operator: AbstractOperator): OnlineFile {
            val createdObject = jsonObject.get("created").asJsonObject
            val modifiedObject = jsonObject.get("modified").asJsonObject
            val effectiveObject = jsonObject.get("effective")?.asJsonObject
            return OnlineFile(
                    jsonObject.get("id").asString,
                    jsonObject.get("parent_id")?.asString,
                    jsonObject.get("ordinal")?.asInt,
                    jsonObject.get("name").asString,
                    jsonObject.get("description")?.asString,
                    if (jsonObject.has("type")) FileType.getById(jsonObject.get("type").asString) else null,
                    jsonObject.get("size").asLong,
                    jsonObject.get("readable")?.asInt?.equals(1),
                    jsonObject.get("writable")?.asInt?.equals(1),
                    jsonObject.get("sparse")?.asInt?.equals(1),
                    jsonObject.get("mine")?.asInt?.equals(1),
                    jsonObject.get("shared")?.asInt?.equals(1),
                    Date(createdObject.get("date").asLong * 1000),
                    operator.getContext().getOrCreateManageable(createdObject.get("user").asJsonObject),
                    Date(modifiedObject.get("date").asLong * 1000),
                    operator.getContext().getOrCreateManageable(modifiedObject.get("user").asJsonObject),
                    effectiveObject?.get("read")?.asInt?.equals(1),
                    effectiveObject?.get("write")?.asInt?.equals(1),
                    effectiveObject?.get("modify")?.asInt?.equals(1),
                    effectiveObject?.get("delete")?.asInt?.equals(1),
                    jsonObject.get("preview")?.asInt?.equals(1),
                    operator
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
    var shared = shared
        private set
    var creationDate = creationDate
        private set
    var creationMember = creationMember
        private set
    var modificationDate = modificationDate
        private set
    var modificationMember = modificationMember
        private set
    var effectiveRead = effectiveRead
        private set
    var effectiveWrite = effectiveWrite
        private set
    var effectiveDelete = effectiveDelete
        private set
    var effectiveModify = effectiveModify
        private set
    var preview = preview
        private set

    override fun getFiles(filter: FileFilter?): List<OnlineFile> {
        check(type == FileType.FOLDER) { "File can't have children!" }
        val request = OperatorApiRequest(operator)
        val id = request.addGetFileStorageFilesRequest(
                id,
                recursive = false,
                getFiles = true,
                getFolders = true,
                searchString = filter?.searchTerm,
                searchMode = filter?.searchMode
        )[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("entries")?.asJsonArray?.map { fromJson(it.asJsonObject, operator) } ?: emptyList()
    }

    fun download(): FileChunk {
        check(type == FileType.FILE) { "Download only available for files" }
        val request = OperatorApiRequest(operator)
        val id = request.addGetFileRequest(id)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return FileChunk.fromJson(subResponse.get("file").asJsonObject)
    }

    fun getPreviewDownloadUrl(): FileDownloadUrl {
        check(type == FileType.FILE) { "Preview only available for files" }
        val request = OperatorApiRequest(operator)
        val id = request.addGetPreviewDownloadUrlRequest(id)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return FileDownloadUrl.fromPreviewJson(subResponse.get("file").asJsonObject)
    }

    fun getTempDownloadUrl(): FileDownloadUrl {
        check(type == FileType.FILE) { "Download urls are only available for files" }
        val request = OperatorApiRequest(operator)
        val id = request.addGetFileDownloadUrlRequest(id)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return FileDownloadUrl.fromJson(subResponse.get("file").asJsonObject)
    }

    fun getFileProxyNonce(): FileProxyNonce {
        check(type == FileType.FILE) { "Proxies are only available for files" }
        val request = OperatorApiRequest(operator)
        val id = request.addGetFileProxyNonceRequest(id)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return FileProxyNonce.fromJson(subResponse.get("file").asJsonObject)
    }

    fun addFile(name: String, data: ByteArray, description: String? = null): OnlineFile {
        check(type == FileType.FOLDER) { "Uploading is only available for folders" }
        val request = OperatorApiRequest(operator)
        val id = request.addAddFileRequest(Base64.getEncoder().encodeToString(data), id, name, description)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return fromJson(subResponse.get("file").asJsonObject, operator)
    }

    fun delete() {
        val request = OperatorApiRequest(operator)
        when (type) {
            FileType.FILE -> request.addDeleteFileRequest(id)[1]
            FileType.FOLDER -> request.addDeleteFolderRequest(id)[1]
            else -> error("Can't delete object")
        }
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun addFolder(name: String, description: String?): OnlineFile {
        val request = OperatorApiRequest(operator)
        val id = request.addAddFolderRequest(id, name, description)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return fromJson(subResponse.get("folder").asJsonObject, operator)
    }

    fun setName(name: String) {
        val request = OperatorApiRequest(operator)
        val id = when (type) {
            FileType.FILE -> request.addSetFileRequest(id, name = name)[1]
            FileType.FOLDER -> request.addSetFolderRequest(id, name = name)[1]
            else -> error("Can't update name")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(subResponse.get("file").asJsonObject)
    }

    fun setDescription(description: String) {
        val request = OperatorApiRequest(operator)
        val id = when (type) {
            FileType.FILE -> request.addSetFileRequest(id, description = description)[1]
            FileType.FOLDER -> request.addSetFolderRequest(id, description = description)[1]
            else -> error("Can't update description")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(subResponse.get("file").asJsonObject)
    }

    fun setDownloadNotificationAddLogin(login: String) {
        check(type == FileType.FILE) { "Download notifications only available for files" }
        val request = OperatorApiRequest(operator)
        val id = when (type) {
            FileType.FILE -> request.addSetFileRequest(id, downloadNotificationAddLogin = login)[1]
            else -> error("Can't add download logins")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(subResponse.get("file").asJsonObject)
    }

    fun setDownloadNotificationDeleteLogin(login: String) {
        check(type == FileType.FILE) { "Download notifications only available for files" }
        val request = OperatorApiRequest(operator)
        val id = when (type) {
            FileType.FILE -> request.addSetFileRequest(id, downloadNotificationDeleteLogin = login)[1]
            else -> error("Can't delete download logins")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(subResponse.get("file").asJsonObject)
    }

    fun setDownloadNotificationMe(receive: Boolean) {
        check(type == FileType.FILE) { "Download notifications only available for files" }
        val request = OperatorApiRequest(operator)
        val id = when (type) {
            FileType.FILE -> request.addSetFileRequest(id, downloadNotificationMe = receive)[1]
            else -> error("Can't update receive update notification state")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(subResponse.get("file").asJsonObject)
    }

    fun setUploadNotificationAddLogin(login: String) {
        check(type == FileType.FOLDER) { "Upload notifications only available for folders" }
        val request = OperatorApiRequest(operator)
        val id = when (type) {
            FileType.FILE -> request.addSetFolderRequest(id, uploadNotificationAddLogin = login)[1]
            else -> error("Can't delete add logins")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(subResponse.get("file").asJsonObject)
    }

    fun setUploadNotificationDeleteLogin(login: String) {
        check(type == FileType.FOLDER) { "Upload notifications only available for folders" }
        val request = OperatorApiRequest(operator)
        val id = when (type) {
            FileType.FILE -> request.addSetFolderRequest(id, uploadNotificationDeleteLogin = login)[1]
            else -> error("Can't delete upload logins")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(subResponse.get("file").asJsonObject)
    }

    fun setUploadNotificationMe(receive: Boolean) {
        check(type == FileType.FOLDER) { "Upload notifications only available for folders" }
        val request = OperatorApiRequest(operator)
        val id = when (type) {
            FileType.FILE -> request.addSetFolderRequest(id, uploadNotificationMe = receive)[1]
            else -> error("Can't update receive update notification state")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(subResponse.get("file").asJsonObject)
    }

    fun setReadable(readable: Boolean) {
        check(type == FileType.FOLDER) { "Function only available for folders" }
        val request = OperatorApiRequest(operator)
        val id = when (type) {
            FileType.FILE -> request.addSetFolderRequest(id, readable = readable)[1]
            else -> error("Can't update readable state")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(subResponse.get("file").asJsonObject)
    }

    fun setWritable(writable: Boolean) {
        check(type == FileType.FOLDER) { "Function only available for folders" }
        val request = OperatorApiRequest(operator)
        val id = when (type) {
            FileType.FILE -> request.addSetFolderRequest(id, writable = writable)[1]
            else -> error("Can't update writable state")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(subResponse.get("file").asJsonObject)
    }

    private fun readFrom(jsonObject: JsonObject) {
        id = jsonObject.get("id").asString
        parentId = jsonObject.get("parent_id")?.asString
        ordinal = jsonObject.get("ordinal")?.asInt
        name = jsonObject.get("name").asString
        description = jsonObject.get("description")?.asString
        type = if (jsonObject.has("type")) FileType.getById(jsonObject.get("type").asString) else null
        size = jsonObject.get("size").asLong
        readable = jsonObject.get("readable")?.asInt?.equals(1)
        writable = jsonObject.get("writable")?.asInt?.equals(1)
        sparse = jsonObject.get("sparse")?.asInt?.equals(1)
        mine = jsonObject.get("mine")?.asInt?.equals(1)
        shared = jsonObject.get("shared")?.asInt?.equals(1)

        val createdObject = jsonObject.get("created").asJsonObject
        creationDate = Date(createdObject.get("date").asLong * 1000)
        creationMember = operator.getContext().getOrCreateManageable(createdObject.get("user").asJsonObject)

        val modifiedObject = jsonObject.get("modified").asJsonObject
        modificationDate = Date(modifiedObject.get("date").asLong * 1000)
        modificationMember = operator.getContext().getOrCreateManageable(modifiedObject.get("user").asJsonObject)

        val effectiveObject = jsonObject.get("effective")?.asJsonObject
        effectiveRead = effectiveObject?.get("read")?.asInt?.equals(1)
        effectiveWrite = effectiveObject?.get("write")?.asInt?.equals(1)
        effectiveModify = effectiveObject?.get("modify")?.asInt?.equals(1)
        effectiveDelete = effectiveObject?.get("delete")?.asInt?.equals(1)

        preview = jsonObject.get("preview")?.asInt?.equals(1)
    }

    override fun getTrash(limit: Int?): List<OnlineFile> {
        val request = OperatorApiRequest(operator)
        val id = request.addGetTrashRequest(limit)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("files").asJsonArray.map { fromJson(it.asJsonObject, operator) }
    }

    override fun toString(): String {
        return name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OnlineFile

        if (operator != other.operator) return false
        if (id != other.id) return false
        if (parentId != other.parentId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = operator.hashCode()
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