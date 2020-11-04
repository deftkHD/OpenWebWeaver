package de.deftk.lonet.api.model.feature.files

import com.google.gson.JsonObject
import java.io.Serializable

data class FileChunk(val name: String, val size: Long, val encoding: String, val data: String) : Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject): FileChunk {
            return FileChunk(
                    jsonObject.get("name").asString,
                    jsonObject.get("size").asLong,
                    jsonObject.get("data_encoding").asString,
                    jsonObject.get("data").asString
            )
        }
    }

}