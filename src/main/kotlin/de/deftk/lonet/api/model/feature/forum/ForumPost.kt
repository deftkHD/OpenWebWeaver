package de.deftk.lonet.api.model.feature.forum

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Member
import java.io.Serializable
import java.util.*

class ForumPost(json: JsonObject): Serializable {

    val id = json.get("id").asString
    val parentId = json.get("parent_id").asString
    val title = json.get("title").asString
    val text = json.get("text").asString
    val icon = ForumMessageIcon.getById(json.get("icon").asInt)
    val level = json.get("level").asInt
    val children = json.get("children").asJsonObject.get("count").asInt
    //val files = json.get("files").asJsonArray //TODO do
    val creationDate: Date
    val creationMember: Member
    val modificationDate: Date
    val modificationMember: Member
    val pinned = json.get("pinned")?.asInt == 1
    val locked = json.get("locked")?.asInt == 1

    init {
        val creationObject = json.get("created").asJsonObject
        creationDate = Date(creationObject.get("date").asInt * 1000L)
        creationMember = Member(creationObject.get("user").asJsonObject, null)
        val modificationObject = json.get("modified").asJsonObject
        modificationDate = Date(modificationObject.get("date").asInt * 1000L)
        modificationMember = Member(modificationObject.get("user").asJsonObject, null)
    }

    enum class ForumMessageIcon(val id: Int): Serializable {
        INFORMATION(0),
        HUMOR(1),
        QUESTION(2),
        ANSWER(3),
        UP_VOTE(4),
        DOWN_VOTE(5),
        UNKNOWN(-1);

        companion object {
            fun getById(id: Int): ForumMessageIcon {
                return values().firstOrNull { it.id == id } ?: UNKNOWN
            }
        }

    }

}