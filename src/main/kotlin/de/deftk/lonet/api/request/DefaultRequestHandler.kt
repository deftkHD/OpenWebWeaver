package de.deftk.lonet.api.request

import de.deftk.lonet.api.LoNet
import de.deftk.lonet.api.response.ApiResponse

class DefaultRequestHandler: IRequestHandler {

    override fun performRequest(request: ApiRequest): ApiResponse {
        return LoNet.performJsonApiRequest(request)
    }
}