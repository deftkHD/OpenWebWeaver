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
    private var _unread: Boolean,
    @SerialName("is_flagged")
    @Serializable(with = BooleanFromIntSerializer::class)
    private var _flagged: Boolean,
    @SerialName("is_answered")
    @Serializable(with = BooleanFromIntSerializer::class)
    private var _answered: Boolean,
    @SerialName("is_deleted")
    @Serializable(with = BooleanFromIntSerializer::class)
    private var _deleted: Boolean,
    @SerialName("date")
    @Serializable(with = DateSerializer::class)
    private var _date: Date,
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
    override var unread: Boolean = _unread
        private set

    @SerialName("_is_flagged")
    override var flagged: Boolean = _flagged
        private set

    @SerialName("_is_answered")
    override var answered: Boolean = _answered
            private set

    @SerialName("_is_deleted")
    override var deleted: Boolean = _deleted
        private set

    @SerialName("_date")
    @Serializable(with = DateSerializer::class)
    override var date: Date = _date
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

    override fun read(folder: IEmailFolder, peek: Boolean?, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        val id = request.addReadEmailRequest(folder.id, id, peek)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse["message"]!!))
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
        attachments = email.attachments
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