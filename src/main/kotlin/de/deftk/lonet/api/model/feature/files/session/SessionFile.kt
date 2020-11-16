package de.deftk.lonet.api.model.feature.files.session

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.User
import de.deftk.lonet.api.model.feature.files.FileChunk
import de.deftk.lonet.api.request.UserApiRequest
import de.deftk.lonet.api.response.ResponseUtil

class SessionFile(val id: String, val name: String, size: Int, downloadUrl: String, val user: User) {

    companion object {
        fun fromJson(jsonObject: JsonObject, user: User): SessionFile {
            return SessionFile(
                    jsonObject.get("id").asString,
                    jsonObject.get("name").asString,
                    jsonObject.get("size").asInt,
                    jsonObject.get("download_url").asString,
                    user
            )
        }
    }

    var size = size
        private set
    var downloadUrl = downloadUrl
        private set

    fun appendData(data: ByteArray) {
        val request = UserApiRequest(user)
        val id = request.addAppendSessionFileRequest(id, data)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(subResponse.get("file").asJsonObject)
    }

    fun delete() {
        val request = UserApiRequest(user)
        request.addDeleteSessionFileRequest(id)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    fun download(limit: Int? = null, offset: Int? = null): FileChunk {
        val request = UserApiRequest(user)
        val id = request.addGetSessionFileRequest(id, limit, offset)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return FileChunk.fromJson(subResponse.get("file").asJsonObject)
    }

    private fun readFrom(jsonObject: JsonObject) {
        size = jsonObject.get("size").asInt
        downloadUrl = jsonObject.get("download_url").asString
    }

}