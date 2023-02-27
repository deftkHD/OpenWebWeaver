package de.deftk.openww.api.implementation.feature.profile

import de.deftk.openww.api.WebWeaverClient
import de.deftk.openww.api.implementation.feature.filestorage.session.SessionFile
import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.contacts.Gender
import de.deftk.openww.api.model.feature.filestorage.session.ISessionFile
import de.deftk.openww.api.model.feature.profile.IUserProfile
import de.deftk.openww.api.request.UserApiRequest
import de.deftk.openww.api.response.ResponseUtil
import de.deftk.openww.api.serialization.BooleanFromIntSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement

@Serializable
class UserProfile(
    @SerialName("fullname")
    override var fullName: String? = null,
    @SerialName("firstname")
    override var firstName: String? = null,
    @SerialName("lastname")
    override var lastName: String? = null,
    @SerialName("homepostalcode")
    override var homePostalCode: String? = null,
    @SerialName("homecity")
    override var homeCity: String? = null,
    @SerialName("homestate")
    override var homeState: String? = null,
    override var birthday: String? = null,
    @SerialName("emailaddress")
    override var emailAddress: String? = null,
    override var gender: Gender? = null,
    @SerialName("hobby")
    override var hobbies: String? = null,
    override var notes: String? = null,
    @SerialName("webpage")
    override var website: String? = null,
    override var company: String? = null,
    @SerialName("companytype")
    override var companyType: String? = null,
    override var subjects: String? = null,
    @SerialName("jobtitle")
    override var jobTitle: String? = null,
    @Serializable(with = BooleanFromIntSerializer::class)
    override var visible: Boolean? = null,
    @SerialName("jobtitle2")
    override var jobTitle2: String? = null,
    @SerialName("homephone")
    override var homePhone: String? = null,
    @SerialName("homefax")
    override var homeFax: String? = null,
    @SerialName("mobilephone")
    override var mobilePhone: String? = null,
    override var title: String? = null,
    override var image: SessionFile? = null
) : IUserProfile {

    override suspend fun setFullName(fullName: String?, context: IRequestContext) = edit(fullName, firstName, lastName, homePostalCode, homeCity, homeState, birthday, emailAddress, gender, hobbies, notes, website, company, companyType, subjects, jobTitle, visible, jobTitle2, homePhone, homeFax, mobilePhone, title, image, context)
    override suspend fun setFirstName(firstName: String?, context: IRequestContext) = edit(fullName, firstName, lastName, homePostalCode, homeCity, homeState, birthday, emailAddress, gender, hobbies, notes, website, company, companyType, subjects, jobTitle, visible, jobTitle2, homePhone, homeFax, mobilePhone, title, image, context)
    override suspend fun setLastName(lastName: String?, context: IRequestContext) = edit(fullName, firstName, lastName, homePostalCode, homeCity, homeState, birthday, emailAddress, gender, hobbies, notes, website, company, companyType, subjects, jobTitle, visible, jobTitle2, homePhone, homeFax, mobilePhone, title, image, context)
    override suspend fun setHomePostalCode(homePostalCode: String?, context: IRequestContext) = edit(fullName, firstName, lastName, homePostalCode, homeCity, homeState, birthday, emailAddress, gender, hobbies, notes, website, company, companyType, subjects, jobTitle, visible, jobTitle2, homePhone, homeFax, mobilePhone, title, image, context)
    override suspend fun setHomeCity(homeCity: String?, context: IRequestContext) = edit(fullName, firstName, lastName, homePostalCode, homeCity, homeState, birthday, emailAddress, gender, hobbies, notes, website, company, companyType, subjects, jobTitle, visible, jobTitle2, homePhone, homeFax, mobilePhone, title, image, context)
    override suspend fun setHomeState(homeState: String?, context: IRequestContext) = edit(fullName, firstName, lastName, homePostalCode, homeCity, homeState, birthday, emailAddress, gender, hobbies, notes, website, company, companyType, subjects, jobTitle, visible, jobTitle2, homePhone, homeFax, mobilePhone, title, image, context)
    override suspend fun setBirthday(birthday: String?, context: IRequestContext) = edit(fullName, firstName, lastName, homePostalCode, homeCity, homeState, birthday, emailAddress, gender, hobbies, notes, website, company, companyType, subjects, jobTitle, visible, jobTitle2, homePhone, homeFax, mobilePhone, title, image, context)
    override suspend fun setEmailAddress(emailAddress: String?, context: IRequestContext) = edit(fullName, firstName, lastName, homePostalCode, homeCity, homeState, birthday, emailAddress, gender, hobbies, notes, website, company, companyType, subjects, jobTitle, visible, jobTitle2, homePhone, homeFax, mobilePhone, title, image, context)
    override suspend fun setGender(gender: Gender?, context: IRequestContext) = edit(fullName, firstName, lastName, homePostalCode, homeCity, homeState, birthday, emailAddress, gender, hobbies, notes, website, company, companyType, subjects, jobTitle, visible, jobTitle2, homePhone, homeFax, mobilePhone, title, image, context)
    override suspend fun setHobbies(hobbies: String?, context: IRequestContext) = edit(fullName, firstName, lastName, homePostalCode, homeCity, homeState, birthday, emailAddress, gender, hobbies, notes, website, company, companyType, subjects, jobTitle, visible, jobTitle2, homePhone, homeFax, mobilePhone, title, image, context)
    override suspend fun setNotes(notes: String?, context: IRequestContext) = edit(fullName, firstName, lastName, homePostalCode, homeCity, homeState, birthday, emailAddress, gender, hobbies, notes, website, company, companyType, subjects, jobTitle, visible, jobTitle2, homePhone, homeFax, mobilePhone, title, image, context)
    override suspend fun setWebsite(website: String?, context: IRequestContext) = edit(fullName, firstName, lastName, homePostalCode, homeCity, homeState, birthday, emailAddress, gender, hobbies, notes, website, company, companyType, subjects, jobTitle, visible, jobTitle2, homePhone, homeFax, mobilePhone, title, image, context)
    override suspend fun setCompany(company: String?, context: IRequestContext) = edit(fullName, firstName, lastName, homePostalCode, homeCity, homeState, birthday, emailAddress, gender, hobbies, notes, website, company, companyType, subjects, jobTitle, visible, jobTitle2, homePhone, homeFax, mobilePhone, title, image, context)
    override suspend fun setCompanyType(companyType: String?, context: IRequestContext) = edit(fullName, firstName, lastName, homePostalCode, homeCity, homeState, birthday, emailAddress, gender, hobbies, notes, website, company, companyType, subjects, jobTitle, visible, jobTitle2, homePhone, homeFax, mobilePhone, title, image, context)
    override suspend fun setSubjects(subjects: String?, context: IRequestContext) = edit(fullName, firstName, lastName, homePostalCode, homeCity, homeState, birthday, emailAddress, gender, hobbies, notes, website, company, companyType, subjects, jobTitle, visible, jobTitle2, homePhone, homeFax, mobilePhone, title, image, context)
    override suspend fun setJobTitle(jobTitle: String?, context: IRequestContext) = edit(fullName, firstName, lastName, homePostalCode, homeCity, homeState, birthday, emailAddress, gender, hobbies, notes, website, company, companyType, subjects, jobTitle, visible, jobTitle2, homePhone, homeFax, mobilePhone, title, image, context)
    override suspend fun setVisible(visible: Boolean, context: IRequestContext) = edit(fullName, firstName, lastName, homePostalCode, homeCity, homeState, birthday, emailAddress, gender, hobbies, notes, website, company, companyType, subjects, jobTitle, visible, jobTitle2, homePhone, homeFax, mobilePhone, title, image, context)
    override suspend fun setJobTitle2(jobTitle2: String?, context: IRequestContext) = edit(fullName, firstName, lastName, homePostalCode, homeCity, homeState, birthday, emailAddress, gender, hobbies, notes, website, company, companyType, subjects, jobTitle, visible, jobTitle2, homePhone, homeFax, mobilePhone, title, image, context)
    override suspend fun setHomePhone(homePhone: String?, context: IRequestContext) = edit(fullName, firstName, lastName, homePostalCode, homeCity, homeState, birthday, emailAddress, gender, hobbies, notes, website, company, companyType, subjects, jobTitle, visible, jobTitle2, homePhone, homeFax, mobilePhone, title, image, context)
    override suspend fun setHomeFax(homeFax: String?, context: IRequestContext) = edit(fullName, firstName, lastName, homePostalCode, homeCity, homeState, birthday, emailAddress, gender, hobbies, notes, website, company, companyType, subjects, jobTitle, visible, jobTitle2, homePhone, homeFax, mobilePhone, title, image, context)
    override suspend fun setMobilePhone(mobilePhone: String?, context: IRequestContext) = edit(fullName, firstName, lastName, homePostalCode, homeCity, homeState, birthday, emailAddress, gender, hobbies, notes, website, company, companyType, subjects, jobTitle, visible, jobTitle2, homePhone, homeFax, mobilePhone, title, image, context)
    override suspend fun setTitle(title: String?, context: IRequestContext) = edit(fullName, firstName, lastName, homePostalCode, homeCity, homeState, birthday, emailAddress, gender, hobbies, notes, website, company, companyType, subjects, jobTitle, visible, jobTitle2, homePhone, homeFax, mobilePhone, title, image, context)
    override suspend fun setImage(sessionFile: ISessionFile, context: IRequestContext) = edit(fullName, firstName, lastName, homePostalCode, homeCity, homeState, birthday, emailAddress, gender, hobbies, notes, website, company, companyType, subjects, jobTitle, visible, jobTitle2, homePhone, homeFax, mobilePhone, title, image, context)

    override suspend fun edit(
        fullName: String?,
        firstName: String?,
        lastName: String?,
        homePostalCode: String?,
        homeCity: String?,
        homeState: String?,
        birthday: String?,
        emailAddress: String?,
        gender: Gender?,
        hobbies: String?,
        notes: String?,
        website: String?,
        company: String?,
        companyType: String?,
        subjects: String?,
        jobTitle: String?,
        visible: Boolean?,
        jobTitle2: String?,
        homePhone: String?,
        homeFax: String?,
        mobilePhone: String?,
        title: String?,
        image: ISessionFile?,
        context: IRequestContext
    ) {
        val request = UserApiRequest(context)
        val id = request.addSetProfileRequest(fullName, firstName, lastName, homePostalCode, homeCity, homeState, birthday, emailAddress, gender, hobbies, notes, website, company, companyType, subjects, jobTitle, visible, jobTitle2, homePhone, homeFax, mobilePhone, title, image)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse["profile"]!!))
    }

    private fun readFrom(userProfile: UserProfile) {
        fullName = userProfile.fullName
        firstName = userProfile.firstName
        lastName = userProfile.lastName
        homePostalCode = userProfile.homePostalCode
        homeCity = userProfile.homeCity
        homeState = userProfile.homeState
        birthday = userProfile.birthday
        emailAddress = userProfile.emailAddress
        gender = userProfile.gender
        hobbies = userProfile.hobbies
        notes = userProfile.notes
        website = userProfile.website
        company = userProfile.company
        companyType = userProfile.companyType
        subjects = userProfile.subjects
        jobTitle = userProfile.jobTitle
        visible = userProfile.visible
        jobTitle2 = userProfile.jobTitle2
        homePhone = userProfile.homePhone
        homeFax = userProfile.homeFax
        mobilePhone = userProfile.mobilePhone
        title = userProfile.title
        image = userProfile.image
    }

}