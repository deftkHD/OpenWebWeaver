package de.deftk.openww.api.implementation.feature.filestorage

import de.deftk.openww.api.WebWeaverClient
import de.deftk.openww.api.implementation.feature.filestorage.session.SessionFile
import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.IUser
import de.deftk.openww.api.model.feature.FileDownloadUrl
import de.deftk.openww.api.model.feature.FilePreviewUrl
import de.deftk.openww.api.model.feature.Modification
import de.deftk.openww.api.model.feature.filestorage.DownloadNotification
import de.deftk.openww.api.model.feature.filestorage.FileAggregation
import de.deftk.openww.api.model.feature.filestorage.FileType
import de.deftk.openww.api.model.feature.filestorage.IRemoteFile
import de.deftk.openww.api.model.feature.filestorage.filter.FileFilter
import de.deftk.openww.api.model.feature.filestorage.io.FileChunk
import de.deftk.openww.api.model.feature.filestorage.proxy.ProxyNonce
import de.deftk.openww.api.model.feature.filestorage.session.ISessionFile
import de.deftk.openww.api.request.OperatingScopeApiRequest
import de.deftk.openww.api.response.ResponseUtil
import de.deftk.openww.api.serialization.BooleanFromIntSerializer
import de.deftk.openww.api.utils.PlatformUtil
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

