package de.deftk.lonet.api.request.handler

import de.deftk.lonet.api.LoNetClient
import de.deftk.lonet.api.auth.Credentials
import de.deftk.lonet.api.exception.ApiException
import de.deftk.lonet.api.model.IApiContext
import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.request.ApiRequest
import de.deftk.lonet.api.response.ApiResponse
import de.deftk.lonet.api.response.ResponseUtil
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class AutoLoginRequestHandler<T : IApiContext>(private val handler: LoginHandler<T>, private val contextClass: Class<T>) : AbstractRequestHandler() {

    override fun performRequest(request: ApiRequest, context: IRequestContext): ApiResponse {
        return try {
            val response = performApiRequestIntern(request, context)
            ResponseUtil.checkSuccess(response.toJson())
            response
        } catch (e: ApiException) {
            if (e.message?.contains("Session key not valid") == true) {
                val apiContext = LoNetClient.login(handler.getCredentials(), contextClass)
                handler.onLogin(apiContext)

                // replace old session id
                request.requests.forEach { requestContent ->
                    requestContent.withIndex().forEach { (index, method) ->
                        if (method.jsonObject["method"]?.jsonPrimitive?.content == "set_session") {
                            val methodEntries = method.entries.map { Pair(it.key, it.value) }.toMap().toMutableMap()
                            val params = methodEntries["params"]!!.jsonObject
                            val paramEntries = params.entries.map { Pair(it.key, it.value) }.toMap().toMutableMap()
                            paramEntries.remove("session_id")
                            paramEntries["session_id"] = JsonPrimitive(apiContext.getSessionId())
                            methodEntries["params"] = JsonObject(paramEntries)
                            requestContent[index] = JsonObject(methodEntries)
                        }
                    }
                }
                context.sessionId = apiContext.getSessionId()
                performApiRequestIntern(request, context)
            } else throw e
        }
    }

    interface LoginHandler<T : IApiContext> {
        fun getCredentials(): Credentials
        fun onLogin(context: T)
    }

}