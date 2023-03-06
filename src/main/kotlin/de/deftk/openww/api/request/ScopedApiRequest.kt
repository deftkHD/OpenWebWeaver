package de.deftk.openww.api.request

import de.deftk.openww.api.WebWeaverClient
import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.response.ApiResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

open class ScopedApiRequest(protected val context: IRequestContext): ApiRequest() {

    suspend fun fireRequest(): ApiResponse {
        return fireRequest(context)
    }

    override fun packRequestsIntoBundle(context: IRequestContext): List<List<JsonObject>> {
        //FIXME also consider scope when splitting
        val splitRequests = mutableListOf<MutableList<JsonObject>>(mutableListOf())
        val length = addSetSessionRequest(splitRequests.last(), 998, context.sessionId)
        var currentSize = length
        requests.forEach { request ->
            val requestSize = WebWeaverClient.json.encodeToString(request).length
            if (currentSize + requestSize > context.postMaxSize) {
                splitRequests.add(mutableListOf())
                val length = addSetSessionRequest(splitRequests.last(), 998, context.sessionId)
                currentSize = length
            }
            currentSize += requestSize
            splitRequests.last().add(request)
        }
        return splitRequests
    }

    private fun addSetSessionRequest(to: MutableList<JsonObject>, id: Int, sessionId: String): Int {
        val params = buildJsonObject {
            put("session_id", sessionId)
        }
        val obj = buildJsonObject {
            put("jsonrpc", "2.0")
            put("method", "set_session")
            put("id", id)
            put("params", params)
        }
        to.add(obj)
        return WebWeaverClient.json.encodeToString(obj).length
    }

}