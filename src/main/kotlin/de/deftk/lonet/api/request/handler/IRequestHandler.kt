package de.deftk.lonet.api.request.handler

import de.deftk.lonet.api.model.abstract.IContext
import de.deftk.lonet.api.request.ApiRequest
import de.deftk.lonet.api.response.ApiResponse
import java.io.Serializable

interface IRequestHandler: Serializable {

    fun performRequest(request: ApiRequest, context: IContext): ApiResponse

}