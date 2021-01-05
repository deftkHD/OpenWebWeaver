package de.deftk.lonet.api.request

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.response.ApiResponse
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

open class ScopedApiRequest(protected val context: IRequestContext): ApiRequest() {

    fun fireRequest(): ApiResponse {
        return fireRequest(context)
    }

    override fun fireRequest(context: IRequestContext): ApiResponse {
        val requests = this.requests
        this.requests = mutableListOf()
        requests.forEach { request ->
            this.requests.add(mutableListOf())
            addSetSessionRequest(context.sessionId)
            currentRequest().addAll(request)
        }
        return super.fireRequest(context)
    }

    private fun addSetSessionRequest(sessionId: String): Int {
        val params = buildJsonObject {
            put("session_id", sessionId)
        }
        return addRequest("set_session", currentRequest().size + 1, params)
    }

}