package de.deftk.lonet.api.model.feature.systemnotification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class SystemNotificationType {
    @SerialName("7")
    FILE_UPLOAD,

    @SerialName("8")
    FILE_DOWNLOAD,

    @SerialName("29")
    NEW_NOTIFICATION,

    @SerialName("30")
    NEW_APPOINTMENT,

    @SerialName("33")
    NEW_TRUST,

    @SerialName("35")
    UNAUTHORIZED_LOGIN_LOCATION,

    @SerialName("46")
    NEW_TASK;
}