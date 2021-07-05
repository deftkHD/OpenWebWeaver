package de.deftk.openww.api.model.feature.courselets

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.FileDownloadUrl
import de.deftk.openww.api.model.feature.IModifiable

interface ICourselet: IModifiable {

    val id: Int
    val title: String
    val mapping: String
    val isLink: Boolean
    val isVisible: Boolean
    val isTemplate: Boolean
    val size: Int

    suspend fun setSuspendDate(suspendData: String, ifLatest: Int, context: IRequestContext)
    suspend fun getResults(context: IRequestContext): ICourseletData
    suspend fun deleteResults(context: IRequestContext)
    suspend fun addBookmark(context: IRequestContext)
    suspend fun deleteBookmark(context: IRequestContext)
    suspend fun export(pkg: TemplatePackage? = null, context: IRequestContext): FileDownloadUrl
    suspend fun delete(context: IRequestContext)


}