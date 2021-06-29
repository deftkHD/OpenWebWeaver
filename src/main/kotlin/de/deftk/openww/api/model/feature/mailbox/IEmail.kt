package de.deftk.openww.api.model.feature.mailbox

import de.deftk.openww.api.model.IRequestContext
import java.util.*

interface IEmail {

    val id: Int

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
    fun edit(folder: IEmailFolder, isFlagged: Boolean, isUnread: Boolean, context: IRequestContext)
    fun move(folder: IEmailFolder, to: IEmailFolder, context: IRequestContext)
    fun delete(folder: IEmailFolder, context: IRequestContext)

}