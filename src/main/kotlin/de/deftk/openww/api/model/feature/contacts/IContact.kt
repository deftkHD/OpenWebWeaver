package de.deftk.openww.api.model.feature.contacts

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.IModifiable

interface IContact: IModifiable {

    val id: Int

    val birthday: String?
    val businessCity: String?
    val businessCoords: String?
    val businessCountry: String?
    val businessFax: String?
    val businessPhone: String?
    val businessPostalCode: String?
    val businessState: String?
    val businessStreet: String?
    val businessStreet2: String?
    val businessStreet3: String?
    val categories: String?
    val company: String?
    val companyType: String?
    val email2Address: String?
    val email3Address: String?
    val emailAddress: String?
    val firstName: String?
    val fullName: String?
    val gender: Gender?
    val hobby: String?
    val homeCity: String?
    val homeCoords: String?
    val homeCountry: String?
    val homeFax: String?
    val homePhone: String?
    val homePostalCode: String?
    val homeState: String?
    val homeStreet: String?
    val homeStreet2: String?
    val homeStreet3: String?
    val jobTitle: String?
    val jobTitle2: String?
    val lastName: String?
    val middleName: String?
    val mobilePhone: String?
    val nickName: String?
    val notes: String?
    val subjects: String?
    val suffix: String?
    val title: String?
    val uid: String?
    val webPage: String?

    suspend fun edit(
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

    suspend fun delete(context: IRequestContext)

}