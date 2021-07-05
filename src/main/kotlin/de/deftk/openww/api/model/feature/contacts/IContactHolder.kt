package de.deftk.openww.api.model.feature.contacts

import de.deftk.openww.api.model.IRequestContext

interface IContactHolder {

    suspend fun getContacts(context: IRequestContext): List<IContact>
    suspend fun addContact(contact: IContact, context: IRequestContext): IContact
    suspend fun addContact(
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
    ): IContact

}