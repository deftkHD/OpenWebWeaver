package de.deftk.lonet.api.model.feature.files

import com.google.gson.JsonObject
import java.io.Serializable

data class FileDownloadUrl(val name: String, val size: Long, val downloadUrl: String) : Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject): FileDownloadUrl {
            return FileDownloadUrl(
                    jsonObject.get("name").asString,
                    jsonObject.get("size").asLong,
                    jsonObject.get("download_url").asString
            )
        }

        fun fromPreviewJson(jsonObject: JsonObject): FileDownloadUrl {
            return FileDownloadUrl(
                    jsonObject.get("name").asString,
                    jsonObject.get("size").asLong,
                    jsonObject.get("preview_download_url").asString
            )
        }
    }

}