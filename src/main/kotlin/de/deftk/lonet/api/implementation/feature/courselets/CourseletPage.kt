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
    private val pageId: String,
    private val title: String,
    private val exists: Boolean? = null,
    @SerialName("max_score")
    private val maxScore: Int? = null,
    private val result: JsonObject? = null
) : ICourseletPage {

    override fun getPageId(): String = pageId
    override fun getTitle(): String = title
    override fun exists(): Boolean? = exists
    override fun maxScore(): Int? = maxScore
    override fun getResult(): JsonObject? = result

    override fun addResult(courselet: ICourselet, score: Int?, time: Long?, context: IRequestContext) {
        val request = GroupApiRequest(context)
        request.addAddCourseletResultRequest(courselet.getId(), pageId, score, time)[1]
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun deleteResults(courselet: ICourselet, context: IRequestContext) {
        val request = GroupApiRequest(context)
        request.addDeleteCourseletResultsRequest(courselet.getId(), pageId)[1]
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }
}