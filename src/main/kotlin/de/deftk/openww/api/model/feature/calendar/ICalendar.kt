package de.deftk.openww.api.model.feature.calendar

import de.deftk.openww.api.model.IRequestContext
import java.util.*

interface ICalendar {

    fun getAppointments(context: IRequestContext): List<IAppointment>
    fun addAppointment(appointment: IAppointment, context: IRequestContext): IAppointment
    fun addAppointment(
        title: String,
        description: String? = null,
        endDate: Date? = null,
        endDateIso: String? = null,
        location: String? = null,
        rrule: String? = null,
        startDate: Date? = null,
        startDateIso: String? = null,
        uid: String? = null,
        context: IRequestContext
    ): IAppointment

}