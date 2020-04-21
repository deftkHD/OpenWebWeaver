package de.deftk.lonet.api.model.feature.abstract

import de.deftk.lonet.api.model.User
import de.deftk.lonet.api.model.feature.Task

interface ITaskList {

    fun getTasks(user: User, overwriteCache: Boolean = false): List<Task>

}