package de.deftk.openww.api.implementation.feature.filestorage.session

import de.deftk.openww.api.WebWeaverClient
import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.FileDownloadUrl
import de.deftk.openww.api.model.feature.FileUploadUrl
import de.deftk.openww.api.model.feature.filestorage.io.FileChunk
import de.deftk.openww.api.model.feature.filestorage.session.ISessionFile
import de.deftk.openww.api.request.UserApiRequest
import de.deftk.openww.api.response.ResponseUtil
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
        val id = request.addAppendSessionFileRequest(id, data)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!))
    }

    override suspend fun download(limit: Int?, offset: Int?, context: IRequestContext): FileChunk {
        val request = UserApiRequest(context)
        val id = request.addGetSessionFileRequest(id, limit, offset)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override suspend fun queryDownloadUrl(context: IRequestContext): FileDownloadUrl {
        val request = UserApiRequest(context)
        val id = request.addGetSessionFileDownloadUrlRequest(id)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override suspend fun queryUploadUrl(context: IRequestContext): FileUploadUrl {
        val request = UserApiRequest(context)
        val id = request.addGetSessionFileUploadUrlRequest(id)[1]
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

}