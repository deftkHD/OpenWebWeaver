package de.deftk.lonet.api.model.feature.files

import com.google.gson.JsonObject
import java.io.Serializable

class FileStorageSettings(json: JsonObject): Serializable {

    val hideOldVersions = json.get("hide_old_versions").asInt == 1

}