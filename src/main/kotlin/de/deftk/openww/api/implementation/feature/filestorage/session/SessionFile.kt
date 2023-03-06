package de.deftk.openww.api.implementation.feature.filestorage.session

import de.deftk.openww.api.WebWeaverClient
import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.FileDownloadUrl
import de.deftk.openww.api.model.feature.FileUploadUrl
import de.deftk.openww.api.model.feature.filestorage.io.FileChunk
import de.deftk.openww.api.model.feature.filestorage.session.ISessionFile
import de.deftk.openww.api.request.UserApiRequest
import de.deftk.openww.api.response.ResponseUtil
import de.deftk.openww.api.utils.PlatformUtil
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

@Serializable
class SessionFile(
    override val id: String,
    override val name: String,
    @SerialName("size")
    private var _size: Int,
    @SerialName("download_url")
    private var _downloadUrl: String
): ISessionFile {

    var deleted = false
        private set

    @SerialName("_size")
    override var size: Int = _size
        private set

    @SerialName("_download_url")
    override var downloadUrl: String = _downloadUrl
        private set

    override suspend fun append(data: ByteArray, context: IRequestContext) {
        val request = UserApiRequest(context)
        val base64 = PlatformUtil.base64EncodeToString(data)
        val id = request.addAppendSessionFileRequest(id, base64)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!))
    }

    override suspend fun download(limit: Int?, offset: Int?, context: IRequestContext): FileChunk {
        val request = UserApiRequest(context)
        val id = request.addGetSessionFileRequest(id, limit, offset)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override suspend fun queryDownloadUrl(context: IRequestContext): FileDownloadUrl {
        val request = UserApiRequest(context)
        val id = request.addGetSessionFileDownloadUrlRequest(id)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override suspend fun queryUploadUrl(context: IRequestContext): FileUploadUrl {
        val request = UserApiRequest(context)
        val id = request.addGetSessionFileUploadUrlRequest(id)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!.jsonObject)
    }

    override suspend fun delete(context: IRequestContext) {
        val request = UserApiRequest(context)
        request.addDeleteSessionFileRequest(id)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
        deleted = true
    }

    private fun readFrom(sessionFile: SessionFile) {
        size = sessionFile.size
        downloadUrl = sessionFile.downloadUrl
    }

    override fun toString(): String {
        return "SessionFile(name='$name')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SessionFile) return false

        if (id != other.id) return false
        if (name != other.name) return false
        if (_size != other._size) return false
        if (_downloadUrl != other._downloadUrl) return false
        if (deleted != other.deleted) return false
        if (size != other.size) return false
        if (downloadUrl != other.downloadUrl) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + _size
        result = 31 * result + _downloadUrl.hashCode()
        result = 31 * result + deleted.hashCode()
        result = 31 * result + size
        result = 31 * result + downloadUrl.hashCode()
        return result
    }

}