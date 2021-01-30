package de.deftk.lonet.api.auth

import de.deftk.lonet.api.request.auth.AuthRequest
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