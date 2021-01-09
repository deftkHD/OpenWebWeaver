package de.deftk.lonet.api.implementation.feature.courselets

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.courselets.ICourselet
import de.deftk.lonet.api.model.feature.courselets.ICourseletPage
import de.deftk.lonet.api.request.GroupApiRequest
import de.deftk.lonet.api.response.ResponseUtil
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

    override fun addResult(courselet: ICourselet, score: Int?, time: Long?, context: IRequestContext) {
        val request = GroupApiRequest(context)
        request.addAddCourseletResultRequest(courselet.id, pageId, score, time)[1]
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun deleteResults(courselet: ICourselet, context: IRequestContext) {
        val request = GroupApiRequest(context)
        request.addDeleteCourseletResultsRequest(courselet.id, pageId)[1]
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }
}