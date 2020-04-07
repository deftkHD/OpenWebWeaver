package de.deftk.lonet.api.request

import com.google.gson.JsonArray
import com.google.gson.JsonObject

open class ApiRequest(val serverUrl: String) {

    val requests = JsonArray()

    fun addRequest(requestName: String, id: Int, request: JsonObject) {
        val obj = JsonObject()
        obj.addProperty("jsonrpc", "2.0")
        obj.addProperty("method", requestName) // Param.METHOD from firebase
        obj.addProperty("id", id)
        obj.add("params", request)
        requests.add(obj)
    }

    fun addRequest(requestName: String, request: JsonObject?) {
        addRequest(requestName, requests.size() + 1, request ?: JsonObject())
    }

    fun addSetSessionRequest(sessionId: String) {
        val obj = JsonObject()
        obj.addProperty("session_id", sessionId)
        addRequest("set_session", obj)
    }

    fun addSetFocusRequest(str: String?) {
        addSetFocusRequest(str, null)
    }

    fun addSetFocusRequest(`object`: String?, login: String?) {
        val obj = JsonObject()
        if (`object` != null) {
            obj.addProperty("object", `object`)
        }
        if (login != null) {
            obj.addProperty("login", login)
        }
        addRequest("set_focus", obj)
    }

    fun addIdRequest(id: String, requestName: String) {
        val obj = JsonObject()
        obj.addProperty("id", id)
        addRequest(requestName, obj)
    }

    fun addExportSessionFileRequest(id: String, str2: String?) {
        val obj = JsonObject()
        obj.addProperty("id", id)
        if (str2?.isNotEmpty() == true) {
            obj.addProperty("", str2) // FontsContractCompat.Column
        }
        addRequest("export_session_file", obj)
    }

    fun addGetInformationRequest() {
        addRequest("get_information", 999, JsonObject())
    }

}