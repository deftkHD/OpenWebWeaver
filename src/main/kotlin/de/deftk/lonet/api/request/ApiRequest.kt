package de.deftk.lonet.api.request

import de.deftk.lonet.api.LoNetClient
import de.deftk.lonet.api.model.IOperatingScope
import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.response.ApiResponse
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

open class ApiRequest {

    companion object {
        const val METHODS_PER_REQUEST = 30
    }

    var requests = mutableListOf(mutableListOf<JsonObject>())

    open fun fireRequest(context: IRequestContext): ApiResponse {
        return context.requestHandler.performRequest(this, context)
    }

    fun addSetFocusRequest(focusable: Focusable, scope: IOperatingScope?): Int {
        return addSetFocusRequest(focusable, scope?.login)
    }

    fun addSetFocusRequest(focusable: Focusable, scope: String?): Int {
        val params = buildJsonObject {
            put("object", LoNetClient.json.encodeToJsonElement(Focusable.serializer(), focusable))
            if (scope != null)
                put("login", scope)
        }
        return addRequest("set_focus", params)
    }

    fun addIdRequest(method: String, id: String): Int {
        val params = buildJsonObject {
            put("id", id)
        }
        return addRequest(method, params)
    }

    fun addGetInformationRequest(): Int {
        return addRequest("get_information", 999, null)
    }

    fun addRequest(method: String, params: JsonObject?): Int {
        return addRequest(method, currentRequest().size + 2, params)
    }

    protected fun ensureCapacity(size: Int) {
        if (currentRequest().size + size > METHODS_PER_REQUEST)
            requests.add(mutableListOf())
    }

    protected fun addRequest(method: String, id: Int, params: JsonObject?): Int {
        val obj = buildJsonObject {
            put("jsonrpc", "2.0")
            put("method", method)
            put("id", id)
            put("params", params ?: JsonObject(emptyMap()))
        }
        currentRequest().add(obj)
        return (requests.size - 1) * (METHODS_PER_REQUEST + 1) + id
    }

    protected fun currentRequest() = requests.last()

    protected fun asApiBoolean(boolean: Boolean): Int {
        return if (boolean) 1 else 0
    }

}