package de.deftk.lonet.api.model.feature.mailbox

import de.deftk.lonet.api.model.IRequestContext
import java.util.*

interface IEmailFolder {

    fun getId(): String
    fun getName(): String
    fun isInbox(): Boolean
    fun isTrash(): Boolean
    fun isDrafts(): Boolean
    fun isSent(): Boolean
    fun getDate(): Date

    fun getEmails(limit: Int? = null, offset: Int? = null, context: IRequestContext): List<IEmail>
    fun setName(name: String, context: IRequestContext)
    fun delete(context: IRequestContext)

}