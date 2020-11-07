package de.deftk.lonet.api.model.feature.courselets

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Group
import de.deftk.lonet.api.request.GroupApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import java.util.*

class CourseletPage(val courselet: Courselet, val pageId: String, val title: String, val resultExists: Boolean, val maxScore: Int, val group: Group) {

    companion object {
        fun fromJson(jsonObject: JsonObject, group: Group, courselet: Courselet): CourseletPage {
            val resultObject = jsonObject.get("result").asJsonObject
            return CourseletPage(
                    courselet,
                    jsonObject.get("page_id").asString,
                    jsonObject.get("title").asString,
                    resultObject.get("exists").asBoolean,
                    resultObject.get("score_max").asInt,
                    group
            )
        }
    }

    fun addResult(score: Int? = null, time: Date? = null) {
        val request = GroupApiRequest(group)
        request.addAddCourseletResultRequest(courselet.id, pageId, score, time)[1]
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    fun deleteResults() {
        val request = GroupApiRequest(group)
        request.addDeleteCourseletResultsRequest(courselet.id, pageId)[1]
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

}