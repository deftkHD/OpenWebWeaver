package de.deftk.openww.api.implementation.feature.mailbox

import de.deftk.openww.api.WebWeaverClient
import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.FileDownloadUrl
import de.deftk.openww.api.model.feature.mailbox.IAttachment
import de.deftk.openww.api.model.feature.mailbox.IEmail
import de.deftk.openww.api.model.feature.mailbox.IEmailFolder
import de.deftk.openww.api.request.OperatingScopeApiRequest
import de.deftk.openww.api.response.ResponseUtil
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement

@Serializable
class Attachment(
    override val id: String,
    override val name: String,
    override val size: Int
) : IAttachment {

    override suspend fun exportSessionFile(email: IEmail, folder: IEmailFolder, context: IRequestContext): FileDownloadUrl {
        val request = OperatingScopeApiRequest(context)
        val id = request.addExportEmailSessionFileRequest(id, folder.id, email.id)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override fun toString(): String {
        return "Attachment(name='$name')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Attachment) return false

        if (id != other.id) return false
        if (name != other.name) return false
        if (size != other.size) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + size
        return result
    }

}