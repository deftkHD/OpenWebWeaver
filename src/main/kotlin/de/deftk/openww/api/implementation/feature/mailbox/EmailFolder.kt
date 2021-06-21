package de.deftk.openww.api.implementation.feature.mailbox

import de.deftk.openww.api.WebWeaverClient
import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.mailbox.IEmailFolder
import de.deftk.openww.api.request.OperatingScopeApiRequest
import de.deftk.openww.api.response.ResponseUtil
import de.deftk.openww.api.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import java.util.*

@Serializable
class EmailFolder(
    override val id: String,
    private var name: String,
    @SerialName("is_inbox")
    override val isInbox: Boolean,
    @SerialName("is_trash")
    override val isTrash: Boolean,
    @SerialName("is_drafts")
    override val isDrafts: Boolean,
    @SerialName("is_sent")
    override val isSent: Boolean,
    @SerialName("m_date")
    @Serializable(with = DateSerializer::class)
    override val date: Date
): IEmailFolder {

    override fun getName(): String = name

    override fun getEmails(limit: Int?, offset: Int?, context: IRequestContext): List<Email> {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetEmailsRequest(id, limit, offset)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["messages"]!!.jsonArray.map { WebWeaverClient.json.decodeFromJsonElement(it) }
    }

    override fun setName(name: String, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        request.addSetEmailFolderRequest(id, name)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
        this.name = name
    }

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

    override fun toString(): String {
        return "EmailFolder(name='$name')"
    }

}