package de.deftk.openww.api.model.feature.mailbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ReferenceMode {
    @SerialName("forwarded")
    FORWARDED,

    @SerialName("answered")
    ANSWERED
}