package de.deftk.openww.api.auth

import kotlinx.serialization.Serializable

@Serializable
data class Credentials(val username: String, val password: String? = null, val token: String? = null) {

    companion object {
        fun fromPassword(username: String, password: String) = Credentials(username, password, null)
        fun fromToken(username: String, token: String) = Credentials(username, null, token)
    }

}
