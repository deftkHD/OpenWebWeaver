package de.deftk.lonet.api.request.handler

import de.deftk.lonet.api.LoNet
import de.deftk.lonet.api.request.ApiRequest
import de.deftk.lonet.api.response.ApiResponse

class SimpleRequestHandler : IRequestHandler {

    override fun performRequest(request: ApiRequest, allowCachedResponse: Boolean): ApiResponse {
        return LoNet.performJsonApiRequestIntern(request)
    }
}