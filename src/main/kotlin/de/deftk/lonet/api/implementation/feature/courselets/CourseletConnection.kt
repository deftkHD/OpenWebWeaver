package de.deftk.lonet.api.implementation.feature.courselets

import de.deftk.lonet.api.model.feature.courselets.ICourseletConnection
import de.deftk.lonet.api.model.feature.courselets.ICourseletPage
import de.deftk.lonet.api.serialization.ApiDate
import de.deftk.lonet.api.serialization.BooleanFromIntSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import java.util.*

@Serializable
class CourseletConnection(
    private val exists: Boolean,
    private val created: ApiDate,
    private val modified: ApiDate,
    private val progressed: ApiDate,
    @SerialName("last_page_id")
    private val lastPageId: String,
    private val result: JsonObject,
    @SerialName("suspend_data")
    private val suspendData: String? = null,
    @SerialName("is_bookmarked")
    private val isBookmarked: Boolean,
    @SerialName("is_corrected")
    private val isCorrected: Boolean,
    @SerialName("is_conversation")
    private val isConversation: Boolean,
    @Serializable(with = BooleanFromIntSerializer::class)
    @SerialName("is_unread")
    private val isUnread: Boolean,
    private val pages: List<CourseletPage>
) : ICourseletConnection {

    override fun exists(): Boolean = exists
    override fun getCreationDate(): Date = created.date
    override fun getModificationDate(): Date = modified.date
    override fun getProgressed(): Date = progressed.date
    override fun getLastPageId(): String = lastPageId
    override fun getResult(): JsonElement = result
    override fun getSuspendData(): String? = suspendData
    override fun isBookmarked(): Boolean = isBookmarked
    override fun isCorrected(): Boolean = isCorrected
    override fun isConversation(): Boolean = isConversation
    override fun isUnread(): Boolean = isUnread
    override fun getPages(): List<ICourseletPage> = pages

}