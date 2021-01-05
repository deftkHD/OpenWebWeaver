package de.deftk.lonet.api.model

import de.deftk.lonet.api.model.feature.calendar.ICalendar
import de.deftk.lonet.api.model.feature.contacts.IContactHolder
import de.deftk.lonet.api.model.feature.filestorage.IFileStorage
import de.deftk.lonet.api.model.feature.tasks.ITaskList

interface IOperatingScope: IScope, IFileStorage, ITaskList, IContactHolder, ICalendar {

    val baseRights: List<Permission>
    val effectiveRights: List<Permission>

    fun getRequestContext(apiContext: IApiContext): IRequestContext

}