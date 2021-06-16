package de.deftk.lonet.api.model.feature.notes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class NoteColor {

    @SerialName("0")
    BLUE,

    @SerialName("1")
    GREEN,

    @SerialName("2")
    RED,

    @SerialName("3")
    YELLOW,

    @SerialName("4")
    WHITE

}
