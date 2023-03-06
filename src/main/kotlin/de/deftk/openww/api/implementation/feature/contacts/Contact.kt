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
        )
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Contact) return false

        if (id != other.id) return false
        if (_birthday != other._birthday) return false
        if (_businessCity != other._businessCity) return false
        if (_businessCoords != other._businessCoords) return false
        if (_businessCountry != other._businessCountry) return false
        if (_businessFax != other._businessFax) return false
        if (_businessPhone != other._businessPhone) return false
        if (_businessPostalCode != other._businessPostalCode) return false
        if (_businessState != other._businessState) return false
        if (_businessStreet != other._businessStreet) return false
        if (_businessStreet2 != other._businessStreet2) return false
        if (_businessStreet3 != other._businessStreet3) return false
        if (_categories != other._categories) return false
        if (_company != other._company) return false
        if (_companyType != other._companyType) return false
        if (_email2Address != other._email2Address) return false
        if (_email3Address != other._email3Address) return false
        if (_emailAddress != other._emailAddress) return false
        if (_firstName != other._firstName) return false
        if (_fullName != other._fullName) return false
        if (_gender != other._gender) return false
        if (_hobby != other._hobby) return false
        if (_homeCity != other._homeCity) return false
        if (_homeCoords != other._homeCoords) return false
        if (_homeCountry != other._homeCountry) return false
        if (_homeFax != other._homeFax) return false
        if (_homePhone != other._homePhone) return false
        if (_homePostalCode != other._homePostalCode) return false
        if (_homeState != other._homeState) return false
        if (_homeStreet != other._homeStreet) return false
        if (_homeStreet2 != other._homeStreet2) return false
        if (_homeStreet3 != other._homeStreet3) return false
        if (_jobTitle != other._jobTitle) return false
        if (_jobTitle2 != other._jobTitle2) return false
        if (_lastName != other._lastName) return false
        if (_middleName != other._middleName) return false
        if (_mobilePhone != other._mobilePhone) return false
        if (_nickName != other._nickName) return false
        if (_notes != other._notes) return false
        if (_subjects != other._subjects) return false
        if (_suffix != other._suffix) return false
        if (_title != other._title) return false
        if (_uid != other._uid) return false
        if (_webPage != other._webPage) return false
        if (created != other.created) return false
        if (_modified != other._modified) return false
        if (deleted != other.deleted) return false
        if (birthday != other.birthday) return false
        if (businessCity != other.businessCity) return false
        if (businessCoords != other.businessCoords) return false
        if (businessCountry != other.businessCountry) return false
        if (businessFax != other.businessFax) return false
        if (businessPhone != other.businessPhone) return false
        if (businessPostalCode != other.businessPostalCode) return false
        if (businessState != other.businessState) return false
        if (businessStreet != other.businessStreet) return false
        if (businessStreet2 != other.businessStreet2) return false
        if (businessStreet3 != other.businessStreet3) return false
        if (categories != other.categories) return false
        if (company != other.company) return false
        if (companyType != other.companyType) return false
        if (email2Address != other.email2Address) return false
        if (email3Address != other.email3Address) return false
        if (emailAddress != other.emailAddress) return false
        if (firstName != other.firstName) return false
        if (fullName != other.fullName) return false
        if (gender != other.gender) return false
        if (hobby != other.hobby) return false
        if (homeCity != other.homeCity) return false
        if (homeCoords != other.homeCoords) return false
        if (homeCountry != other.homeCountry) return false
        if (homeFax != other.homeFax) return false
        if (homePhone != other.homePhone) return false
        if (homePostalCode != other.homePostalCode) return false
        if (homeState != other.homeState) return false
        if (homeStreet != other.homeStreet) return false
        if (homeStreet2 != other.homeStreet2) return false
        if (homeStreet3 != other.homeStreet3) return false
        if (jobTitle != other.jobTitle) return false
        if (jobTitle2 != other.jobTitle2) return false
        if (lastName != other.lastName) return false
        if (middleName != other.middleName) return false
        if (mobilePhone != other.mobilePhone) return false
        if (nickName != other.nickName) return false
        if (notes != other.notes) return false
        if (subjects != other.subjects) return false
        if (suffix != other.suffix) return false
        if (title != other.title) return false
        if (uid != other.uid) return false
        if (webPage != other.webPage) return false
        if (modified != other.modified) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (_birthday?.hashCode() ?: 0)
        result = 31 * result + (_businessCity?.hashCode() ?: 0)
        result = 31 * result + (_businessCoords?.hashCode() ?: 0)
        result = 31 * result + (_businessCountry?.hashCode() ?: 0)
        result = 31 * result + (_businessFax?.hashCode() ?: 0)
        result = 31 * result + (_businessPhone?.hashCode() ?: 0)
        result = 31 * result + (_businessPostalCode?.hashCode() ?: 0)
        result = 31 * result + (_businessState?.hashCode() ?: 0)
        result = 31 * result + (_businessStreet?.hashCode() ?: 0)
        result = 31 * result + (_businessStreet2?.hashCode() ?: 0)
        result = 31 * result + (_businessStreet3?.hashCode() ?: 0)
        result = 31 * result + (_categories?.hashCode() ?: 0)
        result = 31 * result + (_company?.hashCode() ?: 0)
        result = 31 * result + (_companyType?.hashCode() ?: 0)
        result = 31 * result + (_email2Address?.hashCode() ?: 0)
        result = 31 * result + (_email3Address?.hashCode() ?: 0)
        result = 31 * result + (_emailAddress?.hashCode() ?: 0)
        result = 31 * result + (_firstName?.hashCode() ?: 0)
        result = 31 * result + (_fullName?.hashCode() ?: 0)
        result = 31 * result + (_gender?.hashCode() ?: 0)
        result = 31 * result + (_hobby?.hashCode() ?: 0)
        result = 31 * result + (_homeCity?.hashCode() ?: 0)
        result = 31 * result + (_homeCoords?.hashCode() ?: 0)
        result = 31 * result + (_homeCountry?.hashCode() ?: 0)
        result = 31 * result + (_homeFax?.hashCode() ?: 0)
        result = 31 * result + (_homePhone?.hashCode() ?: 0)
        result = 31 * result + (_homePostalCode?.hashCode() ?: 0)
        result = 31 * result + (_homeState?.hashCode() ?: 0)
        result = 31 * result + (_homeStreet?.hashCode() ?: 0)
        result = 31 * result + (_homeStreet2?.hashCode() ?: 0)
        result = 31 * result + (_homeStreet3?.hashCode() ?: 0)
        result = 31 * result + (_jobTitle?.hashCode() ?: 0)
        result = 31 * result + (_jobTitle2?.hashCode() ?: 0)
        result = 31 * result + (_lastName?.hashCode() ?: 0)
        result = 31 * result + (_middleName?.hashCode() ?: 0)
        result = 31 * result + (_mobilePhone?.hashCode() ?: 0)
        result = 31 * result + (_nickName?.hashCode() ?: 0)
        result = 31 * result + (_notes?.hashCode() ?: 0)
        result = 31 * result + (_subjects?.hashCode() ?: 0)
        result = 31 * result + (_suffix?.hashCode() ?: 0)
        result = 31 * result + (_title?.hashCode() ?: 0)
        result = 31 * result + (_uid?.hashCode() ?: 0)
        result = 31 * result + (_webPage?.hashCode() ?: 0)
        result = 31 * result + created.hashCode()
        result = 31 * result + _modified.hashCode()
        result = 31 * result + deleted.hashCode()
        result = 31 * result + (birthday?.hashCode() ?: 0)
        result = 31 * result + (businessCity?.hashCode() ?: 0)
        result = 31 * result + (businessCoords?.hashCode() ?: 0)
        result = 31 * result + (businessCountry?.hashCode() ?: 0)
        result = 31 * result + (businessFax?.hashCode() ?: 0)
        result = 31 * result + (businessPhone?.hashCode() ?: 0)
        result = 31 * result + (businessPostalCode?.hashCode() ?: 0)
        result = 31 * result + (businessState?.hashCode() ?: 0)
        result = 31 * result + (businessStreet?.hashCode() ?: 0)
        result = 31 * result + (businessStreet2?.hashCode() ?: 0)
        result = 31 * result + (businessStreet3?.hashCode() ?: 0)
        result = 31 * result + (categories?.hashCode() ?: 0)
        result = 31 * result + (company?.hashCode() ?: 0)
        result = 31 * result + (companyType?.hashCode() ?: 0)
        result = 31 * result + (email2Address?.hashCode() ?: 0)
        result = 31 * result + (email3Address?.hashCode() ?: 0)
        result = 31 * result + (emailAddress?.hashCode() ?: 0)
        result = 31 * result + (firstName?.hashCode() ?: 0)
        result = 31 * result + (fullName?.hashCode() ?: 0)
        result = 31 * result + (gender?.hashCode() ?: 0)
        result = 31 * result + (hobby?.hashCode() ?: 0)
        result = 31 * result + (homeCity?.hashCode() ?: 0)
        result = 31 * result + (homeCoords?.hashCode() ?: 0)
        result = 31 * result + (homeCountry?.hashCode() ?: 0)
        result = 31 * result + (homeFax?.hashCode() ?: 0)
        result = 31 * result + (homePhone?.hashCode() ?: 0)
        result = 31 * result + (homePostalCode?.hashCode() ?: 0)
        result = 31 * result + (homeState?.hashCode() ?: 0)
        result = 31 * result + (homeStreet?.hashCode() ?: 0)
        result = 31 * result + (homeStreet2?.hashCode() ?: 0)
        result = 31 * result + (homeStreet3?.hashCode() ?: 0)
        result = 31 * result + (jobTitle?.hashCode() ?: 0)
        result = 31 * result + (jobTitle2?.hashCode() ?: 0)
        result = 31 * result + (lastName?.hashCode() ?: 0)
        result = 31 * result + (middleName?.hashCode() ?: 0)
        result = 31 * result + (mobilePhone?.hashCode() ?: 0)
        result = 31 * result + (nickName?.hashCode() ?: 0)
        result = 31 * result + (notes?.hashCode() ?: 0)
        result = 31 * result + (subjects?.hashCode() ?: 0)
        result = 31 * result + (suffix?.hashCode() ?: 0)
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (uid?.hashCode() ?: 0)
        result = 31 * result + (webPage?.hashCode() ?: 0)
        result = 31 * result + modified.hashCode()
        return result
    }

}