package de.deftk.lonet.api.model.feature

import com.google.gson.JsonObject
import java.io.Serializable

class Quota(jsonObject: JsonObject): Serializable {

    val usage = jsonObject.get("usage").asLong
    val free = jsonObject.get("free").asLong
    val limit = jsonObject.get("limit").asLong
    val sequence = jsonObject.get("sequence").asInt
    val updated = jsonObject.get("updated").asLong
    val version = jsonObject.get("version").asInt

}