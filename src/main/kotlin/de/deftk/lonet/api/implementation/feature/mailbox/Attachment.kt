package de.deftk.lonet.api.implementation.feature.mailbox

import de.deftk.lonet.api.LoNetClient
import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.FileDownloadUrl
import de.deftk.lonet.api.model.feature.mailbox.IAttachment
import de.deftk.lonet.api.model.feature.mailbox.IEmail
import de.deftk.lonet.api.model.feature.mailbox.IEmailFolder
import de.deftk.lonet.api.request.OperatingScopeApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement

@Serializable
class Attachment(
    override val id: String,
    override val name: String,
    override val size: Int
) : IAttachment {

    override fun exportSessionFile(email: IEmail, folder: IEmailFolder, context: IRequestContext): FileDownloadUrl {
        val request = OperatingScopeApiRequest(context)
        val id = request.addExportEmailSessionFileRequest(id, folder.id, email.id)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return LoNetClient.json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Attachment

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}