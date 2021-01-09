package de.deftk.lonet.api.implementation.feature.courselets

import de.deftk.lonet.api.model.feature.courselets.ICourseletConnection
import de.deftk.lonet.api.serialization.ApiDate
import de.deftk.lonet.api.serialization.BooleanFromIntSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import java.util.*

@Serializable
class CourseletConnection(
    override val exists: Boolean,
    private val created: ApiDate,
    private var modified: ApiDate,
    private val progressed: ApiDate,
    @SerialName("last_page_id")
    override val lastPageId: String,
    override val result: JsonObject,
    @SerialName("suspend_data")
    override val suspendData: String? = null,
    @SerialName("is_bookmarked")
    override val isBookmarked: Boolean,
    @SerialName("is_corrected")
    override val isCorrected: Boolean,
    @SerialName("is_conversation")
    override val isConversation: Boolean,
    @Serializable(with = BooleanFromIntSerializer::class)
    @SerialName("is_unread")
    override val isUnread: Boolean,
    override val pages: List<CourseletPage>
) : ICourseletConnection {

    override fun getCreationDate(): Date = created.date
    override fun getModificationDate(): Date = modified.date
    override fun getProgressed(): Date = progressed.date
}