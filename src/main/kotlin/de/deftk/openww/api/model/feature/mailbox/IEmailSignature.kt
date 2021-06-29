package de.deftk.openww.api.model.feature.mailbox

import de.deftk.openww.api.model.IRequestContext

interface IEmailSignature {

    val text: String
    val answerPosition: SignaturePosition?
    val forwardPosition: SignaturePosition?

    fun edit(text: String, answerPosition: SignaturePosition? = null, forwardPosition: SignaturePosition? = null, context: IRequestContext)

}