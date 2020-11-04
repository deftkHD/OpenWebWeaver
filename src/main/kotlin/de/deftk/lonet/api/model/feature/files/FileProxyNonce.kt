package de.deftk.lonet.api.model.feature.files

import com.google.gson.JsonObject
import java.io.Serializable

data class FileProxyNonce(val name: String, val size: Long, val nonce: String) : Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject): FileProxyNonce {
            return FileProxyNonce(
                    jsonObject.get("name").asString,
                    jsonObject.get("size").asLong,
                    jsonObject.get("nonce").asString
            )
        }
    }

}