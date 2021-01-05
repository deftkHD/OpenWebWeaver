package de.deftk.lonet.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Locale() {
    @SerialName("en")
    ENGLISH,

    @SerialName("de")
    GERMAN,

    @SerialName("fr")
    FRANCE,

    @SerialName("es")
    SPANISH,

    @SerialName("it")
    ITALIAN,

    @SerialName("tr")
    TURKEY
}