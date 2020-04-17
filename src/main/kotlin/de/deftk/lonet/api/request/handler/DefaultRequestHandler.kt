package de.deftk.lonet.api.request.handler

import de.deftk.lonet.api.LoNet
import de.deftk.lonet.api.request.ApiRequest
import de.deftk.lonet.api.response.ApiResponse

class DefaultRequestHandler : IRequestHandler {

    override fun performRequest(request: ApiRequest, allowCachedResponse: Boolean): ApiResponse {
        if (allowCachedResponse && LoNet.cacheController != null && LoNet.cacheController!!.isCached(request)) {
            return LoNet.cacheController!!.getCachedResponse(request)
        }
        val response = LoNet.performJsonApiRequest(request)
        LoNet.cacheController?.cacheResponse(request, response)
        return response
    }
}