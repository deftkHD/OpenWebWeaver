package de.deftk.lonet.api.model.feature.courselets

import de.deftk.lonet.api.model.IRequestContext
import kotlinx.serialization.json.JsonObject

interface ICourseletPage {

    fun getPageId(): String
    fun getTitle(): String
    fun exists(): Boolean?
    fun maxScore(): Int?
    fun getResult(): JsonObject?

    fun addResult(courselet: ICourselet, score: Int? = null, time: Long? = null, context: IRequestContext)
    fun deleteResults(courselet: ICourselet, context: IRequestContext)

}