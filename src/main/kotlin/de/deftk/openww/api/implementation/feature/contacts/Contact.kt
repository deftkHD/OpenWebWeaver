package de.deftk.openww.api.implementation.feature.contacts

import de.deftk.openww.api.WebWeaverClient
import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.Modification
import de.deftk.openww.api.model.feature.contacts.Gender
import de.deftk.openww.api.model.feature.contacts.IContact
import de.deftk.openww.api.request.OperatingScopeApiRequest
import de.deftk.openww.api.response.ResponseUtil
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement

@Serializable
class Contact(
    override val id: Int,
    @SerialName("birthday")
    private val _birthday: String? = null,
    @SerialName("businesscity")
    private val _businessCity: String? = null,
    @SerialName("businesscoords")
    private val _businessCoords: String? = null,
    @SerialName("businesscountry")
    private val _businessCountry: String? = null,
    @SerialName("businessfax")
    private val _businessFax: String? = null,
    @SerialName("businessphone")
    private val _businessPhone: String? = null,
    @SerialName("businesspostalcode")
    private val _businessPostalCode: String? = null,
    @SerialName("businessstate")
    private val _businessState: String? = null,
    @SerialName("businessstreet")
    private val _businessStreet: String? = null,
    @SerialName("businessstreet2")
    private val _businessStreet2: String? = null,
    @SerialName("businessstreet3")
    private val _businessStreet3: String? = null,
    @SerialName("categories")
    private val _categories: String? = null,
    @SerialName("company")
    private val _company: String? = null,
    @SerialName("companytype")
    private val _companyType: String? = null,
    @SerialName("email2address")
    private val _email2Address: String? = null,
    @SerialName("email3address")
    private val _email3Address: String? = null,
    @SerialName("emailaddress")
    private val _emailAddress: String? = null,
    @SerialName("firstname")
    private val _firstName: String? = null,
    @SerialName("fullname")
    private val _fullName: String? = null,
    @SerialName("gender")
    private val _gender: Gender? = null,
    @SerialName("hobby")
    private val _hobby: String? = null,
    @SerialName("homecity")
    private val _homeCity: String? = null,
    @SerialName("homecoords")
    private val _homeCoords: String? = null,
    @SerialName("homecountry")
    private val _homeCountry: String? = null,
    @SerialName("homefax")
    private val _homeFax: String? = null,
    @SerialName("homephone")
    private val _homePhone: String? = null,
    @SerialName("homepostalcode")
    private val _homePostalCode: String? = null,
    @SerialName("homestate")
    private val _homeState: String? = null,
    @SerialName("homestreet")
    private val _homeStreet: String? = null,
    @SerialName("homestreet2")
    private val _homeStreet2: String? = null,
    @SerialName("homestreet3")
    private val _homeStreet3: String? = null,
    @SerialName("jobtitle")
    private val _jobTitle: String? = null,
    @SerialName("jobtitle2")
    private val _jobTitle2: String? = null,
    @SerialName("lastname")
    private val _lastName: String? = null,
    @SerialName("middlename")
    private val _middleName: String? = null,
    @SerialName("mobilephone")
    private val _mobilePhone: String? = null,
    @SerialName("nickname")
    private val _nickName: String? = null,
    @SerialName("notes")
    private val _notes: String? = null,
    @SerialName("subjects")
    private val _subjects: String? = null,
    @SerialName("suffix")
    private val _suffix: String? = null,
    @SerialName("title")
    private val _title: String? = null,
    @SerialName("uid")
    private val _uid: String? = null,
    @SerialName("webpage")
    private val _webPage: String? = null,
    override val created: Modification,
    @SerialName("modified")
    private val _modified: Modification
) : IContact {

    var deleted = false
        private set

    @SerialName("_birthday")
    override var birthday: String? = _birthday
        private set
    @SerialName("_businessCity")
    override var businessCity: String? = _businessCity
        private set
    @SerialName("_businessCoords")
    override var businessCoords: String? = _businessCoords
        private set
    @SerialName("_businessCounty")
    override var businessCountry: String? = _businessCountry
        private set
    @SerialName("_businessFax")
    override var businessFax: String? = _businessFax
        private set
    @SerialName("_businessPhone")
    override var businessPhone: String? = _businessPhone
        private set
    @SerialName("_businessPostalCode")
    override var businessPostalCode: String? = _businessPostalCode
        private set
    @SerialName("_businessState")
    override var businessState: String? = _businessState
        private set
    @SerialName("_businessStreet")
    override var businessStreet: String? = _businessStreet
        private set
    @SerialName("_businessStreet2")
    override var businessStreet2: String? = _businessStreet2
        private set
    @SerialName("_businessStreet3")
    override var businessStreet3: String? = _businessStreet3
        private set
    @SerialName("_categories")
    override var categories: String? = _categories
        private set
    @SerialName("_company")
    override var company: String? = _company
        private set
    @SerialName("_companyType")
    override var companyType: String? = _companyType
        private set
    @SerialName("_email2Address")
    override var email2Address: String? = _email2Address
        private set
    @SerialName("_email3Address")
    override var email3Address: String? = _email3Address
        private set
    @SerialName("_emailAddress")
    override var emailAddress: String? = _emailAddress
        private set
    @SerialName("_firstName")
    override var firstName: String? = _firstName
        private set
    @SerialName("_fullName")
    override var fullName: String? = _fullName
        private set
    @SerialName("_gender")
    override var gender: Gender? = _gender
        private set
    @SerialName("_hobby")
    override var hobby: String? = _hobby
        private set
    @SerialName("_homeCity")
    override var homeCity: String? = _homeCity
        private set
    @SerialName("_homeCoords")
    override var homeCoords: String? = _homeCoords
        private set
    @SerialName("_homeCountry")
    override var homeCountry: String? = _homeCountry
        private set
    @SerialName("_homeFax")
    override var homeFax: String? = _homeFax
        private set
    @SerialName("_homePhone")
    override var homePhone: String? = _homePhone
        private set
    @SerialName("_homePostalCode")
    override var homePostalCode: String? = _homePostalCode
        private set
    @SerialName("_homeState")
    override var homeState: String? = _homeState
        private set
    @SerialName("_homeStreet")
    override var homeStreet: String? = _homeStreet
        private set
    @SerialName("_homeStreet2")
    override var homeStreet2: String? = _homeStreet2
        private set
    @SerialName("_homeStreet3")
    override var homeStreet3: String? = _homeStreet3
        private set
    @SerialName("_jobTitle")
    override var jobTitle: String? = _jobTitle
        private set
    @SerialName("_jobTitle2")
    override var jobTitle2: String? = _jobTitle2
        private set
    @SerialName("_lastName")
    override var lastName: String? = _lastName
        private set
    @SerialName("_middleName")
    override var middleName: String? = _middleName
        private set
    @SerialName("_mobilePhone")
    override var mobilePhone: String? = _mobilePhone
        private set
    @SerialName("_nickName")
    override var nickName: String? = _nickName
        private set
    @SerialName("_notes")
    override var notes: String? = _notes
        private set
    @SerialName("_subjects")
    override var subjects: String? = _subjects
        private set
    @SerialName("_suffix")
    override var suffix: String? = _suffix
        private set
    @SerialName("_title")
    override var title: String? = _title
        private set
    @SerialName("_uid")
    override var uid: String? = _uid
        private set
    @SerialName("_webPage")
    override var webPage: String? = _webPage
        private set

    @SerialName("_modified")
    override var modified: Modification = _modified
        private set

    override suspend fun edit(
        birthday: String?,
        businessCity: String?,
        businessCoords: String?,
        businessCountry: String?,
        businessFax: String?,
        businessPhone: String?,
        businessPostalCode: String?,
        businessState: String?,
        businessStreet: String?,
        businessStreet2: String?,
        businessStreet3: String?,
        categories: String?,
        company: String?,
        companyType: String?,
        email2Address: String?,
        email3Address: String?,
        emailAddress: String?,
        firstName: String?,
        fullName: String?,
        gender: Gender?,
        hobby: String?,
        homeCity: String?,
        homeCoords: String?,
        homeCountry: String?,
        homeFax: String?,
        homePhone: String?,
        homePostalCode: String?,
        homeState: String?,
        homeStreet: String?,
        homeStreet2: String?,
        homeStreet3: String?,
        jobTitle: String?,
        jobTitle2: String?,
        lastName: String?,
        middleName: String?,
        mobilePhone: String?,
        nickName: String?,
        notes: String?,
        subjects: String?,
        suffix: String?,
        title: String?,
        uid: String?,
        webPage: String?,
        context: IRequestContext
    ) {
        val request = OperatingScopeApiRequest(context)
        val id = request.addSetContactRequest(
            id.toString(),
            birthday,
            businessCity,
            businessCoords,
            businessCountry,
            businessFax,
            businessPhone,
            businessPostalCode,
            businessState,
            businessStreet,
            businessStreet2,
            businessStreet3,
            categories,
            company,
            companyType,
            email2Address,
            email3Address,
            emailAddress,
            firstName,
            fullName,
            gender,
            hobby,
            homeCity,
            homeCoords,
            homeCountry,
            homeFax,
            homePhone,
            homePostalCode,
            homeState,
            homeStreet,
            homeStreet2,
            homeStreet3,
            jobTitle,
            jobTitle2,
            lastName,
            middleName,
            mobilePhone,
            nickName,
            notes,
            subjects,
            suffix,
            title,
            uid,
            webPage
        )[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse["entry"]!!))
    }

    override suspend fun delete(context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        request.addDeleteContactRequest(id.toString())
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
        deleted = true
    }

    private fun readFrom(contact: Contact) {
        birthday = contact.birthday
        businessCity = contact.businessCity
        businessCoords = contact.businessCoords
        businessCountry = contact.businessCountry
        businessFax = contact.businessFax
        businessPhone = contact.businessPhone
        businessPostalCode = contact.businessPostalCode
        businessState = contact.businessState
        businessStreet = contact.businessStreet
        businessStreet2 = contact.businessStreet2
        businessStreet3 = contact.businessStreet3
        categories = contact.categories
        company = contact.company
        companyType = contact.companyType
        email2Address = contact.email2Address
        email3Address = contact.email3Address
        emailAddress = contact.emailAddress
        firstName = contact.firstName
        fullName = contact.fullName
        gender = contact.gender
        hobby = contact.hobby
        homeCity = contact.homeCity
        homeCoords = contact.homeCoords
        homeCountry = contact.homeCountry
        homeFax = contact.homeFax
        homePhone = contact.homePhone
        homePostalCode = contact.homePostalCode
        homeState = contact.homeState
        homeStreet = contact.homeStreet
        homeStreet2 = contact.homeStreet2
        homeStreet3 = contact.homeStreet3
        jobTitle = contact.jobTitle
        jobTitle2 = contact.jobTitle2
        lastName = contact.lastName
        middleName = contact.middleName
        mobilePhone = contact.mobilePhone
        nickName = contact.nickName
        notes = contact.notes
        subjects = contact.subjects
        suffix = contact.suffix
        title = contact.title
        uid = contact.uid
        webPage = contact.webPage
    }

    override fun toString(): String {
        return "Contact(firstName=$firstName, fullName=$fullName, lastName=$lastName, middleName=$middleName)"
    }

}