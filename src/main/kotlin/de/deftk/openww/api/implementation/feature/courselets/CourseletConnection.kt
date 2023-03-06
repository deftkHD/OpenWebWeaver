package de.deftk.openww.api.implementation.feature.courselets

import de.deftk.openww.api.model.feature.courselets.ICourseletConnection
import de.deftk.openww.api.serialization.ApiDate
import de.deftk.openww.api.serialization.BooleanFromIntSerializer
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

    override fun getCreationDate(): Date? = created.date
    override fun getModificationDate(): Date? = modified.date
    override fun getProgressed(): Date? = progressed.date
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CourseletConnection) return false

        if (exists != other.exists) return false
        if (created != other.created) return false
        if (modified != other.modified) return false
        if (progressed != other.progressed) return false
        if (lastPageId != other.lastPageId) return false
        if (result != other.result) return false
        if (suspendData != other.suspendData) return false
        if (isBookmarked != other.isBookmarked) return false
        if (isCorrected != other.isCorrected) return false
        if (isConversation != other.isConversation) return false
        if (isUnread != other.isUnread) return false
        if (pages != other.pages) return false

        return true
    }

    override fun hashCode(): Int {
        var result1 = exists.hashCode()
        result1 = 31 * result1 + created.hashCode()
        result1 = 31 * result1 + modified.hashCode()
        result1 = 31 * result1 + progressed.hashCode()
        result1 = 31 * result1 + lastPageId.hashCode()
        result1 = 31 * result1 + result.hashCode()
        result1 = 31 * result1 + (suspendData?.hashCode() ?: 0)
        result1 = 31 * result1 + isBookmarked.hashCode()
        result1 = 31 * result1 + isCorrected.hashCode()
        result1 = 31 * result1 + isConversation.hashCode()
        result1 = 31 * result1 + isUnread.hashCode()
        result1 = 31 * result1 + pages.hashCode()
        return result1
    }

}