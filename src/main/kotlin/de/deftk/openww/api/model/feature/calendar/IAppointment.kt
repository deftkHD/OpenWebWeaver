package de.deftk.openww.api.model.feature.calendar

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.IModifiable
import java.util.*

interface IAppointment : IModifiable {

    val id: String

    fun getTitle(): String
    fun getDescription(): String?
    fun getEndDate(): Date?
    fun getEndDateIso(): String?
    fun getTzid(): String?
    fun getLocation(): String?
    fun getRrule(): String?
    fun getStartDate(): Date?
    fun getStartDateIso(): String?
    fun getUid(): String?

    fun setTitle(title: String, context: IRequestContext)
    fun setDescription(description: String, context: IRequestContext)
    fun setEndDate(endDate: Date, context: IRequestContext)
    fun setEndDateIso(endDateIso: String, context: IRequestContext)
    fun setLocation(location: String, context: IRequestContext)
    fun setRrule(rrule: String, context: IRequestContext)
    fun setStartDate(startDate: Date, context: IRequestContext)
    fun setStartDateIso(startDateIso: String, context: IRequestContext)
    fun setUid(uid: String, context: IRequestContext)

    fun edit(
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

    fun delete(context: IRequestContext)

}