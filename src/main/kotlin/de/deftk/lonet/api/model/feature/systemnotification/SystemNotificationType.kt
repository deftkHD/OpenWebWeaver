package de.deftk.lonet.api.model.feature.systemnotification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class SystemNotificationType {

    // This isn't nice

    @SerialName("1")
    UNKNOWN_1,

    @SerialName("2")
    UNKNOWN_2,

    @SerialName("3")
    UNKNOWN_3,

    @SerialName("4")
    UNKNOWN_4,

    @SerialName("5")
    PASSWORD_CHANGED,

    @SerialName("6")
    UNKNOWN_6,

    @SerialName("7")
    FILE_UPLOAD,

    @SerialName("8")
    FILE_DOWNLOAD,

    @SerialName("9")
    UNKNOWN_9,

    @SerialName("10")
    UNKNOWN_10,

    @SerialName("11")
    UNKNOWN_11,

    @SerialName("12")
    UNKNOWN_12,

    @SerialName("13")
    UNKNOWN_13,

    @SerialName("14")
    UNKNOWN_14,

    @SerialName("15")
    REQUEST_PASSWORD_RESET_CODE,

    @SerialName("16")
    UNKNOWN_16,

    @SerialName("17")
    UNKNOWN_17,

    @SerialName("18")
    UNKNOWN_18,

    @SerialName("19")
    NEW_POLL,

    @SerialName("20")
    UNKNOWN_20,

    @SerialName("21")
    UNKNOWN_21,

    @SerialName("22")
    UNKNOWN_22,

    @SerialName("23")
    UNKNOWN_23,

    @SerialName("24")
    UNKNOWN_24,

    @SerialName("25")
    UNKNOWN_25,

    @SerialName("26")
    UNKNOWN_26,

    @SerialName("27")
    UNKNOWN_27,

    @SerialName("28")
    UNKNOWN_28,

    @SerialName("29")
    NEW_NOTIFICATION,

    @SerialName("30")
    NEW_APPOINTMENT,

    @SerialName("31")
    UNKNOWN_31,

    @SerialName("32")
    UNKNOWN_32,

    @SerialName("33")
    NEW_TRUST,

    @SerialName("34")
    UNKNOWN_34,

    @SerialName("35")
    UNAUTHORIZED_LOGIN_LOCATION,

    @SerialName("36")
    UNKNOWN_36,

    @SerialName("37")
    UNKNOWN_37,

    @SerialName("38")
    UNKNOWN_38,

    @SerialName("39")
    UNKNOWN_39,

    @SerialName("40")
    UNKNOWN_40,

    @SerialName("41")
    UNKNOWN_41,

    @SerialName("42")
    UNKNOWN_42,

    @SerialName("43")
    UNKNOWN_43,

    @SerialName("44")
    UNKNOWN_44,

    @SerialName("45")
    UNKNOWN_45,

    @SerialName("46")
    NEW_TASK,

    @SerialName("47")
    UNKNOWN_47,

    @SerialName("48")
    UNKNOWN_48,

    @SerialName("49")
    UNKNOWN_49,

    @SerialName("50")
    UNKNOWN_50;
}