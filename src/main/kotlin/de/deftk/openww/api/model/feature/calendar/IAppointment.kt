package de.deftk.openww.api.model.feature.calendar

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.IModifiable
import java.util.*

interface IAppointment : IModifiable {

    val id: String

    val title: String
    val description: String?
    val endDate: Date?
    val endDateIso: String?
    val tzid: String?
    val location: String?
    val rrule: String?
    val startDate: Date?
    val startDateIso: String?
    val uid: String?

    suspend fun setTitle(title: String, context: IRequestContext)
    suspend fun setDescription(description: String, context: IRequestContext)
    suspend fun setEndDate(endDate: Date, context: IRequestContext)
    suspend fun setEndDateIso(endDateIso: String, context: IRequestContext)
    suspend fun setLocation(location: String, context: IRequestContext)
    suspend fun setRrule(rrule: String, context: IRequestContext)
    suspend fun setStartDate(startDate: Date, context: IRequestContext)
    suspend fun setStartDateIso(startDateIso: String, context: IRequestContext)
    suspend fun setUid(uid: String, context: IRequestContext)

    suspend fun edit(
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
    )

    suspend fun delete(context: IRequestContext)

}