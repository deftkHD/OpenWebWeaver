package de.deftk.lonet.api.model

import com.google.gson.JsonObject
import de.deftk.lonet.api.LoNet
import de.deftk.lonet.api.model.feature.SystemNotification
import de.deftk.lonet.api.model.feature.Task
import de.deftk.lonet.api.request.ApiRequest
import de.deftk.lonet.api.response.ApiResponse
import de.deftk.lonet.api.response.ResponseUtil

//TODO not sure if the complete response should be passed to the user
class User(val username: String, val authKey: String, responsibleHost: String, response: ApiResponse) :
    Member(
        response.toJson().asJsonArray.get(0).asJsonObject.get("result").asJsonObject.get("user").asJsonObject,
        responsibleHost
    ) {

    val sessionId: String
    val memberships: List<Member>

    init {
        val jsonResponse = response.toJson().asJsonArray
        val loginResponse = ResponseUtil.getSubResponseResultByMethod(jsonResponse, "login")
        val informationResponse = ResponseUtil.getSubResponseResultByMethod(jsonResponse, "get_information")

        sessionId = informationResponse.get("session_id").asString
        memberships = loginResponse.get("member").asJsonArray.map { Member(it.asJsonObject, responsibleHost) }
    }

    fun getTasks(): List<Task> {
        return getTasks(sessionId)
    }

    override fun getTasks(sessionId: String): List<Task> {
        val tasks = super.getTasks(sessionId).toMutableList()
        memberships.forEach { membership ->
            tasks.addAll(membership.getTasks(sessionId))
        }
        return tasks
    }

    fun logout() {
        val request = ApiRequest(responsibleHost!!)
        request.addSetSessionRequest(sessionId)
        request.addRequest("logout", null)
        val response = LoNet.performJsonApiRequest(request)
        println(response.raw)
    }

    fun getAutoLoginUrl(): String {
        val request = ApiRequest(responsibleHost!!)
        request.addSetSessionRequest(sessionId)
        request.addSetFocusRequest("trusts", login)
        request.addRequest("get_url_for_autologin", null)
        val response = LoNet.performJsonApiRequest(request)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), 3)
        return subResponse.get("url").asString
    }

    fun getSystemNofications(): List<SystemNotification> {
        val request = ApiRequest(responsibleHost!!)
        request.addSetSessionRequest(sessionId)
        request.addSetFocusRequest("messages")
        request.addRequest("get_messages", null)
        val response = LoNet.performJsonApiRequest(request)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), 3)
        return subResponse.get("messages")?.asJsonArray?.map { SystemNotification(it.asJsonObject) } ?: emptyList()
    }

}