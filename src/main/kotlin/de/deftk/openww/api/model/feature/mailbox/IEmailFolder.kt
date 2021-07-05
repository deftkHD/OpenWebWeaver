package de.deftk.openww.api.model.feature.mailbox

import de.deftk.openww.api.model.IRequestContext
import java.util.*

interface IEmailFolder {

    val id: String
    val isInbox: Boolean
    val isTrash: Boolean
    val isDrafts: Boolean
    val isSent: Boolean
    val date: Date

    val name: String

    suspend fun getEmails(limit: Int? = null, offset: Int? = null, context: IRequestContext): List<IEmail>
    suspend fun setName(name: String, context: IRequestContext)
    suspend fun delete(context: IRequestContext)

}