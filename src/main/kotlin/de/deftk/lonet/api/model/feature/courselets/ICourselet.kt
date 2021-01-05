package de.deftk.lonet.api.model.feature.courselets

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.FileDownloadUrl
import de.deftk.lonet.api.model.feature.IModifiable

interface ICourselet: IModifiable {

    fun getId(): Int
    fun getTitle(): String
    fun getMapping(): String
    fun isLink(): Boolean
    fun isVisible(): Boolean
    fun isTemplate(): Boolean
    fun getSize(): Int

    fun setSuspendDate(suspendData: String, ifLatest: Int, context: IRequestContext)
    fun getResults(context: IRequestContext): ICourseletData
    fun deleteResults(context: IRequestContext)
    fun addBookmark(context: IRequestContext)
    fun deleteBookmark(context: IRequestContext)
    fun export(pkg: TemplatePackage? = null, context: IRequestContext): FileDownloadUrl
    fun delete(context: IRequestContext)


}