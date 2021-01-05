package de.deftk.lonet.api.factory

import de.deftk.lonet.api.model.IApiContext
import de.deftk.lonet.api.response.ApiResponse

interface IApiContextFactory {

    fun createApiContext(response: ApiResponse, requestUrl: String): IApiContext

}