package de.deftk.lonet.api.model.feature.courselets

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Group
import de.deftk.lonet.api.utils.getApiDate
import de.deftk.lonet.api.utils.getBoolOrNull
import de.deftk.lonet.api.utils.getStringOrNull
import java.util.*

class CourseletConnection(val exists: Boolean, val creationDate: Date?, val modificationDate: Date?, val progressed: JsonObject?, val lastPageId: String?, val result: JsonObject?, val suspendData: String?, val isBookmarked: Boolean?, val isCorrected: Boolean?, val isConversation: Boolean?, val isUnread: Boolean?, val pages: List<CourseletPage>?, val courselet: Courselet, val group: Group) {

    companion object {
        fun fromJson(jsonObject: JsonObject, group: Group, courselet: Courselet): CourseletConnection {
            val createdObject = jsonObject.get("created").asJsonObject
            val modifiedObject = jsonObject.get("modified").asJsonObject
            if (jsonObject.get("exists").asBoolean) {
                return CourseletConnection(
                        true,
                        createdObject.getApiDate("date"),
                        modifiedObject.getApiDate("date"),
                        jsonObject.get("progressed").asJsonObject,
                        jsonObject.get("last_page_id").asString,
                        jsonObject.get("result").asJsonObject,
                        jsonObject.getStringOrNull("suspend_data"),
                        jsonObject.getBoolOrNull("is_bookmarked"),
                        jsonObject.getBoolOrNull("is_corrected"),
                        jsonObject.getBoolOrNull("is_conversation"),
                        jsonObject.getBoolOrNull("is_unread"),
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