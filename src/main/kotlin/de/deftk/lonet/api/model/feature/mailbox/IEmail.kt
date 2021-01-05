package de.deftk.lonet.api.model.feature.mailbox

import de.deftk.lonet.api.model.IRequestContext
import java.util.*

interface IEmail {

    fun getId(): Int
    fun getSubject(): String
    fun isUnread(): Boolean
    fun isFlagged(): Boolean
    fun isAnswered(): Boolean
    fun isDeleted(): Boolean
    fun getDate(): Date
    fun getSize(): Int
    fun getFrom(): List<EmailAddress>?
    fun getTo(): List<EmailAddress>?
    fun getCC(): List<EmailAddress>?
    fun getPlainBody(): String?
    fun getText(): String?
    fun getAttachments(): List<IAttachment>?

    fun read(folder: IEmailFolder, peek: Boolean? = null, context: IRequestContext)
    fun edit(folder: IEmailFolder, isFlagged: Boolean? = null, isUnread: Boolean? = null, context: IRequestContext)
    fun move(folder: IEmailFolder, to: IEmailFolder, context: IRequestContext)
    fun delete(folder: IEmailFolder, context: IRequestContext)

}