package de.deftk.openww.api.model.feature.courselets

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.FileDownloadUrl
import de.deftk.openww.api.model.feature.Quota
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