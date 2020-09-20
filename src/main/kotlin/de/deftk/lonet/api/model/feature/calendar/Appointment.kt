package de.deftk.lonet.api.model.feature.calendar

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.RemoteManageable
import de.deftk.lonet.api.model.abstract.AbstractOperator
import de.deftk.lonet.api.model.abstract.IManageable
import de.deftk.lonet.api.request.OperatorApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import java.util.*

class Appointment(val id: String, var uid: String?, var title: String, var description: String?, var endDate: Date?, var endDateIso: String?, var location: String?, var tzid: String?, var rrule: String?, var startDate: Date?, var startDateIso: String?, var creationDate: Date?, var creationMember: IManageable?, var modificationDate: Date?, var modificationMember: IManageable?, val operator: AbstractOperator) {

    companion object {
        fun fromJson(jsonObject: JsonObject, operator: AbstractOperator): Appointment {
            val appointment = Appointment(
                    jsonObject.get("id").asString,
                    null,
                    "",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    operator
            )
            appointment.readFrom(jsonObject)
            return appointment
        }
    }

    fun edit(title: String? = null, description: String? = null, endDate: Date? = null, endDateIso: String? = null, location: String? = null, rrule: String? = null, startDate: Date? = null, startDateIso: String? = null) {
        val request = OperatorApiRequest(operator)
        val id = request.addSetAppointmentRequest(id, title, description, endDate, endDateIso, location, rrule, startDate, startDateIso)[1]
        val response = request.fireRequest(true)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(subResponse.get("entry").asJsonObject)
    }

    fun delete() {
        val request = OperatorApiRequest(operator)
        request.addDeleteAppointmentRequest(id)
        val response = request.fireRequest(true)
        ResponseUtil.checkSuccess(response.toJson())
    }

    private fun readFrom(jsonObject: JsonObject) {
        uid = jsonObject.get("uid")?.asString
        title = jsonObject.get("title").asString
        description = jsonObject.get("description")?.asString
        endDate = if (jsonObject.has("end_date")) Date(jsonObject.get("end_date").asInt * 1000L) else null
        endDateIso = jsonObject.get("end_date_iso")?.asString
        location = jsonObject.get("location")?.asString
        tzid = jsonObject.get("tzid")?.asString
        rrule = jsonObject.get("rrule")?.asString
        startDate = if (jsonObject.has("start_date")) Date(jsonObject.get("start_date").asInt * 1000L) else null
        startDateIso = jsonObject.get("start_date_iso")?.asString

        val createdObject = jsonObject.get("created").asJsonObject
        creationDate = Date(createdObject.get("date").asLong * 1000)
        creationMember = operator.getContext().getOrCreateManageable(createdObject.get("user").asJsonObject)

        val modifiedObject = jsonObject.get("modified").asJsonObject
        modificationDate = Date(modifiedObject.get("date").asLong * 1000)
        modificationMember = operator.getContext().getOrCreateManageable(createdObject.get("user").asJsonObject)
    }

}