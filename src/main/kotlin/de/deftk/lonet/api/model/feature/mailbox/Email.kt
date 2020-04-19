package de.deftk.lonet.api.model.feature.mailbox

import com.google.gson.JsonObject
import de.deftk.lonet.api.LoNet
import de.deftk.lonet.api.request.ApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import java.util.*

class Email(jsonObject: JsonObject, val folder: EmailFolder, private val responsibleHost: String) {

    val id = jsonObject.get("id").asInt
    val subject = jsonObject.get("subject").asString
    var read = jsonObject.get("is_unread").asInt == 0
        private set
    val flagged = jsonObject.get("is_flagged").asInt == 1
    val answered = jsonObject.get("is_answered").asInt == 1
    val date = Date(jsonObject.get("date").asLong * 1000)
    val size = jsonObject.get("size").asLong
    val from = jsonObject.get("from")?.asJsonArray?.map { EmailAddress(it.asJsonObject) }

    fun read(sessionId: String, overwriteCache: Boolean = false): EmailContent {
        val request = ApiRequest(responsibleHost)
        request.addSetSessionRequest(sessionId)
        request.addSetFocusRequest("mailbox")
        val requestParams = JsonObject()
        requestParams.addProperty("folder_id", folder.id)
        requestParams.addProperty("message_id", id)
        request.addRequest("read_message", requestParams)
        val response = LoNet.requestHandler.performRequest(request, !overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), 3)
        read = true
        return EmailContent(subResponse.get("message").asJsonObject)
    }

    //TODO attachments

    override fun toString(): String {
        return subject
    }

    class EmailContent(jsonObject: JsonObject) {

        val plainBody = jsonObject.get("body_plain").asString
        val text = jsonObject.get("text")?.asString // not sure if this is needed, but it also appears inside the send_mail request
        val to = jsonObject.get("to").asJsonArray.map { EmailAddress(it.asJsonObject) }

        override fun toString(): String {
            return plainBody
        }

    }

}