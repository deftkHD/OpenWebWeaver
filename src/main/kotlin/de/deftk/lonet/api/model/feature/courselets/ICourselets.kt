package de.deftk.lonet.api.model.feature.courselets

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.FileDownloadUrl
import de.deftk.lonet.api.model.feature.Quota
import kotlinx.serialization.json.JsonElement

interface ICourselets {

    fun getCourseletState(context: IRequestContext): Pair<Quota, String>
    //TODO parse configuration
    fun getCourseletConfiguration(context: IRequestContext): JsonElement
    fun getCourselets(context: IRequestContext): List<ICourselet>

    fun getCourseletMappings(context: IRequestContext): List<ICourseletMapping>
    fun addCourseletMapping(name: String, context: IRequestContext): ICourseletMapping

    fun exportCourseletRuntime(context: IRequestContext): FileDownloadUrl

}