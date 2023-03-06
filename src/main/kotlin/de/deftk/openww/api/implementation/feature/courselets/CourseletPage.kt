package de.deftk.openww.api.implementation.feature.courselets

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.courselets.ICourselet
import de.deftk.openww.api.model.feature.courselets.ICourseletPage
import de.deftk.openww.api.request.GroupApiRequest
import de.deftk.openww.api.response.ResponseUtil
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject


@Serializable
class CourseletPage(
    @SerialName("page_id")
    override val pageId: String,
    override val title: String,
    override val exists: Boolean? = null,
    @SerialName("max_score")
    override val maxScore: Int? = null,
    override val result: JsonObject? = null
) : ICourseletPage {

    override suspend fun addResult(courselet: ICourselet, score: Int?, time: Long?, context: IRequestContext) {
        val request = GroupApiRequest(context)
        request.addAddCourseletResultRequest(courselet.id, pageId, score, time)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override suspend fun deleteResults(courselet: ICourselet, context: IRequestContext) {
        val request = GroupApiRequest(context)
        request.addDeleteCourseletResultsRequest(courselet.id, pageId)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun toString(): String {
        return "CourseletPage(title='$title')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CourseletPage) return false

        if (pageId != other.pageId) return false
        if (title != other.title) return false
        if (exists != other.exists) return false
        if (maxScore != other.maxScore) return false
        if (result != other.result) return false

        return true
    }

    override fun hashCode(): Int {
        var result1 = pageId.hashCode()
        result1 = 31 * result1 + title.hashCode()
        result1 = 31 * result1 + (exists?.hashCode() ?: 0)
        result1 = 31 * result1 + (maxScore ?: 0)
        result1 = 31 * result1 + (result?.hashCode() ?: 0)
        return result1
    }

}