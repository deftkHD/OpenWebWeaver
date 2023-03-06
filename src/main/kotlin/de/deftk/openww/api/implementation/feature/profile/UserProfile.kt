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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UserProfile) return false

        if (fullName != other.fullName) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (homePostalCode != other.homePostalCode) return false
        if (homeCity != other.homeCity) return false
        if (homeState != other.homeState) return false
        if (birthday != other.birthday) return false
        if (emailAddress != other.emailAddress) return false
        if (gender != other.gender) return false
        if (hobbies != other.hobbies) return false
        if (notes != other.notes) return false
        if (website != other.website) return false
        if (company != other.company) return false
        if (companyType != other.companyType) return false
        if (subjects != other.subjects) return false
        if (jobTitle != other.jobTitle) return false
        if (visible != other.visible) return false
        if (jobTitle2 != other.jobTitle2) return false
        if (homePhone != other.homePhone) return false
        if (homeFax != other.homeFax) return false
        if (mobilePhone != other.mobilePhone) return false
        if (title != other.title) return false
        if (image != other.image) return false

        return true
    }

    override fun hashCode(): Int {
        var result = fullName?.hashCode() ?: 0
        result = 31 * result + (firstName?.hashCode() ?: 0)
        result = 31 * result + (lastName?.hashCode() ?: 0)
        result = 31 * result + (homePostalCode?.hashCode() ?: 0)
        result = 31 * result + (homeCity?.hashCode() ?: 0)
        result = 31 * result + (homeState?.hashCode() ?: 0)
        result = 31 * result + (birthday?.hashCode() ?: 0)
        result = 31 * result + (emailAddress?.hashCode() ?: 0)
        result = 31 * result + (gender?.hashCode() ?: 0)
        result = 31 * result + (hobbies?.hashCode() ?: 0)
        result = 31 * result + (notes?.hashCode() ?: 0)
        result = 31 * result + (website?.hashCode() ?: 0)
        result = 31 * result + (company?.hashCode() ?: 0)
        result = 31 * result + (companyType?.hashCode() ?: 0)
        result = 31 * result + (subjects?.hashCode() ?: 0)
        result = 31 * result + (jobTitle?.hashCode() ?: 0)
        result = 31 * result + (visible?.hashCode() ?: 0)
        result = 31 * result + (jobTitle2?.hashCode() ?: 0)
        result = 31 * result + (homePhone?.hashCode() ?: 0)
        result = 31 * result + (homeFax?.hashCode() ?: 0)
        result = 31 * result + (mobilePhone?.hashCode() ?: 0)
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (image?.hashCode() ?: 0)
        return result
    }

}