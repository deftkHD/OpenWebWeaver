package de.deftk.openww.api.model.feature.courselets

import de.deftk.openww.api.model.IRequestContext

interface ICourseletMapping {

    val id: Int

    fun getName(): String

    fun setName(name: String, context: IRequestContext)
    fun delete(context: IRequestContext)

}