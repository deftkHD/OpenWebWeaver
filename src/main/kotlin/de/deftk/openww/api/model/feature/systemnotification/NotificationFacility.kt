package de.deftk.openww.api.model.feature.systemnotification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class NotificationFacility {

    @SerialName("normal")
    NORMAL,

    @SerialName("qm")
    QUICK_MESSAGE,

    @SerialName("mail")
    MAIL,

    @SerialName("push")
    PUSH_NOTIFICATION,

    @SerialName("digest")
    DIGEST,

    @SerialName("digest_weekly")
    DIGEST_WEEKLY,

    @SerialName("sms")
    SMS

}