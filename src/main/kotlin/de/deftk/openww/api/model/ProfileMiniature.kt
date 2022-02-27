package de.deftk.openww.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileMiniature(
    @SerialName("data_encoding")
    val encoding: String,
    val data: String
)