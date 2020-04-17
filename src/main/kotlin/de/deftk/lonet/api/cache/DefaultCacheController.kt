package de.deftk.lonet.api.cache

import de.deftk.lonet.api.request.ApiRequest
import de.deftk.lonet.api.response.ApiResponse

class DefaultCacheController: ICacheController {

    private val requestCache = mutableMapOf<ApiRequest, ApiResponse>()

    override fun isCached(request: ApiRequest): Boolean {
        return requestCache.containsKey(request)
    }

    override fun getCachedResponse(request: ApiRequest): ApiResponse {
        return requestCache[request]!!
    }

    override fun getCachedResponseOrNull(request: ApiRequest): ApiResponse? {
        return requestCache[request]
    }

    override fun cacheResponse(request: ApiRequest, response: ApiResponse) {
        requestCache[request] = response
    }
}