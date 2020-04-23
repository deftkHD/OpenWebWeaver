package de.deftk.lonet.api.model.feature.abstract

import de.deftk.lonet.api.model.feature.Quota
import de.deftk.lonet.api.model.feature.mailbox.EmailFolder

interface IMailbox {

    //TODO groups are also able to do some of this stuff
    fun getEmailStatus(overwriteCache: Boolean = false): Pair<Quota, Int>
    fun getEmailQuota(overwriteCache: Boolean = false): Quota
    fun getUnreadEmailCount(overwriteCache: Boolean = false): Int
    fun getEmailFolders(overwriteCache: Boolean = false): List<EmailFolder>
    fun sendEmail(to: String, subject: String, plainBody: String, text: String? = null, bcc: String? = null, cc: String? = null)

}