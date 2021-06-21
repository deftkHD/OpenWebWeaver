package de.deftk.openww.api.model.feature

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ServiceType {
    @SerialName("apns")
    APPLE_PUSH_NOTIFICATION_SERVICE,

    @SerialName("fcm")
    FIREBASE_CLOUD_MESSAGING,

    @Deprecated("Google Cloud Messaging is officially deprecated. Consider using Firebase Cloud Messaging")
    @SerialName("gcm")
    GOOGLE_CLOUD_MESSAGING,

    @SerialName("test")
    TEST
}