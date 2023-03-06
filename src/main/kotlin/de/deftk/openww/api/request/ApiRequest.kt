package de.deftk.openww.api.request

import de.deftk.openww.api.WebWeaverClient
import de.deftk.openww.api.model.IOperatingScope
import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.response.ApiResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

open class ApiRequest {

    private var currentFocusable: Focusable? = null
    private var currentScope: String? = null

    /**
     * null: uncertain
     * true: request was chunked into multiple subrequests
     * false: request was sent as a whole to the server
     */
    var isChunked: Boolean? = null
    var requests = mutableListOf<JsonObject>()

    suspend fun fireRequest(context: IRequestContext): ApiResponse {
        return context.requestHandler.performRequest(this, context)
    }

    open fun packRequestsIntoBundle(context: IRequestContext): List<List<JsonObject>> {
        //FIXME also consider scope when splitting
        val splitRequests = mutableListOf<MutableList<JsonObject>>(mutableListOf())
        var currentSize = 0
        requests.forEach { request ->
            val requestSize = WebWeaverClient.json.encodeToString(request).length
            if (currentSize + requestSize > context.postMaxSize) {
                splitRequests.add(mutableListOf())
                currentSize = 0
            }
            currentSize += requestSize
            splitRequests.last().add(request)
        }
        isChunked = splitRequests.size > 1
        return splitRequests
    }

    private fun addSetFocusRequest(focusable: Focusable, scope: IOperatingScope?): Int {
        return addSetFocusRequest(focusable, scope?.login)
    }

    private fun addSetFocusRequest(focusable: Focusable, scope: String?): Int {
        val params = buildJsonObject {
            put("object", WebWeaverClient.json.encodeToJsonElement(Focusable.serializer(), focusable))
            currentFocusable = focusable
            currentScope = scope
            if (scope != null) {
                put("login", scope)
            }
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
        return addRequest(method, requests.size + 1, params)
    }

    fun ensureFocus(focusable: Focusable, scope: String?) {
        if (currentFocusable != focusable || currentScope != scope) {
            addSetFocusRequest(focusable, scope)
        }
    }

    protected fun addRequest(method: String, id: Int, params: JsonObject?): Int {
        val obj = buildJsonObject {
            put("jsonrpc", "2.0")
            put("method", method)
            put("id", id)
            put("params", params ?: JsonObject(emptyMap()))
        }
        requests.add(obj)
        return id
    }

    protected fun asApiBoolean(boolean: Boolean?): JsonElement {
        return if (boolean ?: return JsonNull) JsonPrimitive(1) else JsonPrimitive(0)
    }

}