package de.deftk.lonet.api.model.abstract

import java.io.Serializable

interface IManageable: Serializable {

    fun getLogin(): String
    fun getName(): String
    fun getType(): ManageableType

}