package de.deftk.openww.api.implementation.feature.mailbox

import de.deftk.openww.api.WebWeaverClient
import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.mailbox.IEmailSignature
import de.deftk.openww.api.model.feature.mailbox.SignaturePosition
import de.deftk.openww.api.request.OperatingScopeApiRequest
import de.deftk.openww.api.response.ResponseUtil
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement

@Serializable
class EmailSignature(
    @SerialName("text")
    private var _text: String,
    @SerialName("position_answer")
    private var _answerPosition: SignaturePosition? = null,
    @SerialName("position_forward")
    private var _forwardPosition: SignaturePosition? = null
): IEmailSignature {

    @SerialName("_text")
    override var text: String = _text
        private set

    @SerialName("_position_answer")
    override var answerPosition: SignaturePosition? = _answerPosition
        private set

    @SerialName("_position_forward")
    override var forwardPosition: SignaturePosition? = _forwardPosition
        private set

    override suspend fun edit(text: String, answerPosition: SignaturePosition, forwardPosition: SignaturePosition, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        val id = request.addSetEmailSignatureRequest(text, answerPosition, forwardPosition)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse["signature"]!!))
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

    override fun toString(): String {
        return "EmailSignature(text='$text')"
    }

}