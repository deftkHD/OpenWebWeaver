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

    override suspend fun setTitle(title: String, context: IRequestContext) = edit(title, description, endDate, endDateIso, location, rrule, startDate, startDateIso, uid, context)
    override suspend fun setDescription(description: String, context: IRequestContext) = edit(title, description, endDate, endDateIso, location, rrule, startDate, startDateIso, uid, context)
    override suspend fun setEndDate(endDate: Date, context: IRequestContext) = edit(title, description, endDate, endDateIso, location, rrule, startDate, startDateIso, uid, context)
    override suspend fun setEndDateIso(endDateIso: String, context: IRequestContext) = edit(title, description, endDate, endDateIso, location, rrule, startDate, startDateIso, uid, context)
    override suspend fun setLocation(location: String, context: IRequestContext) = edit(title, description, endDate, endDateIso, location, rrule, startDate, startDateIso, uid, context)
    override suspend fun setRrule(rrule: String, context: IRequestContext) = edit(title, description, endDate, endDateIso, location, rrule, startDate, startDateIso, uid, context)
    override suspend fun setStartDate(startDate: Date, context: IRequestContext) = edit(title, description, endDate, endDateIso, location, rrule, startDate, startDateIso, uid, context)
    override suspend fun setStartDateIso(startDateIso: String, context: IRequestContext) = edit(title, description, endDate, endDateIso, location, rrule, startDate, startDateIso, uid, context)
    override suspend fun setUid(uid: String, context: IRequestContext) = edit(title, description, endDate, endDateIso, location, rrule, startDate, startDateIso, uid, context)

    override suspend fun edit(title: String, description: String?, endDate: Date?, endDateIso: String?, location: String?, rrule: String?, startDate: Date?, startDateIso: String?, uid: String?, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        val id = request.addSetAppointmentRequest(id, title, description, endDate?.time, endDateIso, location, rrule, startDate?.time, startDateIso)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse["entry"]!!))
    }

    override suspend fun delete(context: IRequestContext) {
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

    override fun toString(): String {
        return "Appointment(title='$title')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Appointment) return false

        if (id != other.id) return false
        if (_uid != other._uid) return false
        if (_title != other._title) return false
        if (_description != other._description) return false
        if (_startDate != other._startDate) return false
        if (_startDateIso != other._startDateIso) return false
        if (_endDate != other._endDate) return false
        if (_endDateIso != other._endDateIso) return false
        if (_tzid != other._tzid) return false
        if (_location != other._location) return false
        if (_rrule != other._rrule) return false
        if (created != other.created) return false
        if (_modified != other._modified) return false
        if (deleted != other.deleted) return false
        if (modified != other.modified) return false
        if (title != other.title) return false
        if (description != other.description) return false
        if (endDate != other.endDate) return false
        if (endDateIso != other.endDateIso) return false
        if (tzid != other.tzid) return false
        if (location != other.location) return false
        if (rrule != other.rrule) return false
        if (startDate != other.startDate) return false
        if (startDateIso != other.startDateIso) return false
        if (uid != other.uid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (_uid?.hashCode() ?: 0)
        result = 31 * result + _title.hashCode()
        result = 31 * result + (_description?.hashCode() ?: 0)
        result = 31 * result + (_startDate?.hashCode() ?: 0)
        result = 31 * result + (_startDateIso?.hashCode() ?: 0)
        result = 31 * result + (_endDate?.hashCode() ?: 0)
        result = 31 * result + (_endDateIso?.hashCode() ?: 0)
        result = 31 * result + (_tzid?.hashCode() ?: 0)
        result = 31 * result + (_location?.hashCode() ?: 0)
        result = 31 * result + (_rrule?.hashCode() ?: 0)
        result = 31 * result + created.hashCode()
        result = 31 * result + _modified.hashCode()
        result = 31 * result + deleted.hashCode()
        result = 31 * result + modified.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (endDate?.hashCode() ?: 0)
        result = 31 * result + (endDateIso?.hashCode() ?: 0)
        result = 31 * result + (tzid?.hashCode() ?: 0)
        result = 31 * result + (location?.hashCode() ?: 0)
        result = 31 * result + (rrule?.hashCode() ?: 0)
        result = 31 * result + (startDate?.hashCode() ?: 0)
        result = 31 * result + (startDateIso?.hashCode() ?: 0)
        result = 31 * result + (uid?.hashCode() ?: 0)
        return result
    }

}