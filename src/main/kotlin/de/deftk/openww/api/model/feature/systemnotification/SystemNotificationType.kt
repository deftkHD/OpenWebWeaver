package de.deftk.openww.api.model.feature.systemnotification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class SystemNotificationType {

    // This isn't nice

    @SerialName("1")
    FOLDER_FILE_UPLOAD,

    @SerialName("2")
    NEW_FORUM_COMMENT,

    @SerialName("3")
    NEW_COURSE,

    @SerialName("4")
    UNKNOWN_4,

    @SerialName("5")
    PASSWORD_CHANGED,

    @SerialName("6")
    NEW_FORUM_POST,

    @SerialName("7")
    FILE_UPLOAD,

    @SerialName("8")
    FILE_DOWNLOAD,

    @SerialName("9")
    UNKNOWN_9,

    @SerialName("10")
    ADDED_TO_MESSENGER,

    @SerialName("11")
    UNKNOWN_11,

    @SerialName("12")
    CALENDAR_REMINDER,

    @SerialName("13")
    NEW_MAIL,

    @SerialName("14")
    NEW_GUESTBOOK_ENTRY,

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
    RESOURCE_FAULTY,

    @SerialName("22")
    RESOURCE_REPAIRED,

    @SerialName("23")
    NEW_BLOG_COMMENT,

    @SerialName("24")
    NEW_BLOG_ENTRY,

    @SerialName("25")
    NEW_LEARNING_LOG_ENTRY,

    @SerialName("26")
    NEW_LEARNING_LOG_ENTRY_COMMENT,

    @SerialName("27")
    NEW_LEARNING_LOG_COMMENT,

    @SerialName("28")
    NEW_LEARNING_LOG,

    @SerialName("29")
    NEW_NOTIFICATION,

    @SerialName("30")
    NEW_APPOINTMENT,

    @SerialName("31")
    NEW_POLL2,

    @SerialName("32")
    UNKNOWN_32,

    @SerialName("33")
    NEW_TRUST,

    @SerialName("34")
    UNKNOWN_34,

    @SerialName("35")
    UNAUTHORIZED_LOGIN_LOCATION,

    @SerialName("36")
    NEW_WALL_ENTRY,

    @SerialName("37")
    NEW_WALL_COMMENT,

    @SerialName("38")
    NEW_SUBSTITUTION_PLAN,

    @SerialName("39")
    NEW_TEACHER_NOTIFICATION,

    @SerialName("40")
    NEW_STUDENT_NOTIFICATION,

    @SerialName("41")
    PENDING_QUICK_MESSAGE,

    @SerialName("42")
    COURSELET_CORRECTED,

    @SerialName("43")
    NEW_BOOKMARK,

    @SerialName("44")
    UNKNOWN_44,

    @SerialName("45")
    RESOURCE_BOOKED,

    @SerialName("46")
    NEW_TASK,

    @SerialName("47")
    NEW_FORM_SUBMISSION,

    @SerialName("48")
    UNKNOWN_48,

    @SerialName("49")
    NEW_CONSULTATION_HOUR_BOOKED,

    @SerialName("50")
    NEW_COURSELET,

    @SerialName("51")
    NEW_LEARNING_PLAN;
}