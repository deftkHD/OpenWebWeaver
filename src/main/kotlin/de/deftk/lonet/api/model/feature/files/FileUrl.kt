package de.deftk.lonet.api.model.feature.files

import com.google.gson.JsonObject
import java.io.Serializable

data class FileUrl(val name: String, val size: Long, val url: String) : Serializable {

    companion object {
        fun fromDownloadJson(jsonObject: JsonObject): FileUrl {
            return FileUrl(
                    jsonObject.get("name").asString,
                    jsonObject.get("size").asLong,
                    jsonObject.get("download_url").asString
            )
        }

        fun fromPreviewJson(jsonObject: JsonObject): FileUrl {
            return FileUrl(
                    jsonObject.get("name").asString,
                    jsonObject.get("size").asLong,
                    jsonObject.get("preview_download_url").asString
            )
        }

        fun fromUploadJson(jsonObject: JsonObject): FileUrl {
            return FileUrl(
                    jsonObject.get("name").asString,
                    -1,
                    jsonObject.get("upload_url").asString
            )
        }
    }

}