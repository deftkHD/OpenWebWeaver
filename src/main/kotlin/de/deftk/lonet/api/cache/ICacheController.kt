package de.deftk.lonet.api.cache

import de.deftk.lonet.api.request.ApiRequest
import de.deftk.lonet.api.response.ApiResponse

interface ICacheController {

    fun isCached(request: ApiRequest): Boolean
    fun getCachedResponse(request: ApiRequest): ApiResponse
    fun getCachedResponseOrNull(request: ApiRequest): ApiResponse?
    fun cacheResponse(request: ApiRequest, response: ApiResponse)
}