package de.deftk.lonet.api.cache

import com.google.gson.JsonObject

interface ICacheController {

    fun isCached(request: JsonObject): Boolean
    fun getCachedResponse(request: JsonObject): JsonObject
    fun getCachedResponseOrNull(request: JsonObject): JsonObject?
    fun cacheResponse(request: JsonObject, response: JsonObject)
}