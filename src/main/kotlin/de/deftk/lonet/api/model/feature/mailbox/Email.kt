package de.deftk.lonet.api.model.feature.mailbox

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.abstract.AbstractOperator
import de.deftk.lonet.api.request.ApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import java.io.Serializable
import java.util.*

class Email(val id: Int, val subject: String, read: Boolean, val flagged: Boolean, val answered: Boolean, val date: Date, val size: Long, val from: List<EmailAddress>?, val folder: EmailFolder, val operator: AbstractOperator) : Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject, folder: EmailFolder, operator: AbstractOperator): Email {
            return Email(
                    jsonObject.get("id").asInt,
                    jsonObject.get("subject").asString,
                    jsonObject.get("is_unread").asInt == 0,
                    jsonObject.get("is_flagged").asInt == 1,
                    jsonObject.get("is_answered").asInt == 1,
                    Date(jsonObject.get("date").asLong * 1000),
                    jsonObject.get("size").asLong,
                    jsonObject.get("from")?.asJsonArray?.map { EmailAddress.fromJson(it.asJsonObject) },
                    folder,
                    operator
            )
        }
    }

    var read = read
        private set

    fun read(): EmailContent {
        //TODO put into request class
        val request = ApiRequest()
        request.addSetFocusRequest("mailbox", operator.getLogin())
        val requestParams = JsonObject()
        requestParams.addProperty("folder_id", folder.id)
        requestParams.addProperty("message_id", id)
        request.addRequest("read_message", requestParams)
        val response = request.fireRequest(operator.getContext())
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), 3)
        read = true
        return EmailContent.fromJson(subResponse.get("message").asJsonObject)
    }

    //TODO attachments

    override fun toString(): String {
        return subject
    }

    data class EmailContent(val plainBody: String, val text: String?, val to: List<EmailAddress>) : Serializable {

        companion object {
            fun fromJson(jsonObject: JsonObject): EmailContent {
                return EmailContent(
                        jsonObject.get("body_plain").asString,
                        jsonObject.get("text")?.asString,
                        jsonObject.get("to").asJsonArray.map { EmailAddress.fromJson(it.asJsonObject) }
                )
            }
        }
    }

}