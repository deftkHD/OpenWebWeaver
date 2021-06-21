package de.deftk.openww.api.auth

import de.deftk.openww.api.request.auth.AuthRequest
import kotlinx.serialization.Serializable

@Serializable
data class Substitute(
    val login: String,
    val password: String,
    val bearer: String,
    val timeout: Long,
    val services: List<AuthRequest.SubstituteService>,
    val name: String
)