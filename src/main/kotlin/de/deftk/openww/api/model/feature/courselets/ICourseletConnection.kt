package de.deftk.openww.api.model.feature.courselets

import kotlinx.serialization.json.JsonElement
import java.util.*

interface ICourseletConnection {

    val exists: Boolean
    val lastPageId: String
    val result: JsonElement
    val suspendData: String?
    val isBookmarked: Boolean
    val isCorrected: Boolean
    val isConversation: Boolean
    val isUnread: Boolean
    val pages: List<ICourseletPage>

    fun getCreationDate(): Date?
    fun getModificationDate(): Date?
    fun getProgressed(): Date?

}