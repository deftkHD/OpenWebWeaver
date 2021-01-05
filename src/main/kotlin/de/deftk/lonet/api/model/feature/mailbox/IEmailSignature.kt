package de.deftk.lonet.api.model.feature.mailbox

import de.deftk.lonet.api.model.IRequestContext

interface IEmailSignature {

    fun getText(): String
    fun getAnswerPosition(): SignaturePosition?
    fun getForwardPosition(): SignaturePosition?

    fun edit(text: String, answerPosition: SignaturePosition? = null, forwardPosition: SignaturePosition? = null, context: IRequestContext)

}