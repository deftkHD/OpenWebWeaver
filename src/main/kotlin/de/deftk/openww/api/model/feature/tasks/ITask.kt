package de.deftk.openww.api.model.feature.tasks

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.IModifiable
import java.util.*

interface ITask: IModifiable {

    val id: String

    val title: String
    val description: String?
    val startDate: Date?
    val dueDate: Date?
    val completed: Boolean

    fun setTitle(title: String, context: IRequestContext)
    fun setDescription(description: String, context: IRequestContext)
    fun setStartDate(startDate: Date?, context: IRequestContext)
    fun setDueDate(dueDate: Date?, context: IRequestContext)
    fun setCompleted(completed: Boolean, context: IRequestContext)

    fun edit(title: String, description: String? = null, completed: Boolean? = null, startDate: Date? = null, dueDate: Date? = null, context: IRequestContext)
    fun delete(context: IRequestContext)

}