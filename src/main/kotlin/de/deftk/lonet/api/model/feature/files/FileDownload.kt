package de.deftk.lonet.api.model.feature.files

import com.google.gson.JsonObject
import java.io.Serializable

data class FileDownload(val name: String, val size: Long, val downloadUrl: String) : Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject): FileDownload {
            return FileDownload(
                    jsonObject.get("name").asString,
                    jsonObject.get("size").asLong,
                    jsonObject.get("download_url").asString
            )
        }
    }

}