package de.deftk.openww.api.implementation.feature.mailbox

import de.deftk.openww.api.WebWeaverClient
import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.mailbox.EmailAddress
import de.deftk.openww.api.model.feature.mailbox.IAttachment
import de.deftk.openww.api.model.feature.mailbox.IEmail
import de.deftk.openww.api.model.feature.mailbox.IEmailFolder
import de.deftk.openww.api.request.OperatingScopeApiRequest
import de.deftk.openww.api.response.ResponseUtil
import de.deftk.openww.api.serialization.BooleanFromIntSerializer
import de.deftk.openww.api.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement
import java.util.*

@Serializable
class Email(
    override val id: Int,
    @SerialName("subject")
    private var _subject: String,
    @SerialName("is_unread")
    @Serializable(with = BooleanFromIntSerializer::class)
    private var _unread: Boolean?,
    @SerialName("is_flagged")
    @Serializable(with = BooleanFromIntSerializer::class)
    private var _flagged: Boolean?,
    @SerialName("is_answered")
    @Serializable(with = BooleanFromIntSerializer::class)
    private var _answered: Boolean?,
    @SerialName("is_deleted")
    @Serializable(with = BooleanFromIntSerializer::class)
    private var _deleted: Boolean?,
    @SerialName("date")
    @Serializable(with = DateSerializer::class)
    private var _date: Date?,
    @SerialName("size")
    private var _size: Int,
    @SerialName("from")
    private var _from: List<EmailAddress>? = null,
    @SerialName("to")
    private var _to: List<EmailAddress>? = null,
    @SerialName("cc")
    private var _cc: List<EmailAddress>? = null,
    @SerialName("body_plain")
    private var _plainBody: String? = null,
    @SerialName("text")
    private var _text: String? = null,
    @SerialName("files")
    private var _files: List<Attachment>? = null
) : IEmail {

    @SerialName("_subject")
    override var subject: String = _subject
        private set

    @SerialName("_is_unread")
    override var unread: Boolean? = _unread
        private set

    @SerialName("_is_flagged")
    override var flagged: Boolean? = _flagged
        private set

    @SerialName("_is_answered")
    override var answered: Boolean? = _answered
            private set

    @SerialName("_is_deleted")
    override var deleted: Boolean? = _deleted
        private set

    @SerialName("_date")
    @Serializable(with = DateSerializer::class)
    override var date: Date? = _date
        private set

    @SerialName("_size")
    override var size: Int = _size
        private set

    @SerialName("_from")
    override var from: List<EmailAddress>? = _from
        private set

    @SerialName("_to")
    override var to: List<EmailAddress>? = _to
        private set

    @SerialName("_cc")
    override var cc: List<EmailAddress>? = _cc
        private set

    @SerialName("_body_plain")
    override var plainBody: String? = _plainBody
        private set

    @SerialName("_text")
    override var text: String? = _text
        private set

    @SerialName("_files")
    override var attachments: List<IAttachment>? = _files
        private set

    override suspend fun read(folder: IEmailFolder, peek: Boolean?, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        val id = request.addReadEmailRequest(folder.id, id, peek)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse["message"]!!))
    }

    override suspend fun edit(folder: IEmailFolder, isFlagged: Boolean, isUnread: Boolean, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        request.addSetEmailRequest(folder.id, id, isFlagged, isUnread)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
        this.flagged = isFlagged
        this.unread = isUnread
    }

    override suspend fun move(folder: IEmailFolder, to: IEmailFolder, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        request.addMoveEmailRequest(folder.id, id, to.id)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override suspend fun delete(folder: IEmailFolder, context: IRequestContext) {
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
        attachments = email.attachments
    }

    override fun toString(): String {
        return "Email(subject='$subject')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Email) return false

        if (id != other.id) return false
        if (_subject != other._subject) return false
        if (_unread != other._unread) return false
        if (_flagged != other._flagged) return false
        if (_answered != other._answered) return false
        if (_deleted != other._deleted) return false
        if (_date != other._date) return false
        if (_size != other._size) return false
        if (_from != other._from) return false
        if (_to != other._to) return false
        if (_cc != other._cc) return false
        if (_plainBody != other._plainBody) return false
        if (_text != other._text) return false
        if (_files != other._files) return false
        if (subject != other.subject) return false
        if (unread != other.unread) return false
        if (flagged != other.flagged) return false
        if (answered != other.answered) return false
        if (deleted != other.deleted) return false
        if (date != other.date) return false
        if (size != other.size) return false
        if (from != other.from) return false
        if (to != other.to) return false
        if (cc != other.cc) return false
        if (plainBody != other.plainBody) return false
        if (text != other.text) return false
        if (attachments != other.attachments) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + _subject.hashCode()
        result = 31 * result + (_unread?.hashCode() ?: 0)
        result = 31 * result + (_flagged?.hashCode() ?: 0)
        result = 31 * result + (_answered?.hashCode() ?: 0)
        result = 31 * result + (_deleted?.hashCode() ?: 0)
        result = 31 * result + (_date?.hashCode() ?: 0)
        result = 31 * result + _size
        result = 31 * result + (_from?.hashCode() ?: 0)
        result = 31 * result + (_to?.hashCode() ?: 0)
        result = 31 * result + (_cc?.hashCode() ?: 0)
        result = 31 * result + (_plainBody?.hashCode() ?: 0)
        result = 31 * result + (_text?.hashCode() ?: 0)
        result = 31 * result + (_files?.hashCode() ?: 0)
        result = 31 * result + subject.hashCode()
        result = 31 * result + (unread?.hashCode() ?: 0)
        result = 31 * result + (flagged?.hashCode() ?: 0)
        result = 31 * result + (answered?.hashCode() ?: 0)
        result = 31 * result + (deleted?.hashCode() ?: 0)
        result = 31 * result + (date?.hashCode() ?: 0)
        result = 31 * result + size
        result = 31 * result + (from?.hashCode() ?: 0)
        result = 31 * result + (to?.hashCode() ?: 0)
        result = 31 * result + (cc?.hashCode() ?: 0)
        result = 31 * result + (plainBody?.hashCode() ?: 0)
        result = 31 * result + (text?.hashCode() ?: 0)
        result = 31 * result + (attachments?.hashCode() ?: 0)
        return result
    }

}