package de.deftk.openww.api.implementation.feature.calendar

import de.deftk.openww.api.WebWeaverClient
import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.Modification
import de.deftk.openww.api.model.feature.calendar.IAppointment
import de.deftk.openww.api.request.OperatingScopeApiRequest
import de.deftk.openww.api.response.ResponseUtil
import de.deftk.openww.api.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement
import java.util.*

@Serializable
class Appointment(
    override val id: String,
    @SerialName("uid")
    private var _uid: String? = null,
    @SerialName("title")
    private var _title: String,
    @SerialName("description")
    private var _description: String? = null,
    @SerialName("start_date")
    @Serializable(with = DateSerializer::class)
    private var _startDate: Date? = null,
    @SerialName("start_date_iso")
    private var _startDateIso: String? = null,
    @SerialName("end_date")
    @Serializable(with = DateSerializer::class)
    private var _endDate: Date? = null,
    @SerialName("end_date_iso")
    private var _endDateIso: String? = null,
    @SerialName("tzid")
    private var _tzid: String? = null,
    @SerialName("location")
    private var _location: String? = null,
    @SerialName("rrule")
    private var _rrule: String? = null,
    override val created: Modification,
    @SerialName("modified")
    private var _modified: Modification
): IAppointment {

    var deleted = false
        private set

    @SerialName("_modified")
    override var modified: Modification = _modified
        private set

    @SerialName("_title")
    override var title: String = _title
        private set

    @SerialName("_description")
    override var description: String? = _description
        private set

    @SerialName("_end_date")
    @Serializable(with = DateSerializer::class)
    override var endDate: Date? = _endDate
        private set

    @SerialName("_end_date_iso")
    override var endDateIso: String? = _endDateIso
        private set

    @SerialName("_tzid")
    override var tzid: String? = _tzid
        private set

    @SerialName("_location")
    override var location: String? = _location
        private set

    @SerialName("_rrule")
    override var rrule: String? = _rrule
        private set

    @SerialName("_start_date")
    @Serializable(with = DateSerializer::class)
    override var startDate: Date? = _startDate
        private set

    @SerialName("_start_date_iso")
    override var startDateIso: String? = _startDateIso
        private set

    @SerialName("_uid")
    override var uid: String? = _uid
        private set

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
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse["entry"]!!))
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