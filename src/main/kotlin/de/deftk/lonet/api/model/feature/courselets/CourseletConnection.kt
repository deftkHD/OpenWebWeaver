package de.deftk.lonet.api.model.feature.courselets

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Group
import java.util.*

class CourseletConnection(val exists: Boolean, val creationDate: Date?, val modificationDate: Date?, val progressed: JsonObject?, val lastPageId: String?, val result: JsonObject?, val suspendData: String?, val isBookmarked: Boolean?, val isCorrected: Boolean?, val isConversation: Boolean?, val isUnread: Boolean?, val pages: List<CourseletPage>?, val courselet: Courselet, val group: Group) {

    companion object {
        fun fromJson(jsonObject: JsonObject, group: Group, courselet: Courselet): CourseletConnection {
            val createdObject = jsonObject.get("created").asJsonObject
            val modifiedObject = jsonObject.get("modified").asJsonObject
            if (jsonObject.get("exists").asBoolean) {
                return CourseletConnection(
                        true,
                        Date(createdObject.get("date").asInt * 1000L),
                        Date(modifiedObject.get("date").asInt * 1000L),
                        jsonObject.get("progressed").asJsonObject,
                        jsonObject.get("last_page_id").asString,
                        jsonObject.get("result").asJsonObject,
                        if (jsonObject.get("suspend_data").isJsonNull) null else jsonObject.get("suspend_data").asString,
                        jsonObject.get("is_bookmarked").asBoolean,
                        jsonObject.get("is_corrected").asBoolean,
                        jsonObject.get("is_conversation").asBoolean,
                        jsonObject.get("is_unread").asInt == 1,
                        jsonObject.get("pages").asJsonArray.map { CourseletPage.fromJson(it.asJsonObject, group, courselet) },
                        courselet,
                        group
                )
            } else {
                return CourseletConnection(
                        false,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        courselet,
                        group
                )
            }

        }
    }

}