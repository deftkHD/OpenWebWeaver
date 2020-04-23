package de.deftk.lonet.api.model.feature.forum

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Group
import de.deftk.lonet.api.model.abstract.IManageable
import java.io.Serializable
import java.util.*

class ForumPost(val id: String, val parentId: String, val title: String, val text: String, val icon: ForumMessageIcon, val level: Int, val children: Int, val creationDate: Date, val creationMember: IManageable, val modificationDate: Date, val modificationMember: IManageable, val pinned: Boolean, val locked: Boolean, val member: Group) : Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject, group: Group): ForumPost {
            val creationObject = jsonObject.get("created").asJsonObject
            val modificationObject = jsonObject.get("modified").asJsonObject
            return ForumPost(
                    jsonObject.get("id").asString,
                    jsonObject.get("parent_id").asString,
                    jsonObject.get("title").asString,
                    jsonObject.get("text").asString,
                    ForumMessageIcon.getById(jsonObject.get("icon").asInt),
                    jsonObject.get("level").asInt,
                    jsonObject.get("children").asJsonObject.get("count").asInt,
                    //val files = json.get("files").asJsonArray //TODO do
                    Date(creationObject.get("date").asInt * 1000L),
                    group.getContext().getOrCreateManageable(creationObject.get("user").asJsonObject),
                    Date(modificationObject.get("date").asInt * 1000L),
                    group.getContext().getOrCreateManageable(modificationObject.get("user").asJsonObject),
                    jsonObject.get("pinned")?.asInt == 1,
                    jsonObject.get("locked")?.asInt == 1,
                    group
            )
        }
    }

    enum class ForumMessageIcon(val id: Int) : Serializable {
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