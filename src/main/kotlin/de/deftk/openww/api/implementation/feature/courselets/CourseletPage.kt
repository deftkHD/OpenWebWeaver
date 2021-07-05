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
        request.addAddCourseletResultRequest(courselet.id, pageId, score, time)[1]
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override suspend fun deleteResults(courselet: ICourselet, context: IRequestContext) {
        val request = GroupApiRequest(context)
        request.addDeleteCourseletResultsRequest(courselet.id, pageId)[1]
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun toString(): String {
        return "CourseletPage(title='$title')"
    }
}