package de.deftk.lonet.api.model.feature.mailbox

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.Quota
import kotlinx.serialization.json.JsonElement

interface IMailbox {

    fun getEmailStatus(context: IRequestContext): Pair<Quota, Int>
    fun getEmailQuota(context: IRequestContext): Quota
    fun getUnreadEmailCount(context: IRequestContext): Int

    fun getEmailFolders(context: IRequestContext): List<IEmailFolder>
    fun addEmailFolder(name: String, context: IRequestContext)

    fun sendEmail(
        to: String,
        subject: String,
        plainBody: String,
        addToSentFolder: Boolean? = null,
        cc: String? = null,
        bcc: String? = null,
        importSessionFiles: Array<JsonElement>? = null,
        referenceFolderId: String? = null,
        referenceMessageId: Int? = null,
        referenceMode: ReferenceMode? = null,
        text: String? = null,
        context: IRequestContext
    )

    fun getEmailSignature(context: IRequestContext): IEmailSignature

}