package de.deftk.lonet.api.model.feature.files

import com.google.gson.JsonObject
import de.deftk.lonet.api.LoNet
import de.deftk.lonet.api.model.User
import de.deftk.lonet.api.request.ApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import java.io.Serializable

abstract class FileProvider(private val directory: String, private val responsibleHost: String?, private val login: String): Serializable {

    open fun getFileStorageFiles(user: User, overwriteCache: Boolean): List<OnlineFile> {
        val request = ApiRequest(responsibleHost!!)
        request.addSetFocusRequest("files", login)
        val json = JsonObject()
        json.addProperty("folder_id", directory)
        json.addProperty("get_file_download_url", 1)
        json.addProperty("get_files", 1)
        json.addProperty("get_folders", 1)
        json.addProperty("recursive", 0)
        val id = request.addRequest("get_entries", json)
        val response = request.fireRequest(user, overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("entries")?.asJsonArray?.map { OnlineFile(it.asJsonObject, responsibleHost, login) } ?: emptyList()
    }

}