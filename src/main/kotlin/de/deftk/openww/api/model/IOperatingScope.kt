package de.deftk.openww.api.model

import de.deftk.openww.api.model.feature.calendar.ICalendar
import de.deftk.openww.api.model.feature.contacts.IContactHolder
import de.deftk.openww.api.model.feature.filestorage.IFileStorage
import de.deftk.openww.api.model.feature.messenger.IQuickMessageReceiver
import de.deftk.openww.api.model.feature.tasks.ITaskList

interface IOperatingScope: IScope, IFileStorage, ITaskList, IContactHolder, ICalendar, IQuickMessageReceiver {

    val baseRights: List<Permission>
    val effectiveRights: List<Permission>

    fun getRequestContext(apiContext: IApiContext): IRequestContext

}