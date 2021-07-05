package de.deftk.openww.api.model.feature.mailbox

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.Quota
import de.deftk.openww.api.model.feature.filestorage.session.ISessionFile

interface IMailbox {

    suspend fun getEmailStatus(context: IRequestContext): Pair<Quota, Int>
    suspend fun getEmailQuota(context: IRequestContext): Quota
    suspend fun getUnreadEmailCount(context: IRequestContext): Int

    suspend fun getEmailFolders(context: IRequestContext): List<IEmailFolder>
    suspend fun addEmailFolder(name: String, context: IRequestContext)

    suspend fun sendEmail(
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

    suspend fun getEmailSignature(context: IRequestContext): IEmailSignature

}