package de.deftk.lonet.api.model.feature.forum

import com.google.gson.JsonObject
import java.io.Serializable

data class ForumSettings(val createThreads: String, val alternateView: Int) : Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject): ForumSettings {
            return ForumSettings(
                    jsonObject.get("create_threads").asString,
                    jsonObject.get("alternate_view").asInt
            )
        }
    }

}