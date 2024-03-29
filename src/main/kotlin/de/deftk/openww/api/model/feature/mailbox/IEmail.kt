package de.deftk.openww.api.model.feature.mailbox

import de.deftk.openww.api.model.IRequestContext
import java.util.*

interface IEmail {

    val id: Int

    val subject: String
    val unread: Boolean?
    val flagged: Boolean?
    val answered: Boolean?
    val deleted: Boolean?
    val date: Date?
    val size: Int
    val from: List<EmailAddress>?
    val to: List<EmailAddress>?
    val cc: List<EmailAddress>?
    val plainBody: String?
    val text: String?
    val attachments: List<IAttachment>?

    suspend fun read(folder: IEmailFolder, peek: Boolean? = null, context: IRequestContext)
    suspend fun edit(folder: IEmailFolder, isFlagged: Boolean, isUnread: Boolean, context: IRequestContext)
    suspend fun move(folder: IEmailFolder, to: IEmailFolder, context: IRequestContext)
    suspend fun delete(folder: IEmailFolder, context: IRequestContext)

}