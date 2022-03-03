package de.deftk.openww.api.factory

import de.deftk.openww.api.model.IApiContext
import de.deftk.openww.api.response.ApiResponse

/**
 * Implement this interface und register it inside the WebWeaverClient to provide
 * your own implementation of the API
 */
interface IApiContextFactory {

    fun createApiContext(response: ApiResponse, requestUrl: String): IApiContext

}