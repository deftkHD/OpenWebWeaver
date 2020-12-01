package de.deftk.lonet.api.request

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import de.deftk.lonet.api.LoNet
import de.deftk.lonet.api.model.abstract.IContext
import de.deftk.lonet.api.response.ApiResponse
import java.io.Serializable

open class ApiRequest(): Serializable {

    companion object {
        const val SUB_REQUESTS_PER_REQUEST = 30
    }

    var requests = mutableListOf(JsonArray())

    private fun addRequest(requestName: String, id: Int, request: JsonObject): Int {
        //TODO try to avoid double setFocus, setSession, ... requests
        val obj = JsonObject()
        obj.addProperty("jsonrpc", "2.0")
        obj.addProperty("method", requestName)
        obj.addProperty("id", id)
        obj.add("params", request)
        currentRequest().add(obj)
        return (requests.size - 1) * (SUB_REQUESTS_PER_REQUEST + 1) + id
    }

    fun addRequest(requestName: String, request: JsonObject?): Int {
        return addRequest(requestName, currentRequest().size() + 2 /* place holder for setSession request */, request ?: JsonObject())
    }

    private fun addSetSessionRequest(sessionId: String): Int {
        val obj = JsonObject()
        obj.addProperty("session_id", sessionId)
        return addRequest("set_session", currentRequest().size() + 1, obj)
    }

    fun addSetFocusRequest(scope: String?, login: String?): Int {
        val obj = JsonObject()
        if (scope != null)
            obj.addProperty("object", scope)
        if (login != null)
            obj.addProperty("login", login)
        return addRequest("set_focus", obj)
    }

    fun addIdRequest(id: String, requestName: String): Int {
        val obj = JsonObject()
        obj.addProperty("id", id)
        return addRequest(requestName, obj)
    }

    fun addGetInformationRequest(): Int {
        return addRequest("get_information", 999, JsonObject())
    }

    open fun fireRequest(context: IContext): ApiResponse {
        if (context !is AuthRequest.AuthContext) {
            val requests = this.requests
            this.requests = mutableListOf()
            requests.forEach { request ->
                this.requests.add(JsonArray())
                addSetSessionRequest(context.getSessionId())
                currentRequest().addAll(request)
            }
        }
        return LoNet.requestHandler.performRequest(this, context)
    }

    protected fun asApiBoolean(boolean: Boolean): Int {
        return if (boolean) 1 else 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ApiRequest

        if (requests != other.requests) return false

        return true
    }

    private fun currentRequest(): JsonArray  {
        return requests.last()
    }

    protected fun ensureCapacity(size: Int) {
        if (currentRequest().size() + size > SUB_REQUESTS_PER_REQUEST) {
            requests.add(JsonArray())
        }
    }

    override fun hashCode(): Int {
        return requests.hashCode()
    }


}