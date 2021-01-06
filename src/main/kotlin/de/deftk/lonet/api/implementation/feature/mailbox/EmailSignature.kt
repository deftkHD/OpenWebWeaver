package de.deftk.lonet.api.implementation.feature.mailbox

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.mailbox.IEmailSignature
import de.deftk.lonet.api.model.feature.mailbox.SignaturePosition
import de.deftk.lonet.api.request.OperatingScopeApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

@Serializable
class EmailSignature(
    private var text: String,
    @SerialName("position_answer")
    private var answerPosition: SignaturePosition? = null,
    @SerialName("position_forward")
    private var forwardPosition: SignaturePosition? = null
): IEmailSignature {

    override fun getText(): String = text
    override fun getAnswerPosition(): SignaturePosition? = answerPosition
    override fun getForwardPosition(): SignaturePosition? = forwardPosition

    override fun edit(text: String, answerPosition: SignaturePosition?, forwardPosition: SignaturePosition?, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        val id = request.addSetEmailSignatureRequest(text, answerPosition, forwardPosition)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(Json.decodeFromJsonElement(subResponse["signature"]!!))
    }

    private fun readFrom(signature: EmailSignature) {
        text = signature.text
        answerPosition = signature.answerPosition
        forwardPosition = signature.forwardPosition
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EmailSignature

        if (text != other.text) return false

        return true
    }

    override fun hashCode(): Int {
        return text.hashCode()
    }

}