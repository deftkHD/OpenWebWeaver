package de.deftk.openww.api.model.feature.courselets

import de.deftk.openww.api.model.IRequestContext
import kotlinx.serialization.json.JsonObject

interface ICourseletPage {

    val pageId: String
    val title: String
    val exists: Boolean?
    val maxScore: Int?
    val result: JsonObject?

    suspend fun addResult(courselet: ICourselet, score: Int? = null, time: Long? = null, context: IRequestContext)
    suspend fun deleteResults(courselet: ICourselet, context: IRequestContext)

}