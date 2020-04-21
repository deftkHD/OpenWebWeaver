package de.deftk.lonet.api.model.feature.files

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Member
import de.deftk.lonet.api.model.User
import java.io.Serializable
import java.text.DecimalFormat
import java.util.*
import kotlin.math.log10
import kotlin.math.pow


class OnlineFile(jsonObject: JsonObject, responsibleHost: String?, login: String) :
    FileProvider(jsonObject.get("id").asString, responsibleHost, login), Serializable {

    val id = jsonObject.get("id").asString
    val parentId = jsonObject.get("parent_id").asString
    val ordinal = jsonObject.get("ordinal").asInt
    val name = jsonObject.get("name").asString
    val description = jsonObject.get("description").asString
    val type = FileType.getById(jsonObject.get("type").asString)
    val size = jsonObject.get("size").asLong
    val readable = jsonObject.get("readable").asInt == 1
    val writable = jsonObject.get("writable").asInt == 1
    val sparse = jsonObject.get("sparse").asInt == 1
    val mine = jsonObject.get("mine").asInt == 1
    val downloadUrl = jsonObject.get("download_url")?.asString
    val creationDate: Date
    val creationMember: Member
    val modificationDate: Date
    val modificationMember: Member

    init {
        val createdObject = jsonObject.get("created").asJsonObject
        creationDate = Date(createdObject.get("date").asLong * 1000)
        creationMember = Member(createdObject.get("user").asJsonObject, null)

        val modifiedObject = jsonObject.get("modified").asJsonObject
        modificationDate = Date(modifiedObject.get("date").asLong * 1000)
        modificationMember = Member(modifiedObject.get("user").asJsonObject, null)
    }

    fun getTmpDownloadUrl(sessionId: String, overwriteCache: Boolean = false) {
        //if (downloadUrl != null)
        TODO("not implemented yet")
    }

    fun delete(sessionId: String, overwriteCache: Boolean = false) {
        TODO("not implemented yet")
    }

    override fun getFileStorageFiles(user: User, overwriteCache: Boolean): List<OnlineFile> {
        check(type == FileType.FOLDER)
        return super.getFileStorageFiles(user, overwriteCache)
    }

    override fun toString(): String {
        return name
    }

    //TODO equals() and hashCode() should consider including login and id, because id is not unique if same file is inside different file storages (whoever thought about this...)

    enum class FileType(val id: String): Serializable {
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