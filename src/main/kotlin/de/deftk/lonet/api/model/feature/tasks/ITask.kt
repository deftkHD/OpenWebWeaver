package de.deftk.lonet.api.model.feature.tasks

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.IModifiable
import java.util.*

interface ITask: IModifiable {

    val id: String

    fun getTitle(): String
    fun getDescription(): String?
    fun getStartDate(): Date?
    fun getEndDate(): Date?
    fun isCompleted(): Boolean

    fun setTitle(title: String, context: IRequestContext)
    fun setDescription(description: String, context: IRequestContext)
    fun setStartDate(startDate: Date, context: IRequestContext)
    fun setEndDate(endDate: Date, context: IRequestContext)
    fun setCompleted(completed: Boolean, context: IRequestContext)

    fun edit(title: String? = null, description: String? = null, completed: Boolean? = null, startDate: Date? = null, endDate: Date? = null, context: IRequestContext)
    fun delete(context: IRequestContext)

}