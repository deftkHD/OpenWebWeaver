package de.deftk.openww.api.model.feature.contacts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Gender {
    @SerialName("1")
    MALE,

    @SerialName("2")
    FEMALE,

    @SerialName("3")
    OTHER;

}