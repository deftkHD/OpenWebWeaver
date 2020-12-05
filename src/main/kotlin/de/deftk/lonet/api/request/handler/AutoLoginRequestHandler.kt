package de.deftk.lonet.api.request.handler

import de.deftk.lonet.api.Credentials
import de.deftk.lonet.api.LoNet
import de.deftk.lonet.api.exception.ApiException
import de.deftk.lonet.api.model.User
import de.deftk.lonet.api.model.abstract.IContext
import de.deftk.lonet.api.request.ApiRequest
import de.deftk.lonet.api.response.ApiResponse
import de.deftk.lonet.api.response.ResponseUtil

class AutoLoginRequestHandler(private val handler: LoginHandler) : AbstractRequestHandler() {

    override fun performRequest(request: ApiRequest, context: IContext): ApiResponse {
        return try {
            val response = performJsonApiRequestIntern(request, context)
            ResponseUtil.checkSuccess(response.toJson())
            response
        } catch (e: ApiException) {
            if (e.message?.contains("Session key not valid") == true) {
                val user = LoNet.login(handler.getCredentials())

                // replace old session id
                request.requests.forEach { methodList ->
                    val targetMethods = methodList.filter { it.asJsonObject.get("method")?.asString == "set_session" }
                    targetMethods.forEach { setSessionMethod ->
                        val params = setSessionMethod.asJsonObject.get("params").asJsonObject
                        params.remove("session_id")
                        params.addProperty("session_id", user.getContext().getSessionId())
                    }
                }

                performJsonApiRequestIntern(request, user.getContext())
            } else throw e
        }
    }

    interface LoginHandler {
        fun getCredentials(): Credentials
        fun onLogin(user: User)
    }

}