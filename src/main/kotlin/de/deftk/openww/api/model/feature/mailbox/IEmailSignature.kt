package de.deftk.openww.api.model.feature.mailbox

import de.deftk.openww.api.model.IRequestContext

interface IEmailSignature {

    fun getText(): String
    fun getAnswerPosition(): SignaturePosition?
    fun getForwardPosition(): SignaturePosition?

    fun edit(text: String, answerPosition: SignaturePosition, forwardPosition: SignaturePosition, context: IRequestContext)

}