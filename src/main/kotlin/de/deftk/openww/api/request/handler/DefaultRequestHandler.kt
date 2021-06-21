package de.deftk.openww.api.request.handler

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.request.ApiRequest
import de.deftk.openww.api.response.ApiResponse

class DefaultRequestHandler: AbstractRequestHandler() {

    override fun performRequest(request: ApiRequest, context: IRequestContext): ApiResponse {
        return performApiRequestIntern(request, context)
    }
}