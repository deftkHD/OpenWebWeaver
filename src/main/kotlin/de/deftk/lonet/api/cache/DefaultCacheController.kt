package de.deftk.lonet.api.cache

import com.google.gson.JsonObject
import kotlin.random.Random

class DefaultCacheController: ICacheController {

    companion object {
        private const val MAX_CACHE_SIZE = 200
    }

    private val requestCache = mutableMapOf<JsonObject, JsonObject>()

    override fun isCached(request: JsonObject): Boolean {
        return requestCache.containsKey(request)
    }

    override fun getCachedResponse(request: JsonObject): JsonObject {
        return requestCache[request]!!
    }

    override fun getCachedResponseOrNull(request: JsonObject): JsonObject? {
        return requestCache[request]
    }

    override fun cacheResponse(request: JsonObject, response: JsonObject) {
        if (requestCache.size >= MAX_CACHE_SIZE) {
            requestCache.remove(requestCache.keys.toList()[Random.nextInt(requestCache.size)]) //TODO more efficient cache cleaning
        }
        requestCache[request] = response
    }
}