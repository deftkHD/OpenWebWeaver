package de.deftk.lonet.api.implementation.feature.calendar

import de.deftk.lonet.api.LoNetClient
import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.Modification
import de.deftk.lonet.api.model.feature.calendar.IAppointment
import de.deftk.lonet.api.request.OperatingScopeApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import de.deftk.lonet.api.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement
import java.util.*

@Serializable
class Appointment(
    override val id: String,
    private var uid: String? = null,
    private var title: String,
    private var description: String? = null,
    @SerialName("start_date")
    @Serializable(with = DateSerializer::class)
    private var startDate: Date? = null,
    @SerialName("start_date_iso")
    private var startDateIso: String? = null,
    @SerialName("end_date")
    @Serializable(with = DateSerializer::class)
    private var endDate: Date? = null,
    @SerialName("end_date_iso")
    private var endDateIso: String? = null,
    private var tzid: String? = null,
    private var location: String? = null,
    private var rrule: String? = null,
    override val created: Modification,
    private var modified: Modification
): IAppointment {

    var deleted = false
        private set

    override fun getModified(): Modification = modified
    override fun getTitle(): String = title
    override fun getDescription(): String? = description
    override fun getEndDate(): Date? = endDate
    override fun getEndDateIso(): String? = endDateIso
    override fun getTzid(): String? = tzid
    override fun getLocation(): String? = location
    override fun getRrule(): String? = rrule
    override fun getStartDate(): Date? = startDate
    override fun getStartDateIso(): String? = startDateIso
    override fun getUid(): String? = uid

    override fun setTitle(title: String, context: IRequestContext) = edit(title = title, context = context)
    override fun setDescription(description: String, context: IRequestContext) = edit(description = description, context = context)
    override fun setEndDate(endDate: Date, context: IRequestContext) = edit(endDate = endDate, context = context)
    override fun setEndDateIso(endDateIso: String, context: IRequestContext) = edit(endDateIso = endDateIso, context = context)
    override fun setLocation(location: String, context: IRequestContext) = edit(location = location, context = context)
    override fun setRrule(rrule: String, context: IRequestContext) = edit(rrule = rrule, context = context)
    override fun setStartDate(startDate: Date, context: IRequestContext) = edit(startDate = startDate, context = context)
    override fun setStartDateIso(startDateIso: String, context: IRequestContext) = edit(startDateIso = startDateIso, context = context)
    override fun setUid(uid: String, context: IRequestContext) = edit(uid = uid, context = context)

    override fun edit(title: String?, description: String?, endDate: Date?, endDateIso: String?, location: String?, rrule: String?, startDate: Date?, startDateIso: String?, uid: String?, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        val id = request.addSetAppointmentRequest(id, title, description, endDate?.time, endDateIso, location, rrule, startDate?.time, startDateIso)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(LoNetClient.json.decodeFromJsonElement(subResponse["entry"]!!))
    }

    override fun delete(context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        request.addDeleteAppointmentRequest(id)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
        deleted = true
    }

    private fun readFrom(appointment: Appointment) {
        uid = appointment.uid
        title = appointment.title
        description = appointment.description
        startDate = appointment.startDate
        startDateIso = appointment.startDateIso
        endDate = appointment.endDate
        endDateIso = appointment.endDateIso
        tzid = appointment.tzid
        location = appointment.location
        rrule = appointment.rrule
        modified = appointment.modified
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Appointment

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Appointment(title='$title')"
    }

}