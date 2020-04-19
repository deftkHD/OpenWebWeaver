package de.deftk.lonet.api.model.feature.forum

import com.google.gson.JsonObject

class ForumSettings(json: JsonObject) {

    val createThreads = json.get("create_threads").asString
    val alternateView = json.get("alternate_view").asInt

}