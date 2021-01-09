package de.deftk.lonet.api.implementation.feature.contacts

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.Modification
import de.deftk.lonet.api.model.feature.contacts.Gender
import de.deftk.lonet.api.model.feature.contacts.IContact
import de.deftk.lonet.api.request.OperatingScopeApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

@Serializable
class Contact(
    override val id: Int,
    private var birthday: String? = null,
    @SerialName("businesscity")
    private var businessCity: String? = null,
    @SerialName("businesscoords")
    private var businessCoords: String? = null,
    @SerialName("businesscountry")
    private var businessCountry: String? = null,
    @SerialName("businessfax")
    private var businessFax: String? = null,
    @SerialName("businessphone")
    private var businessPhone: String? = null,
    @SerialName("businesspostalcode")
    private var businessPostalCode: String? = null,
    @SerialName("businessstate")
    private var businessState: String? = null,
    @SerialName("businessstreet")
    private var businessStreet: String? = null,
    @SerialName("businessstreet2")
    private var businessStreet2: String? = null,
    @SerialName("businessstreet3")
    private var businessStreet3: String? = null,
    private var categories: String? = null,
    private var company: String? = null,
    @SerialName("companytype")
    private var companyType: String? = null,
    @SerialName("email2address")
    private var email2Address: String? = null,
    @SerialName("email3address")
    private var email3Address: String? = null,
    @SerialName("emailaddress")
    private var emailAddress: String? = null,
    @SerialName("firstname")
    private var firstName: String? = null,
    @SerialName("fullname")
    private var fullName: String? = null,
    private var gender: Gender? = null,
    private var hobby: String? = null,
    @SerialName("homecity")
    private var homeCity: String? = null,
    @SerialName("homecoords")
    private var homeCoords: String? = null,
    @SerialName("homecountry")
    private var homeCountry: String? = null,
    @SerialName("homefax")
    private var homeFax: String? = null,
    @SerialName("homephone")
    private var homePhone: String? = null,
    @SerialName("homepostalcode")
    private var homePostalCode: String? = null,
    @SerialName("homestate")
    private var homeState: String? = null,
    @SerialName("homestreet")
    private var homeStreet: String? = null,
    @SerialName("homestreet2")
    private var homeStreet2: String? = null,
    @SerialName("homestreet3")
    private var homeStreet3: String? = null,
    @SerialName("jobtitle")
    private var jobTitle: String? = null,
    @SerialName("jobtitle2")
    private var jobTitle2: String? = null,
    @SerialName("lastname")
    private var lastName: String? = null,
    @SerialName("middlename")
    private var middleName: String? = null,
    @SerialName("mobilephone")
    private var mobilePhone: String? = null,
    @SerialName("nickname")
    private var nickName: String? = null,
    private var notes: String? = null,
    private var subjects: String? = null,
    private var suffix: String? = null,
    private var title: String? = null,
    private var uid: String? = null,
    @SerialName("webpage")
    private var webPage: String? = null,
    override val created: Modification,
    private var modified: Modification
) : IContact {

    var deleted = false
        private set

    override fun getBirthday(): String? = birthday
    override fun getBusinessCity(): String? = businessCity
    override fun getBusinessCoords(): String? = businessCoords
    override fun getBusinessCountry(): String? = businessCountry
    override fun getBusinessFax(): String? = businessFax
    override fun getBusinessPhone(): String? = businessPhone
    override fun getBusinessPostalCode(): String? = businessPostalCode
    override fun getBusinessState(): String? = businessState
    override fun getBusinessStreet(): String? = businessStreet
    override fun getBusinessStreet2(): String? = businessStreet2
    override fun getBusinessStreet3(): String? = businessStreet3
    override fun getCategories(): String? = categories
    override fun getCompany(): String? = company
    override fun getCompanyType(): String? = companyType
    override fun getEmailAddress2(): String? = email2Address
    override fun getEmailAddress3(): String? = email3Address
    override fun getEmailAddress(): String? = emailAddress
    override fun getFirstName(): String? = firstName
    override fun getFullName(): String? = fullName
    override fun getGender(): Gender? = gender
    override fun getHobby(): String? = hobby
    override fun getHomeCity(): String? = homeCity
    override fun getHomeCoords(): String? = homeCoords
    override fun getHomeCountry(): String? = homeCountry
    override fun getHomeFax(): String? = homeFax
    override fun getHomePhone(): String? = homePhone
    override fun getHomePostalCode(): String? = homePostalCode
    override fun getHomeState(): String? = homeState
    override fun getHomeStreet(): String? = homeStreet
    override fun getHomeStreet2(): String? = homeStreet2
    override fun getHomeStreet3(): String? = homeStreet3
    override fun getJobTitle(): String? = jobTitle
    override fun getJobTitle2(): String? = jobTitle2
    override fun getLastName(): String? = lastName
    override fun getMiddleName(): String? = middleName
    override fun getMobilePhone(): String? = mobilePhone
    override fun getNickName(): String? = nickName
    override fun getNotes(): String? = notes
    override fun getSubjects(): String? = subjects
    override fun getSuffix(): String? = suffix
    override fun getTitle(): String? = title
    override fun getUid(): String? = uid
    override fun getWebPage(): String? = webPage
    override fun getModified(): Modification = modified

    override fun edit(
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
        readFrom(Json.decodeFromJsonElement(subResponse["entry"]!!))
    }

    override fun delete(context: IRequestContext) {
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Contact

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

}