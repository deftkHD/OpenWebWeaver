package de.deftk.openww.api.implementation

import de.deftk.openww.api.factory.IApiContextFactory
import de.deftk.openww.api.request.handler.DefaultRequestHandler
import de.deftk.openww.api.response.ApiResponse
import de.deftk.openww.api.response.ResponseUtil
import kotlinx.serialization.json.*

class DefaultApiContextFactory : IApiContextFactory {

    private val parser = Json { ignoreUnknownKeys = true }

    override fun createApiContext(response: ApiResponse, requestUrl: String): ApiContext {
        val json = response.toJson()
        val loginResult = ResponseUtil.getSubResponseResultByMethod(json, "login")
        val informationResult = ResponseUtil.getSubResponseResultByMethod(json, "get_information")

        val user = parser.decodeFromJsonElement<User>(loginResult)

        return ApiContext(
            informationResult["session_id"]!!.jsonPrimitive.content,
            informationResult["server_version"]!!.jsonPrimitive.content,
            informationResult["client_version"]!!.jsonPrimitive.content,
            informationResult["client_url"]!!.jsonPrimitive.content,
            informationResult["post_max_size"]!!.jsonPrimitive.int,
            informationResult["timezone"]!!.jsonPrimitive.content,
            informationResult["locale"]!!.jsonPrimitive.content,
            informationResult["info"]!!.jsonPrimitive.content,
            informationResult["custom_translations_url"]!!.jsonPrimitive.content,
            informationResult["custom_skinning_url"]!!.jsonPrimitive.content,
            informationResult["admin_types"]!!.jsonArray.toList().map { it.jsonPrimitive.int },
            informationResult["custom_options"]!!.jsonPrimitive.content,
            user,
            user.getGroups(),
            requestUrl,
            DefaultRequestHandler()
        )
    }
}