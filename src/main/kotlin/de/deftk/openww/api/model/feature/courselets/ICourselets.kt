package de.deftk.openww.api.model.feature.courselets

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.FileDownloadUrl
import de.deftk.openww.api.model.feature.Quota
import kotlinx.serialization.json.JsonElement

interface ICourselets {

    suspend fun getCourseletState(context: IRequestContext): Pair<Quota, String>
    //TODO parse configuration
    suspend fun getCourseletConfiguration(context: IRequestContext): JsonElement
    suspend fun getCourselets(context: IRequestContext): List<ICourselet>

    suspend fun getCourseletMappings(context: IRequestContext): List<ICourseletMapping>
    suspend fun addCourseletMapping(name: String, context: IRequestContext): ICourseletMapping

    suspend fun exportCourseletRuntime(context: IRequestContext): FileDownloadUrl

}