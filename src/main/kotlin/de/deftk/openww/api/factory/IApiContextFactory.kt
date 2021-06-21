package de.deftk.openww.api.factory

import de.deftk.openww.api.model.IApiContext
import de.deftk.openww.api.response.ApiResponse

interface IApiContextFactory {

    fun createApiContext(response: ApiResponse, requestUrl: String): IApiContext

}