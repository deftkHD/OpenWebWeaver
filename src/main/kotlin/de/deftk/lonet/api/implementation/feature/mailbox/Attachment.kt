package de.deftk.lonet.api.implementation.feature.mailbox

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.FileDownloadUrl
import de.deftk.lonet.api.model.feature.mailbox.IAttachment
import de.deftk.lonet.api.model.feature.mailbox.IEmail
import de.deftk.lonet.api.model.feature.mailbox.IEmailFolder
import de.deftk.lonet.api.request.OperatingScopeApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

@Serializable
class Attachment(
    private val id: String,
    private val name: String,
    private val size: Int
) : IAttachment {

    override fun getId(): String = id
    override fun getName(): String = name
    override fun getSize(): Int = size

    override fun exportSessionFile(email: IEmail, folder: IEmailFolder, context: IRequestContext): FileDownloadUrl {
        val request = OperatingScopeApiRequest(context)
        val id = request.addExportEmailSessionFileRequest(id, folder.getId(), email.getId())[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["file"]!!)
    }

}