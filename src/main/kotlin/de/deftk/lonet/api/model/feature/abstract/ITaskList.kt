package de.deftk.lonet.api.model.feature.abstract

import de.deftk.lonet.api.model.feature.Task
import java.util.*

interface ITaskList {

    fun getTasks(): List<Task>
    fun addTask(title: String, completed: Boolean? = null, description: String? = null, dueDate: Date? = null, startDate: Date? = null): Task

}