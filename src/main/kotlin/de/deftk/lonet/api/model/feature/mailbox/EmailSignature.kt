package de.deftk.lonet.api.model.feature.mailbox

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.abstract.AbstractOperator
import de.deftk.lonet.api.request.OperatorApiRequest
import de.deftk.lonet.api.response.ResponseUtil

class EmailSignature(var text: String, var positionAnswer: Position?, var positionForward: Position?, val operator: AbstractOperator) {

    companion object {
        fun fromJson(jsonObject: JsonObject, operator: AbstractOperator): EmailSignature {
            val signature = EmailSignature(
                    jsonObject.get("text").asString,
                    null,
                    null,
                    operator
            )
            signature.readFrom(jsonObject)
            return signature
        }
    }

    fun edit(text: String, positionAnswer: Position? = null, positionForward: Position? = null) {
        val request = OperatorApiRequest(operator)
        val id = request.addSetEmailSignatureRequest(text, positionAnswer?.text, positionForward?.text)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(subResponse.get("signature").asJsonObject)
    }

    private fun readFrom(jsonObject: JsonObject) {
        positionAnswer = Position.byTextValue(jsonObject.get("position_answer").asString)
        positionForward = Position.byTextValue(jsonObject.get("position_forward").asString)
    }

    enum class Position(val text: String) {
        BEGINNING("beginning"),
        END("end"),
        NONE("none");

        companion object {
            fun byTextValue(text: String): Position? {
                return values().firstOrNull { it.text == text }
            }
        }
    }

}