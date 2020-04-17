package de.deftk.lonet.api.request

import de.deftk.lonet.api.response.ApiResponse

interface IRequestHandler {

    fun performRequest(request: ApiRequest): ApiResponse

}