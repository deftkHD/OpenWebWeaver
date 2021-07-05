package de.deftk.openww.api.model.feature.tasks

import de.deftk.openww.api.model.IRequestContext

interface ITaskList {

    suspend fun getTasks(context: IRequestContext): List<ITask>
    suspend fun addTask(task: ITask, context: IRequestContext): ITask
    suspend fun addTask(title: String, completed: Boolean? = null, description: String? = null, dueDate: Long? = null, startDate: Long? = null, context: IRequestContext): ITask

}