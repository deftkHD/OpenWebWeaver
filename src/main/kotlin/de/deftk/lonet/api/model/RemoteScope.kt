package de.deftk.lonet.api.model

import de.deftk.lonet.api.serialization.BooleanFromIntSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteScope(
    override val login: String,
    @SerialName("name_hr")
    override val name: String,
    override val type: Int,
    @SerialName("is_online")
    @Serializable(with = BooleanFromIntSerializer::class)
    val isOnline: Boolean = false
) : IScope