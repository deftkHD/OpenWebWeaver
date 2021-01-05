package de.deftk.lonet.api.model.feature.tasks

import de.deftk.lonet.api.model.IRequestContext

interface ITaskList {

    fun getTasks(context: IRequestContext): List<ITask>
    fun addTask(task: ITask, context: IRequestContext): ITask
    fun addTask(title: String, completed: Boolean? = null, description: String? = null, dueDate: Long? = null, startDate: Long? = null, context: IRequestContext): ITask

}