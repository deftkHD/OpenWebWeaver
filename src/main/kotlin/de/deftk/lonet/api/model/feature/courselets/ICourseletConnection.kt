package de.deftk.lonet.api.model.feature.courselets

import kotlinx.serialization.json.JsonElement
import java.util.*

interface ICourseletConnection {

    fun exists(): Boolean
    fun getCreationDate(): Date
    fun getModificationDate(): Date
    fun getProgressed(): Date
    fun getLastPageId(): String
    fun getResult(): JsonElement
    fun getSuspendData(): String?
    fun isBookmarked(): Boolean
    fun isCorrected(): Boolean
    fun isConversation(): Boolean
    fun isUnread(): Boolean
    fun getPages(): List<ICourseletPage>

}