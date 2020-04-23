package de.deftk.lonet.api.request.handler

import de.deftk.lonet.api.LoNet
import de.deftk.lonet.api.model.abstract.IContext
import de.deftk.lonet.api.request.ApiRequest
import de.deftk.lonet.api.response.ApiResponse

class CachedRequestHandler: IRequestHandler {

    override fun performRequest(apiRequest: ApiRequest, context: IContext, allowCachedResponse: Boolean): ApiResponse {
        if (allowCachedResponse) {
            //TODO caching
        }

        return LoNet.performJsonApiRequestIntern(apiRequest, context)
    }
}