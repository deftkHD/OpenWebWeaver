package de.deftk.lonet.api.implementation.feature.mailbox

import de.deftk.lonet.api.LoNetClient
import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.mailbox.EmailAddress
import de.deftk.lonet.api.model.feature.mailbox.IAttachment
import de.deftk.lonet.api.model.feature.mailbox.IEmail
import de.deftk.lonet.api.model.feature.mailbox.IEmailFolder
import de.deftk.lonet.api.request.OperatingScopeApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import de.deftk.lonet.api.serialization.BooleanFromIntSerializer
import de.deftk.lonet.api.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement
import java.util.*

@Serializable
class Email(
    override val id: Int,
    private var subject: String,
    @SerialName("is_unread")
    @Serializable(with = BooleanFromIntSerializer::class)
    private var unread: Boolean,
    @SerialName("is_flagged")
    @Serializable(with = BooleanFromIntSerializer::class)
    private var flagged: Boolean,
    @SerialName("is_answered")
    @Serializable(with = BooleanFromIntSerializer::class)
    private var answered: Boolean,
    @SerialName("is_deleted")
    @Serializable(with = BooleanFromIntSerializer::class)
    private var deleted: Boolean,
    @Serializable(with = DateSerializer::class)
    private var date: Date,
    private var size: Int,
    private var from: List<EmailAddress>? = null,
    private var to: List<EmailAddress>? = null,
    private var cc: List<EmailAddress>? = null,
    @SerialName("body_plain")
    private var plainBody: String? = null,
    private var text: String? = null,
    private var files: List<Attachment>? = null
) : IEmail {

    override fun getSubject(): String = subject
    override fun isUnread(): Boolean = unread
    override fun isFlagged(): Boolean = flagged
    override fun isAnswered(): Boolean = answered
    override fun isDeleted(): Boolean = deleted
    override fun getDate(): Date = date
    override fun getSize(): Int = size
    override fun getFrom(): List<EmailAddress>? = from
    override fun getTo(): List<EmailAddress>? = to
    override fun getCC(): List<EmailAddress>? = cc
    override fun getPlainBody(): String? = plainBody
    override fun getText(): String? = text
    override fun getAttachments(): List<IAttachment>? = files

    override fun read(folder: IEmailFolder, peek: Boolean?, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        val id = request.addReadEmailRequest(folder.id, id, peek)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(LoNetClient.json.decodeFromJsonElement(subResponse["message"]!!))
    }

    override fun edit(folder: IEmailFolder, isFlagged: Boolean?, isUnread: Boolean?, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        request.addSetEmailRequest(folder.id, id, isFlagged, isUnread)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
        if (isFlagged != null)
            this.flagged = isFlagged
        if (isUnread != null)
            this.unread = isUnread
    }

    override fun move(folder: IEmailFolder, to: IEmailFolder, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        request.addMoveEmailRequest(folder.id, id, to.id)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun delete(folder: IEmailFolder, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        request.addDeleteEmailRequest(folder.id, id)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
        deleted = true
    }

    private fun readFrom(email: Email) {
        subject = email.subject
        unread = email.unread
        flagged = email.flagged
        answered = email.answered
        deleted = email.deleted
        date = email.date
        size = email.size
        from = email.from
        to = email.to
        cc = email.cc
        plainBody = email.plainBody
        text = email.text
        files = email.files
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Email

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    override fun toString(): String {
        return "Email(subject='$subject')"
    }

}