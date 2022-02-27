package de.deftk.openww.api.model.feature.profile

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.contacts.Gender
import de.deftk.openww.api.model.feature.filestorage.session.ISessionFile

interface IUserProfile {

    val fullName: String?
    val firstName: String?
    val lastName: String?
    val homePostalCode: String?
    val homeCity: String?
    val homeState: String?
    val birthday: String?
    val emailAddress: String?
    val gender: Gender?
    val hobbies: String?
    val notes: String?
    val website: String?
    val company: String?
    val companyType: String?
    val subjects: String?
    val jobTitle: String?
    val visible: Boolean?
    val jobTitle2: String?
    val homePhone: String?
    val homeFax: String?
    val mobilePhone: String?
    val title: String?
    val image: ISessionFile?

    suspend fun setFullName(fullName: String?, context: IRequestContext)
    suspend fun setFirstName(firstName: String?, context: IRequestContext)
    suspend fun setLastName(lastName: String?, context: IRequestContext)
    suspend fun setHomePostalCode(homePostalCode: String?, context: IRequestContext)
    suspend fun setHomeCity(homeCity: String?, context: IRequestContext)
    suspend fun setHomeState(homeState: String?, context: IRequestContext)
    suspend fun setBirthday(birthday: String?, context: IRequestContext)
    suspend fun setEmailAddress(emailAddress: String?, context: IRequestContext)
    suspend fun setGender(gender: Gender?, context: IRequestContext)
    suspend fun setHobbies(hobbies: String?, context: IRequestContext)
    suspend fun setNotes(notes: String?, context: IRequestContext)
    suspend fun setWebsite(website: String?, context: IRequestContext)
    suspend fun setCompany(company: String?, context: IRequestContext)
    suspend fun setCompanyType(companyType: String?, context: IRequestContext)
    suspend fun setSubjects(subjects: String?, context: IRequestContext)
    suspend fun setJobTitle(jobTitle: String?, context: IRequestContext)
    suspend fun setVisible(visible: Boolean, context: IRequestContext)
    suspend fun setJobTitle2(jobTitle2: String?, context: IRequestContext)
    suspend fun setHomePhone(homePhone: String?, context: IRequestContext)
    suspend fun setHomeFax(homeFax: String?, context: IRequestContext)
    suspend fun setMobilePhone(mobilePhone: String?, context: IRequestContext)
    suspend fun setTitle(title: String?, context: IRequestContext)
    suspend fun setImage(sessionFile: ISessionFile, context: IRequestContext)

    suspend fun edit(
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
        image: ISessionFile?, context: IRequestContext
    )

}