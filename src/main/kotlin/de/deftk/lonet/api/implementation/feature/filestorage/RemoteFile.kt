package de.deftk.lonet.api.implementation.feature.filestorage

import de.deftk.lonet.api.implementation.feature.filestorage.session.SessionFile
import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.IUser
import de.deftk.lonet.api.model.feature.FileDownloadUrl
import de.deftk.lonet.api.model.feature.FilePreviewUrl
import de.deftk.lonet.api.model.feature.Modification
import de.deftk.lonet.api.model.feature.filestorage.DownloadNotification
import de.deftk.lonet.api.model.feature.filestorage.FileType
import de.deftk.lonet.api.model.feature.filestorage.IRemoteFile
import de.deftk.lonet.api.model.feature.filestorage.filter.FileFilter
import de.deftk.lonet.api.model.feature.filestorage.io.FileChunk
import de.deftk.lonet.api.model.feature.filestorage.proxy.ProxyNonce
import de.deftk.lonet.api.model.feature.filestorage.session.ISessionFile
import de.deftk.lonet.api.request.OperatingScopeApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import de.deftk.lonet.api.serialization.BooleanFromIntSerializer
import de.deftk.lonet.api.utils.PlatformUtil
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

@Serializable
class RemoteFile(
    private val id: String,
    @SerialName("parent_id")
    private val parentId: String? = null,
    private var ordinal: Int? = null,
    @SerialName("name")
    private var shadowedName: String,
    private var description: String? = null,
    private val type: FileType,
    private var size: Long,
    @Serializable(with = BooleanFromIntSerializer::class)
    private var readable: Boolean? = null,
    @Serializable(with = BooleanFromIntSerializer::class)
    private var writable: Boolean? = null,
    @Serializable(with = BooleanFromIntSerializer::class)
    private var sparse: Boolean? = null,
    @Serializable(with = BooleanFromIntSerializer::class)
    private var mine: Boolean? = null,
    @Serializable(with = BooleanFromIntSerializer::class)
    private var shared: Boolean? = null,
    private val created: Modification,
    private var modified: Modification,
    private var effective: Effectiveness,
    @Serializable(with = BooleanFromIntSerializer::class)
    private var preview: Boolean? = null,
    @Serializable(with = BooleanFromIntSerializer::class)
    private var empty: Boolean? = null,
    private var sparseKey: String? = null,
    @SerialName("download_notification")
    private var downloadNotification: DownloadNotification? = null
) : IRemoteFile {

    override fun getCreated(): Modification = created
    override fun getModified(): Modification = modified
    override fun getId(): String = id
    override fun getParentId(): String? = parentId
    override val name: String
        get() = shadowedName
    override fun getOrdinal(): Int? = ordinal
    override fun getDescription(): String? = description
    override fun getType(): FileType = type
    override fun getSize(): Long = size
    override fun isReadable(): Boolean? = readable
    override fun isWritable(): Boolean? = writable
    override fun isSparseFile(): Boolean? = sparse
    override fun getSparseKey(): String? = sparseKey
    override fun hasPreview(): Boolean? = preview
    override fun isMine(): Boolean? = mine
    override fun isShared(): Boolean? = shared
    override fun effectiveRead(): Boolean? = effective.read
    override fun effectiveCreate(): Boolean? = effective.create
    override fun effectiveModify(): Boolean? = effective.modify
    override fun effectiveDelete(): Boolean? = effective.delete
    override fun getDownloadNotification(): DownloadNotification? = downloadNotification

    override fun download(limit: Int?, offset: Int?, context: IRequestContext): FileChunk {
        requireFile()
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetFileRequest(id, limit, offset)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["file"]!!.jsonObject)
    }

    override fun getPreviewUrl(context: IRequestContext): FilePreviewUrl {
        requireFile()
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetPreviewDownloadUrlRequest(id)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override fun getDownloadUrl(context: IRequestContext): FileDownloadUrl {
        requireFile()
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetFileDownloadUrlRequest(id)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override fun getProxyNonce(context: IRequestContext): ProxyNonce {
        requireFile()
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetFileProxyNonceRequest(id)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["file"]!!.jsonObject)
    }

    override fun delete(context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        when (type) {
            FileType.FILE -> request.addDeleteFileRequest(id)[1]
            FileType.FOLDER -> request.addDeleteFolderRequest(id)[1]
        }
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun setName(name: String, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        val id = when (type) {
            FileType.FILE -> request.addSetFileRequest(id, name = name)[1]
            FileType.FOLDER -> request.addSetFolderRequest(id, name = name)[1]
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(Json.decodeFromJsonElement(subResponse["file"]!!))
    }

    override fun setDescription(description: String, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        val id = when (type) {
            FileType.FILE -> request.addSetFileRequest(id, description = description)[1]
            FileType.FOLDER -> request.addSetFolderRequest(id, description = description)[1]
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(Json.decodeFromJsonElement(subResponse["file"]!!))
    }

    override fun setDownloadNotificationAddLogin(login: String, context: IRequestContext) {
        requireFile()
        val request = OperatingScopeApiRequest(context)
        val id = when (type) {
            FileType.FILE -> request.addSetFileRequest(id, downloadNotificationAddLogin = login)[1]
            else -> error("Can't add download logins")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(Json.decodeFromJsonElement(subResponse["file"]!!))
    }

    override fun setDownloadNotificationDeleteLogin(login: String, context: IRequestContext) {
        requireFile()
        val request = OperatingScopeApiRequest(context)
        val id = when (type) {
            FileType.FILE -> request.addSetFileRequest(id, downloadNotificationDeleteLogin = login)[1]
            else -> error("Can't delete download logins")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(Json.decodeFromJsonElement(subResponse["file"]!!))
    }

    override fun setDownloadNotificationMe(receive: Boolean, context: IRequestContext) {
        requireFile()
        val request = OperatingScopeApiRequest(context)
        val id = when (type) {
            FileType.FILE -> request.addSetFileRequest(id, downloadNotificationMe = receive)[1]
            else -> error("Can't update receive update notification state")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(Json.decodeFromJsonElement(subResponse["file"]!!))
    }

    override fun setUploadNotificationAddLogin(login: String, context: IRequestContext) {
        requireFolder()
        val request = OperatingScopeApiRequest(context)
        val id = when (type) {
            FileType.FILE -> request.addSetFolderRequest(id, uploadNotificationAddLogin = login)[1]
            else -> error("Can't delete add logins")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(Json.decodeFromJsonElement(subResponse["file"]!!))
    }

    override fun setUploadNotificationDeleteLogin(login: String, context: IRequestContext) {
        requireFolder()
        val request = OperatingScopeApiRequest(context)
        val id = when (type) {
            FileType.FILE -> request.addSetFolderRequest(id, uploadNotificationDeleteLogin = login)[1]
            else -> error("Can't delete upload logins")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(Json.decodeFromJsonElement(subResponse["file"]!!))
    }

    override fun setUploadNotificationMe(receive: Boolean, context: IRequestContext) {
        requireFolder()
        val request = OperatingScopeApiRequest(context)
        val id = when (type) {
            FileType.FILE -> request.addSetFolderRequest(id, uploadNotificationMe = receive)[1]
            else -> error("Can't update receive update notification state")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(Json.decodeFromJsonElement(subResponse["file"]!!))
    }

    override fun exportSessionFile(user: IUser, context: IRequestContext): SessionFile {
        requireFile()
        val request = OperatingScopeApiRequest(context)
        val id = request.addExportSessionFileRequest(id)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override fun getFiles(limit: Int?, offset: Int?, filter: FileFilter?, context: IRequestContext): List<RemoteFile> {
        requireFolder()
        val request = OperatingScopeApiRequest(context)
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
        return subResponse["entries"]?.jsonArray?.map { Json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override fun addFile(name: String, data: ByteArray, description: String?, context: IRequestContext): RemoteFile {
        requireFolder()
        val request = OperatingScopeApiRequest(context)
        val id = request.addAddFileRequest(PlatformUtil.base64EncodeToString(data), id, name, description)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override fun addSparseFile(name: String, size: Int, description: String?, context: IRequestContext): RemoteFile {
        requireFolder()
        val request = OperatingScopeApiRequest(context)
        val id = request.addAddSparseFileRequest(id, name, size, description)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override fun importSessionFile(sessionFile: ISessionFile, createCopy: Boolean?, description: String?, context: IRequestContext): RemoteFile {
        requireFolder()
        val request = OperatingScopeApiRequest(context)
        val id = request.addImportSessionFileRequest(sessionFile.getId(), createCopy, description, folderId = id)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override fun addFolder(name: String, description: String?, context: IRequestContext): RemoteFile {
        requireFolder()
        val request = OperatingScopeApiRequest(context)
        val id = request.addAddFolderRequest(id, name, description)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["folder"]!!)
    }

    override fun setReadable(readable: Boolean, context: IRequestContext) {
        requireFolder()
        val request = OperatingScopeApiRequest(context)
        val id = when (type) {
            FileType.FOLDER -> request.addSetFolderRequest(id, readable = readable)[1]
            else -> error("Can't update readable state")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(Json.decodeFromJsonElement(subResponse["folder"]!!))
    }

    override fun setWritable(writable: Boolean, context: IRequestContext) {
        requireFolder()
        val request = OperatingScopeApiRequest(context)
        val id = when (type) {
            FileType.FOLDER -> request.addSetFolderRequest(id, writable = writable)[1]
            else -> error("Can't update writable state")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(Json.decodeFromJsonElement(subResponse["folder"]!!))
    }

    private fun readFrom(file: RemoteFile) {
        ordinal = file.ordinal
        shadowedName = file.shadowedName
        description = file.description
        size = file.size
        readable = file.readable
        writable = file.writable
        sparse = file.sparse
        mine = file.mine
        shared = file.shared
        modified = file.modified
        effective = file.effective
        preview = file.preview
        empty = file.empty
        sparseKey = file.sparseKey
    }

    private fun requireFile() {
        check(type == FileType.FILE) { "Feature only available for files" }
    }

    private fun requireFolder() {
        check(type == FileType.FOLDER) { "Feature only available for folders" }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RemoteFile

        if (id != other.id) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }


    @Serializable
    data class Effectiveness(
        @Serializable(with = BooleanFromIntSerializer::class)
        val read: Boolean? = null,
        @Serializable(with = BooleanFromIntSerializer::class)
        val create: Boolean? = null,
        @Serializable(with = BooleanFromIntSerializer::class)
        val modify: Boolean? = null,
        @Serializable(with = BooleanFromIntSerializer::class)
        val delete: Boolean? = null
    )

}