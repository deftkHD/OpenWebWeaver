package de.deftk.lonet.api.model.feature.files

import com.google.gson.JsonObject
import java.io.Serializable

class FileDownload(json: JsonObject): Serializable {

    val name = json.get("name").asString
    val size = json.get("size").asLong
    val downloadUrl = json.get("download_url").asString

}