package de.deftk.lonet.api.model.feature

import com.google.gson.JsonObject
import java.io.Serializable

data class Quota(val usage: Long, val free: Long, val limit: Long, val sequence: Int, val updated: Long, val version: Int) : Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject): Quota {
            return Quota(
                    jsonObject.get("usage").asLong,
                    jsonObject.get("free").asLong,
                    jsonObject.get("limit").asLong,
                    jsonObject.get("sequence").asInt,
                    jsonObject.get("updated").asLong,
                    jsonObject.get("version").asInt
            )
        }
    }


}