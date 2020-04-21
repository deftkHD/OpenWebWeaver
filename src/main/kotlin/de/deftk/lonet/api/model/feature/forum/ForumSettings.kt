package de.deftk.lonet.api.model.feature.forum

import com.google.gson.JsonObject
import java.io.Serializable

class ForumSettings(json: JsonObject): Serializable {

    val createThreads = json.get("create_threads").asString
    val alternateView = json.get("alternate_view").asInt

}