package de.deftk.lonet.api.model.feature.forum

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Member
import java.util.*

class ForumPost(json: JsonObject) {

    val id = json.get("id").asString
    val parentId = json.get("parent_id").asString
    val title = json.get("title").asString
    val text = json.get("text").asString
    val icon = ForumMessageIcon.getById(json.get("icon").asInt)
    val level = json.get("level").asInt
    val children = json.get("children").asJsonObject //TODO do
    val files = json.get("files").asJsonArray //TODO do
    val creationDate: Date
    val creationMember: Member
    val modificationDate: Date
    val modificationMember: Member
    val pinned = json.get("pinned").asInt == 1
    val locked = json.get("locked").asInt == 1

    init {
        val creationObject = json.get("created").asJsonObject
        creationDate = Date(creationObject.get("date").asInt * 1000L)
        creationMember = Member(creationObject.get("user").asJsonObject, null)
        val modificationObject = json.get("modified").asJsonObject
        modificationDate = Date(modificationObject.get("date").asInt * 1000L)
        modificationMember = Member(modificationObject.get("user").asJsonObject, null)
    }

    enum class ForumMessageIcon(val id: Int) {
        INFORMATION(0),
        UNKNOWN_1(1),
        UNKNOWN_2(2),
        UNKNOWN_3(3),
        UNKNOWN_4(4),
        UNKNOWN_5(5),
        UNKNOWN(-1);

        companion object {
            fun getById(id: Int): ForumMessageIcon {
                return values().firstOrNull { it.id == id } ?: UNKNOWN
            }
        }

    }

}