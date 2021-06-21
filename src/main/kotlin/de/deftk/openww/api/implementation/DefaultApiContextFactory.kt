package de.deftk.openww.api.implementation

import de.deftk.openww.api.factory.IApiContextFactory
import de.deftk.openww.api.request.handler.DefaultRequestHandler
import de.deftk.openww.api.response.ApiResponse
import de.deftk.openww.api.response.ResponseUtil
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonPrimitive

class DefaultApiContextFactory : IApiContextFactory {

    override fun createApiContext(response: ApiResponse, requestUrl: String): ApiContext {
        val json = response.toJson()
        val loginResult = ResponseUtil.getSubResponseResultByMethod(json, "login")
        val informationResult = ResponseUtil.getSubResponseResultByMethod(json, "get_information")

        val user = Json { ignoreUnknownKeys = true }.decodeFromJsonElement<User>(loginResult)

        return ApiContext(
            informationResult["session_id"]!!.jsonPrimitive.content,
            user,
            user.getGroups(),
            requestUrl,
            DefaultRequestHandler()
        )
    }
}