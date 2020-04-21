package de.deftk.lonet.api.model.feature.abstract

interface IUserController {

    fun getAutoLoginUrl(): String
    fun logout(removeTrust: Boolean = true)

}