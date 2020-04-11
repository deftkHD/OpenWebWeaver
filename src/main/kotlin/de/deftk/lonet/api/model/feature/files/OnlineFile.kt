package de.deftk.lonet.api.model.feature.files

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Member
import java.text.DecimalFormat
import java.util.*
import kotlin.math.log10
import kotlin.math.pow


class OnlineFile(jsonObject: JsonObject, responsibleHost: String?, login: String) :
    FileProvider(jsonObject.get("id").asString, responsibleHost, login) {

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

    fun getTmpDownloadUrl(sessionId: String) {
        //if (downloadUrl != null)
        TODO("not implemented yet")
    }

    fun delete(sessionId: String) {
        TODO("not implemented yet")
    }

    fun getFormattedSize(): String {
        if (type == FileType.FOLDER)
            return ""
        val units = arrayOf("B", "kB", "MB", "GB", "T")
        val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(size / 1024.0.pow(digitGroups.toDouble())) + " " + units[digitGroups]
    }

    override fun getFiles(sessionId: String): List<OnlineFile> {
        check(type == FileType.FOLDER)
        return super.getFiles(sessionId)
    }

    override fun toString(): String {
        return name
    }

    enum class FileType(val id: String) {
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