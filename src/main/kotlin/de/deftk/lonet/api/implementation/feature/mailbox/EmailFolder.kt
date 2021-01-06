package de.deftk.lonet.api.implementation.feature.mailbox

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.mailbox.IEmailFolder
import de.deftk.lonet.api.request.OperatingScopeApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import de.deftk.lonet.api.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import java.util.*

@Serializable
class EmailFolder(
    private val id: String,
    private var name: String,
    @SerialName("is_inbox")
    private val isInbox: Boolean,
    @SerialName("is_trash")
    private val isTrash: Boolean,
    @SerialName("is_drafts")
    private val isDrafts: Boolean,
    @SerialName("is_sent")
    private val isSent: Boolean,
    @SerialName("m_date")
    @Serializable(with = DateSerializer::class)
    private val date: Date
): IEmailFolder {

    override fun getId(): String = id
    override fun getName(): String = name
    override fun isInbox(): Boolean = isInbox
    override fun isTrash(): Boolean = isTrash
    override fun isDrafts(): Boolean = isDrafts
    override fun isSent(): Boolean = isSent
    override fun getDate(): Date = date

    override fun getEmails(limit: Int?, offset: Int?, context: IRequestContext): List<Email> {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetEmailsRequest(id, limit, offset)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["messages"]!!.jsonArray.map { Json.decodeFromJsonElement(it) }
    }

    override fun setName(name: String, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        request.addSetEmailFolderRequest(id, name)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
        this.name = name
    }

    //FIXME fails for whatever reason
    override fun delete(context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        request.addDeleteEmailFolderRequest(id)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EmailFolder

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}