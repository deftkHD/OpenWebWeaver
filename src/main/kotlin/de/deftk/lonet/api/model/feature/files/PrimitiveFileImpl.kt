package de.deftk.lonet.api.model.feature.files

import de.deftk.lonet.api.model.User
import de.deftk.lonet.api.model.feature.abstract.IFilePrimitive
import de.deftk.lonet.api.request.MemberApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import java.io.Serializable

class PrimitiveFileImpl(protected var folderId: String, private val responsibleHost: String?, private val login: String) : IFilePrimitive, Serializable {

    override fun getFileStorageFiles(user: User, overwriteCache: Boolean): List<OnlineFile> {
        check(responsibleHost != null) { "Can't do API calls for member $login" }
        val request = MemberApiRequest(responsibleHost, login)
        val id = request.addGetFileStorageFilesRequest(folderId, recursive = false, getFiles = true, getFolders = true)[1]
        val response = request.fireRequest(user, overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("entries")?.asJsonArray?.map { OnlineFile.fromJson(it.asJsonObject, user.findMember(login)!!) } ?: emptyList()
    }

    override fun createFolder(name: String, description: String?, user: User): OnlineFile {
        check(responsibleHost != null) { "Can't do API calls for member $login" }
        val request = MemberApiRequest(responsibleHost, login)
        val id = request.addAddFolderRequest(folderId, name, description)[1]
        val response = request.fireRequest(user, true)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return OnlineFile.fromJson(subResponse.get("folder").asJsonObject, user.findMember(login)!!)
    }

}