package de.deftk.lonet.api.model.feature.courselets

import com.google.gson.JsonObject
import java.io.Serializable

data class CourseletDownload(val id: String, val name: String, val size: Long, val downloadUrl: String) : Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject): CourseletDownload {
            return CourseletDownload(
                    jsonObject.get("id").asString,
                    jsonObject.get("name").asString,
                    jsonObject.get("size").asLong,
                    jsonObject.get("download_url").asString
            )
        }
    }

}