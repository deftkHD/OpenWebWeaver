package de.deftk.lonet.api.model.feature.forum

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Group
import de.deftk.lonet.api.model.abstract.IManageable
import de.deftk.lonet.api.request.GroupApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import java.io.Serializable
import java.util.*

class ForumPost(val id: String, val parentId: String, val title: String, val text: String, val icon: ForumPostIcon, val level: Int, val commentCount: Int, val creationDate: Date, val creationMember: IManageable, val modificationDate: Date, val modificationMember: IManageable, val pinned: Boolean, val locked: Boolean, val group: Group) : Serializable {

    val comments = mutableListOf<ForumPost>()

    companion object {
        fun fromJson(jsonObject: JsonObject, group: Group): ForumPost {
            val creationObject = jsonObject.get("created").asJsonObject
            val modificationObject = jsonObject.get("modified").asJsonObject
            return ForumPost(
                    jsonObject.get("id").asString,
                    jsonObject.get("parent_id").asString,
                    jsonObject.get("title").asString,
                    jsonObject.get("text").asString,
                    ForumPostIcon.getById(jsonObject.get("icon").asInt),
                    jsonObject.get("level").asInt,
                    jsonObject.get("children").asJsonObject.get("count").asInt,
                    //jsonObject.get("files").asJsonArray //seems to be unused (always empty)
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

    fun addComment(title: String, text: String, icon: ForumPostIcon, importSessionFile: String? = null, importSessionFiles: Array<String>? = null, replyNotificationMe: Boolean? = null): ForumPost {
        return group.addForumPost(title, text, icon, id, importSessionFile, importSessionFiles, replyNotificationMe)
    }

    fun delete() {
        val request = GroupApiRequest(group)
        request.addDeleteForumPostRequest(id)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

}