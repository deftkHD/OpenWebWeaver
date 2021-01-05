package de.deftk.lonet.api.implementation.feature.filestorage.session

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.FileDownloadUrl
import de.deftk.lonet.api.model.feature.FileUploadUrl
import de.deftk.lonet.api.model.feature.filestorage.io.FileChunk
import de.deftk.lonet.api.model.feature.filestorage.session.ISessionFile
import de.deftk.lonet.api.request.UserApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

@Serializable
class SessionFile(
    private val id: String,
    private val name: String,
    private var size: Int,
    @SerialName("download_url")
    private var downloadUrl: String
): ISessionFile {

    var deleted = false
        private set

    override fun getId(): String = id
    override fun getName(): String = name
    override fun getSize(): Int = size
    override fun getDownloadUrl(): String = downloadUrl

    override fun append(data: ByteArray, context: IRequestContext) {
        val request = UserApiRequest(context)
        val id = request.addAppendSessionFileRequest(id, data)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(Json.decodeFromJsonElement(subResponse["file"]!!))
    }

    override fun download(limit: Int?, offset: Int?, context: IRequestContext): FileChunk {
        val request = UserApiRequest(context)
        val id = request.addGetSessionFileRequest(id, limit, offset)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override fun queryDownloadUrl(context: IRequestContext): FileDownloadUrl {
        val request = UserApiRequest(context)
        val id = request.addGetSessionFileDownloadUrlRequest(id)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override fun queryUploadUrl(context: IRequestContext): FileUploadUrl {
        val request = UserApiRequest(context)
        val id = request.addGetSessionFileUploadUrlRequest(id)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["file"]!!.jsonObject)
    }

    override fun delete(context: IRequestContext) {
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

}