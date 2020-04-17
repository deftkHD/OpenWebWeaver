package de.deftk.lonet.api.request.handler

import de.deftk.lonet.api.LoNet
import de.deftk.lonet.api.request.ApiRequest
import de.deftk.lonet.api.response.ApiResponse

class DefaultRequestHandler: IRequestHandler {

    override fun performRequest(request: ApiRequest, allowCaching: Boolean): ApiResponse {
        if (allowCaching && LoNet.cacheController != null) {
            return if (LoNet.cacheController!!.isCached(request)) {
                LoNet.cacheController!!.getCachedResponse(request)
            } else {
                val response = LoNet.performJsonApiRequest(request)
                LoNet.cacheController!!.cacheResponse(request, response)
                response
            }
        }
        return LoNet.performJsonApiRequest(request)
    }
}