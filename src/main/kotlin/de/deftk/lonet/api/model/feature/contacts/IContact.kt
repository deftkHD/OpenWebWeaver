package de.deftk.lonet.api.model.feature.contacts

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.IModifiable

interface IContact: IModifiable {

    val id: Int

    fun getBirthday(): String?
    fun getBusinessCity(): String?
    fun getBusinessCoords(): String?
    fun getBusinessCountry(): String?
    fun getBusinessFax(): String?
    fun getBusinessPhone(): String?
    fun getBusinessPostalCode(): String?
    fun getBusinessState(): String?
    fun getBusinessStreet(): String?
    fun getBusinessStreet2(): String?
    fun getBusinessStreet3(): String?
    fun getCategories(): String?
    fun getCompany(): String?
    fun getCompanyType(): String?
    fun getEmailAddress2(): String?
    fun getEmailAddress3(): String?
    fun getEmailAddress(): String?
    fun getFirstName(): String?
    fun getFullName(): String?
    fun getGender(): Gender?
    fun getHobby(): String?
    fun getHomeCity(): String?
    fun getHomeCoords(): String?
    fun getHomeCountry(): String?
    fun getHomeFax(): String?
    fun getHomePhone(): String?
    fun getHomePostalCode(): String?
    fun getHomeState(): String?
    fun getHomeStreet(): String?
    fun getHomeStreet2(): String?
    fun getHomeStreet3(): String?
    fun getJobTitle(): String?
    fun getJobTitle2(): String?
    fun getLastName(): String?
    fun getMiddleName(): String?
    fun getMobilePhone(): String?
    fun getNickName(): String?
    fun getNotes(): String?
    fun getSubjects(): String?
    fun getSuffix(): String?
    fun getTitle(): String?
    fun getUid(): String?
    fun getWebPage(): String?

    fun edit(
        birthday: String? = null,
        businessCity: String? = null,
        businessCoords: String? = null,
        businessCountry: String? = null,
        businessFax: String? = null,
        businessPhone: String? = null,
        businessPostalCode: String? = null,
        businessState: String? = null,
        businessStreet: String? = null,
        businessStreet2: String? = null,
        businessStreet3: String? = null,
        categories: String? = null,
        company: String? = null,
        companyType: String? = null,
        email2Address: String? = null,
        email3Address: String? = null,
        emailAddress: String? = null,
        firstName: String? = null,
        fullName: String? = null,
        gender: Gender? = null,
        hobby: String? = null,
        homeCity: String? = null,
        homeCoords: String? = null,
        homeCountry: String? = null,
        homeFax: String? = null,
        homePhone: String? = null,
        homePostalCode: String? = null,
        homeState: String? = null,
        homeStreet: String? = null,
        homeStreet2: String? = null,
        homeStreet3: String? = null,
        jobTitle: String? = null,
        jobTitle2: String? = null,
        lastName: String? = null,
        middleName: String? = null,
        mobilePhone: String? = null,
        nickName: String? = null,
        notes: String? = null,
        subjects: String? = null,
        suffix: String? = null,
        title: String? = null,
        uid: String? = null,
        webPage: String? = null,
        context: IRequestContext
    )

    fun delete(context: IRequestContext)

}