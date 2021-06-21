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

    fun setSuspendDate(suspendData: String, ifLatest: Int, context: IRequestContext)
    fun getResults(context: IRequestContext): ICourseletData
    fun deleteResults(context: IRequestContext)
    fun addBookmark(context: IRequestContext)
    fun deleteBookmark(context: IRequestContext)
    fun export(pkg: TemplatePackage? = null, context: IRequestContext): FileDownloadUrl
    fun delete(context: IRequestContext)


}