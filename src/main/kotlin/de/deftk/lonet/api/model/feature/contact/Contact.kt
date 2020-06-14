package de.deftk.lonet.api.model.feature.contact

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.abstract.AbstractOperator
import de.deftk.lonet.api.model.abstract.IManageable
import de.deftk.lonet.api.request.OperatorApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import java.util.*

class Contact(
        val id: Int,
        var uid: String?,
        var categories: String?,
        var firstName: String?,
        var lastName: String?,
        var homeStreet: String?,
        var homeStreet2: String?,
        var homePostalCode: String?,
        var homeCity: String?,
        var homeState: String?,
        var homeCountry: String?,
        var homeCoords: String?,
        var homePhone: String?,
        var homeFax: String?,
        var mobilePhone: String?,
        var birthday: String?,
        var emailAddress: String?,
        var gender: Gender?,
        var hobbies: String?,
        var notes: String?,
        var website: String?,
        var company: String?,
        var companyType: String?,
        var jobTitle: String?,
        var creationDate: Date,
        var creationMember: IManageable,
        var modificationDate: Date,
        var modificationMember: IManageable,
        val operator: AbstractOperator
) {

    companion object {
        fun fromJson(jsonObject: JsonObject, operator: AbstractOperator): Contact {
            val createdObject = jsonObject.get("created").asJsonObject
            val modifiedObject = jsonObject.get("modified").asJsonObject
            val contact = Contact(
                    jsonObject.get("id").asInt,
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
                    Date(createdObject.get("date").asLong * 1000),
                    operator.getContext().getOrCreateManageable(createdObject.get("user").asJsonObject),
                    Date(modifiedObject.get("date").asLong * 1000),
                    operator.getContext().getOrCreateManageable(modifiedObject.get("user").asJsonObject),
                    operator)
            contact.readFrom(jsonObject)
            return contact
        }
    }

    fun edit(categories: String? = null, firstName: String? = null, lastName: String? = null, homeStreet: String? = null, homeStreet2: String? = null, homePostalCode: String? = null, homeCity: String? = null, homeState: String? = null, homeCountry: String? = null, homeCoords: String? = null, homePhone: String? = null, homeFax: String? = null, mobilePhone: String? = null, birthday: String? = null, email: String? = null, gender: Gender? = null, hobby: String? = null, notes: String? = null, website: String? = null, company: String? = null, companyType: String? = null, jobTitle: String? = null) {
        val request = OperatorApiRequest(operator)
        val id = request.addSetContactRequest(id.toString(), categories, firstName, lastName, homeStreet, homeStreet2, homePostalCode, homeCity, homeState, homeCountry, homeCoords, homePhone, homeFax, mobilePhone, birthday, email, gender, hobby, notes, website, company, companyType, jobTitle)[1]
        val response = request.fireRequest(true)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(subResponse.get("entry").asJsonObject)
    }

    private fun readFrom(jsonObject: JsonObject) {
        val createdObject = jsonObject.get("created").asJsonObject
        val modifiedObject = jsonObject.get("modified").asJsonObject

        uid = jsonObject.get("uid")?.asString
        categories = jsonObject.get("categories")?.asString
        firstName = jsonObject.get("firstname")?.asString
        lastName = jsonObject.get("lastname")?.asString
        homeStreet = jsonObject.get("homestreet")?.asString
        homeStreet2 = jsonObject.get("homestreet2")?.asString
        homePostalCode = jsonObject.get("homepostalcode")?.asString
        homeCity = jsonObject.get("homecity")?.asString
        homeState = jsonObject.get("homestate")?.asString
        homeCountry = jsonObject.get("homecountry")?.asString
        homeCoords = jsonObject.get("homecoords")?.asString
        homePhone = jsonObject.get("homephone")?.asString
        homeFax = jsonObject.get("homefax")?.asString
        mobilePhone = jsonObject.get("mobilephone")?.asString
        birthday = jsonObject.get("birthday")?.asString
        emailAddress = jsonObject.get("emailaddress")?.asString
        gender = if (jsonObject.has("gender")) Gender.getById(jsonObject.get("gender").asString.toInt()) else null
        hobbies = jsonObject.get("hobby")?.asString
        notes = jsonObject.get("notes")?.asString
        website = jsonObject.get("webpage")?.asString
        company = jsonObject.get("company")?.asString
        companyType = jsonObject.get("companytype")?.asString
        jobTitle = jsonObject.get("jobtitle")?.asString
        creationDate = Date(createdObject.get("date").asLong * 1000)
        creationMember = operator.getContext().getOrCreateManageable(createdObject.get("user").asJsonObject)
        modificationDate = Date(modifiedObject.get("date").asLong * 1000)
        modificationMember = operator.getContext().getOrCreateManageable(modifiedObject.get("user").asJsonObject)
    }

    fun delete() {
        val request = OperatorApiRequest(operator)
        request.addDeleteContactRequest(id.toString())
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

}