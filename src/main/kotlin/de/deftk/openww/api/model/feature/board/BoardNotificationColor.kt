package de.deftk.openww.api.model.feature.board

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class BoardNotificationColor(val serialId: Int) {
    @SerialName("0")
    BLUE(0),

    @SerialName("1")
    GREEN(1),

    @SerialName("2")
    RED(2),

    @SerialName("3")
    YELLOW(3),

    @SerialName("4")
    WHITE(4);
}