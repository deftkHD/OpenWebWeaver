package de.deftk.lonet.api.request.handler

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.request.ApiRequest
import de.deftk.lonet.api.response.ApiResponse

class DefaultRequestHandler: AbstractRequestHandler() {

    override fun performRequest(request: ApiRequest, context: IRequestContext): ApiResponse {
        return performApiRequestIntern(request, context)
    }
}