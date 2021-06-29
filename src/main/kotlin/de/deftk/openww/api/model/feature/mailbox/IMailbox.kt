package de.deftk.openww.api.model.feature.mailbox

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.Quota
import de.deftk.openww.api.model.feature.filestorage.session.ISessionFile

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
        importSessionFiles: List<ISessionFile>? = null,
        referenceFolderId: String? = null,
        referenceMessageId: Int? = null,
        referenceMode: ReferenceMode? = null,
        text: String? = null,
        context: IRequestContext
    )

    fun getEmailSignature(context: IRequestContext): IEmailSignature

}