@Serializable
open class RemoteFile(
    override val id: String,
    @SerialName("parent_id")
    private val _parentId: String? = null,
    @SerialName("ordinal")
    private var _ordinal: Int? = null,
    @SerialName("name")
    private var _name: String,
    @SerialName("description")
    private var _description: String? = null,
    override val type: FileType,
    @SerialName("size")
    private var _size: Long,
    @SerialName("readable")
    @Serializable(with = BooleanFromIntSerializer::class)
    private var _readable: Boolean? = null,
    @SerialName("writable")
    @Serializable(with = BooleanFromIntSerializer::class)
    private var _writable: Boolean? = null,
    @SerialName("sparse")
    @Serializable(with = BooleanFromIntSerializer::class)
    private var _sparse: Boolean? = null,
    @SerialName("mine")
    @Serializable(with = BooleanFromIntSerializer::class)
    private var _mine: Boolean? = null,
    @SerialName("shared")
    @Serializable(with = BooleanFromIntSerializer::class)
    private var _shared: Boolean? = null,
    override val created: Modification,
    @SerialName("modified")
    private var _modified: Modification,
    private var effective: Effectiveness,
    @SerialName("preview")
    @Serializable(with = BooleanFromIntSerializer::class)
    private var _preview: Boolean? = null,
    @SerialName("empty")
    @Serializable(with = BooleanFromIntSerializer::class)
    private var _empty: Boolean? = null,
    @SerialName("sparseKey")
    private var _sparseKey: String? = null,
    @SerialName("download_notification")
    private var _downloadNotification: DownloadNotification? = null,
    @SerialName("aggregation")
    private var _aggregation: FileAggregation? = null
) : IRemoteFile {

    @SerialName("_modified")
    override var modified: Modification = _modified
        protected set

    @SerialName("_parent_id")
    override var parentId: String? = _parentId
        protected set

    @SerialName("_ordinal")
    override var ordinal: Int? = _ordinal
        protected set

    @SerialName("_description")
    override var description: String? = _description
        protected set

    @SerialName("_size")
    override var size: Long = _size
        protected set

    @SerialName("_readable")
    override var readable: Boolean? = _readable
        protected set

    @SerialName("_writable")
    override var writable: Boolean? = _writable
        protected set

    @SerialName("_sparse_file")
    override var sparse: Boolean? = _sparse
        protected set

    @SerialName("_is_empty")
    override var empty: Boolean? = _empty
        protected set

    @SerialName("_sparse_key")
    override var sparseKey: String? = _sparseKey
        protected set

    @SerialName("_preview")
    override var preview: Boolean? = _preview
        protected set

    @SerialName("_mine")
    override var mine: Boolean? = _mine
        protected set

    @SerialName("_shared")
    override var shared: Boolean? = _shared
        protected set

    @SerialName("_effective_read")
    override var effectiveRead: Boolean? = effective.read
        protected set

    @SerialName("_effective_create")
    override var effectiveCreate: Boolean? = effective.create
        protected set

    @SerialName("_effective_modify")
    override var effectiveModify: Boolean? = effective.modify
        protected set

    @SerialName("_effective_delete")
    override var effectiveDelete: Boolean? = effective.delete
        protected set

    @SerialName("_download_notification")
    override var downloadNotification: DownloadNotification? = _downloadNotification
        protected set

    @SerialName("_name")
    override var name: String = _name
        protected set

    @SerialName("_aggregation")
    override var aggregation: FileAggregation? = _aggregation
        protected set

    override suspend fun download(limit: Int?, offset: Int?, context: IRequestContext): FileChunk {
        requireFile()
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetFileRequest(id, limit, offset)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!.jsonObject)
    }

    override suspend fun getPreviewUrl(context: IRequestContext): FilePreviewUrl {
        requireFile()
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetPreviewDownloadUrlRequest(id)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override suspend fun getDownloadUrl(context: IRequestContext): FileDownloadUrl {
        requireFile()
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetFileDownloadUrlRequest(id)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override suspend fun getProxyNonce(context: IRequestContext): ProxyNonce {
        requireFile()
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetFileProxyNonceRequest(id)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!.jsonObject)
    }

    override suspend fun delete(context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        when (type) {
            FileType.FILE -> request.addDeleteFileRequest(id)
            FileType.FOLDER -> request.addDeleteFolderRequest(id)
        }
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override suspend fun setName(name: String, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        val id = when (type) {
            FileType.FILE -> request.addSetFileRequest(id, description, null, null, downloadNotification?.me, name, parentId!!)
            FileType.FOLDER -> request.addSetFolderRequest(id, description, name, readable, null, null, null, writable)
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        val respObj = when (type) {
            FileType.FILE -> "file"
            FileType.FOLDER -> "folder"
        }
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse[respObj]!!))
    }

    override suspend fun setDescription(description: String, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        val id = when (type) {
            FileType.FILE -> request.addSetFileRequest(id, description, null, null, downloadNotification?.me, name, parentId!!)
            FileType.FOLDER -> request.addSetFolderRequest(id, description, name, readable, null, null, null, writable)
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        val respObj = when (type) {
            FileType.FILE -> "file"
            FileType.FOLDER -> "folder"
        }
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse[respObj]!!))
    }

    override suspend fun setDownloadNotificationAddLogin(login: String, context: IRequestContext) {
        requireFile()
        val request = OperatingScopeApiRequest(context)
        val id = when (type) {
            FileType.FILE -> request.addSetFileRequest(id, description, login, null, downloadNotification?.me, name, parentId!!)
            else -> error("Can't add download logins")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!))
    }

    override suspend fun setDownloadNotificationDeleteLogin(login: String, context: IRequestContext) {
        requireFile()
        val request = OperatingScopeApiRequest(context)
        val id = when (type) {
            FileType.FILE -> request.addSetFileRequest(id, description, null, login, downloadNotification?.me, name, parentId!!)
            else -> error("Can't delete download logins")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!))
    }

    override suspend fun setDownloadNotificationMe(receive: Boolean, context: IRequestContext) {
        requireFile()
        val request = OperatingScopeApiRequest(context)
        val id = when (type) {
            FileType.FILE -> request.addSetFileRequest(id, description, null, null, receive, name, parentId!!)
            else -> error("Can't update receive update notification state")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!))
    }

    override suspend fun setUploadNotificationAddLogin(login: String, context: IRequestContext) {
        requireFolder()
        val request = OperatingScopeApiRequest(context)
        val id = when (type) {
            FileType.FILE -> request.addSetFolderRequest(id, description, name, readable, login, null, null, writable)
            else -> error("Can't delete add logins")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!))
    }

    override suspend fun setUploadNotificationDeleteLogin(login: String, context: IRequestContext) {
        requireFolder()
        val request = OperatingScopeApiRequest(context)
        val id = when (type) {
            FileType.FILE -> request.addSetFolderRequest(id, description, name, readable, null, login, null, writable)
            else -> error("Can't delete upload logins")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!))
    }

    override suspend fun setUploadNotificationMe(receive: Boolean, context: IRequestContext) {
        requireFolder()
        val request = OperatingScopeApiRequest(context)
        val id = when (type) {
            FileType.FILE -> request.addSetFolderRequest(id, description, name, readable, null, null, receive, writable)
            else -> error("Can't update receive update notification state")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!))
    }

    override suspend fun exportSessionFile(user: IUser, context: IRequestContext): SessionFile {
        requireFile()
        val request = OperatingScopeApiRequest(context)
        val id = request.addExportSessionFileRequest(id)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override suspend fun getRootFile(context: IRequestContext): IRemoteFile {
        requireFolder()
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetFileStorageFilesRequest(
            folderId = id,
            getFolder = true,
            getFiles = false,
            getFolders = false,
            recursive = false
        )
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]!!.jsonArray.map { WebWeaverClient.json.decodeFromJsonElement<RemoteFile>(it) }[0]
    }

    override suspend fun getFiles(limit: Int?, offset: Int?, filter: FileFilter?, context: IRequestContext): List<RemoteFile> {
        requireFolder()
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetFileStorageFilesRequest(
            id,
            recursive = false,
            getFiles = true,
            getFolders = true,
            searchString = filter?.searchTerm,
            searchMode = filter?.searchMode
        )
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]?.jsonArray?.map { WebWeaverClient.json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override suspend fun addFile(name: String, data: ByteArray, description: String?, context: IRequestContext): RemoteFile {
        requireFolder()
        val request = OperatingScopeApiRequest(context)
        val id = request.addAddFileRequest(PlatformUtil.base64EncodeToString(data), id, name, description)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override suspend fun addSparseFile(name: String, size: Int, description: String?, context: IRequestContext): RemoteFile {
        requireFolder()
        val request = OperatingScopeApiRequest(context)
        val id = request.addAddSparseFileRequest(id, name, size, description)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override suspend fun importSessionFile(sessionFile: ISessionFile, createCopy: Boolean?, description: String?, context: IRequestContext): RemoteFile {
        requireFolder()
        val request = OperatingScopeApiRequest(context)
        val id = request.addImportSessionFileRequest(sessionFile.id, createCopy, description, folderId = id)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override suspend fun addFolder(name: String, description: String?, context: IRequestContext): RemoteFile {
        requireFolder()
        val request = OperatingScopeApiRequest(context)
        val id = request.addAddFolderRequest(id, name, description)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["folder"]!!)
    }

    override suspend fun setReadable(readable: Boolean, context: IRequestContext) {
        requireFolder()
        val request = OperatingScopeApiRequest(context)
        val id = when (type) {
            FileType.FOLDER -> request.addSetFolderRequest(id, description, name, readable, null, null, null, writable)
            else -> error("Can't update readable state")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse["folder"]!!))
    }

    override suspend fun setWritable(writable: Boolean, context: IRequestContext) {
        requireFolder()
        val request = OperatingScopeApiRequest(context)
        val id = when (type) {
            FileType.FOLDER -> request.addSetFolderRequest(id, description, name, readable, null, null, null, writable)
            else -> error("Can't update writable state")
        }
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse["folder"]!!))
    }

    override suspend fun setFile(name: String, description: String?, downloadNotificationAddLogin: String?, downloadNotificationDeleteLogin: String?, downloadNotificationMe: Boolean?, context: IRequestContext) {
        requireFile()
        val request = OperatingScopeApiRequest(context)
        val id = request.addSetFileRequest(id, description, downloadNotificationAddLogin, downloadNotificationDeleteLogin, downloadNotificationMe, name, parentId!!)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!))
    }

    override suspend fun setFolder(name: String, description: String?, readable: Boolean?, writable: Boolean?, uploadNotificationAddLogin: String?, uploadNotificationDeleteLogin: String?, uploadNotificationMe: Boolean?, context: IRequestContext) {
        requireFolder()
        val request = OperatingScopeApiRequest(context)
        val id = request.addSetFolderRequest(id, description, name, readable, uploadNotificationAddLogin, uploadNotificationDeleteLogin, uploadNotificationMe, writable)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse["folder"]!!))
    }

    private fun readFrom(file: RemoteFile) {
        ordinal = file.ordinal
        name = file.name
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
        aggregation = file.aggregation
    }

    private fun requireFile() {
        check(type == FileType.FILE) { "Feature only available for files" }
    }

    private fun requireFolder() {
        check(type == FileType.FOLDER) { "Feature only available for folders" }
    }

    override fun toString(): String {
        return "RemoteFile(name='$name')"
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