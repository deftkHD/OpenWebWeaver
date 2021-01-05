package de.deftk.lonet.api.model.feature.mailbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class SignaturePosition {

    @SerialName("beginning")
    BEGINNING,

    @SerialName("end")
    END,

    @SerialName("none")
    NONE

}