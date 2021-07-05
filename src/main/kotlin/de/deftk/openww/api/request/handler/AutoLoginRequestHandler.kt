package de.deftk.openww.api.request.handler

import de.deftk.openww.api.WebWeaverClient
import de.deftk.openww.api.auth.Credentials
import de.deftk.openww.api.exception.ApiException
import de.deftk.openww.api.model.IApiContext
import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.request.ApiRequest
import de.deftk.openww.api.response.ApiResponse
import de.deftk.openww.api.response.ResponseUtil
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class AutoLoginRequestHandler<T : IApiContext>(private val handler: LoginHandler<T>, private val contextClass: Class<T>) : AbstractRequestHandler() {

    override suspend fun performRequest(request: ApiRequest, context: IRequestContext): ApiResponse {
        return try {
            val response = performApiRequestIntern(request, context)
            ResponseUtil.checkSuccess(response.toJson())
            response
        } catch (e: ApiException) {
            if (e.message?.contains("Session key not valid") == true) {
                val apiContext = WebWeaverClient.login(handler.getCredentials(), contextClass)
                handler.onLogin(apiContext)

                // replace old session id
                request.requests.forEach { requestContent ->
                    requestContent.withIndex().forEach { (index, method) ->
                        if (method.jsonObject["method"]?.jsonPrimitive?.content == "set_session") {
                            val methodEntries = method.entries.map { Pair(it.key, it.value) }.toMap().toMutableMap()
                            val params = methodEntries["params"]!!.jsonObject
                            val paramEntries = params.entries.map { Pair(it.key, it.value) }.toMap().toMutableMap()
                            paramEntries.remove("session_id")
                            paramEntries["session_id"] = JsonPrimitive(apiContext.sessionId)
                            methodEntries["params"] = JsonObject(paramEntries)
                            requestContent[index] = JsonObject(methodEntries)
                        }
                    }
                }
                context.sessionId = apiContext.sessionId
                performApiRequestIntern(request, context)
            } else throw e
        }
    }

    interface LoginHandler<T : IApiContext> {
        suspend fun getCredentials(): Credentials
        suspend fun onLogin(context: T)
    }

}