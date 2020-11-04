package de.deftk.lonet.api.model.feature.files

import com.google.gson.JsonObject
import java.io.Serializable

data class FileStorageSettings(val hideOldVersions: Boolean) : Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject): FileStorageSettings {
            return FileStorageSettings(jsonObject.get("hide_old_versions").asInt == 1)
        }
    }
}