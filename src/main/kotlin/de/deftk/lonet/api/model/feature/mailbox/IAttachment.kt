package de.deftk.lonet.api.model.feature.mailbox

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.FileDownloadUrl

interface IAttachment {

    fun getId(): String
    fun getName(): String
    fun getSize(): Int

    fun exportSessionFile(email: IEmail, folder: IEmailFolder, context: IRequestContext): FileDownloadUrl

}