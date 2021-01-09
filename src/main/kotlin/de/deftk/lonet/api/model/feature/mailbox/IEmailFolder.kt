package de.deftk.lonet.api.model.feature.mailbox

import de.deftk.lonet.api.model.IRequestContext
import java.util.*

interface IEmailFolder {

    val id: String
    val isInbox: Boolean
    val isTrash: Boolean
    val isDrafts: Boolean
    val isSent: Boolean
    val date: Date

    fun getName(): String

    fun getEmails(limit: Int? = null, offset: Int? = null, context: IRequestContext): List<IEmail>
    fun setName(name: String, context: IRequestContext)
    fun delete(context: IRequestContext)

}