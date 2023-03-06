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
    @SerialName("name")
    private var _name: String,
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
    override val date: Date?
): IEmailFolder {

    @SerialName("_name")
    override var name: String = _name
        private set

    override suspend fun getEmails(limit: Int?, offset: Int?, context: IRequestContext): List<Email> {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetEmailsRequest(id, limit, offset)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["messages"]!!.jsonArray.map { WebWeaverClient.json.decodeFromJsonElement(it) }
    }

    override suspend fun setName(name: String, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        request.addSetEmailFolderRequest(id, name)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
        this.name = name
    }

    override suspend fun delete(context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        request.addDeleteEmailFolderRequest(id)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun toString(): String {
        return "EmailFolder(name='$name')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EmailFolder) return false

        if (id != other.id) return false
        if (_name != other._name) return false
        if (isInbox != other.isInbox) return false
        if (isTrash != other.isTrash) return false
        if (isDrafts != other.isDrafts) return false
        if (isSent != other.isSent) return false
        if (date != other.date) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + _name.hashCode()
        result = 31 * result + isInbox.hashCode()
        result = 31 * result + isTrash.hashCode()
        result = 31 * result + isDrafts.hashCode()
        result = 31 * result + isSent.hashCode()
        result = 31 * result + (date?.hashCode() ?: 0)
        result = 31 * result + name.hashCode()
        return result
    }

}