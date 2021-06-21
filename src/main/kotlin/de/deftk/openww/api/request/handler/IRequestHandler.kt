package de.deftk.openww.api.request.handler

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.request.ApiRequest
import de.deftk.openww.api.response.ApiResponse

interface IRequestHandler {

    fun performRequest(request: ApiRequest, context: IRequestContext): ApiResponse

}