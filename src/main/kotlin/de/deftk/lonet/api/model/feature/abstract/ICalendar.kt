package de.deftk.lonet.api.model.feature.abstract

import de.deftk.lonet.api.model.feature.calendar.Appointment
import java.util.*

interface ICalendar {

    fun getAppointments(overwriteCache: Boolean = false): List<Appointment>
    fun addAppointment(title: String, description: String? = null, endDate: Date? = null, endDateIso: String? = null, location: String? = null, rrule: String? = null, startDate: Date? = null, startDateIso: String? = null): Appointment

}