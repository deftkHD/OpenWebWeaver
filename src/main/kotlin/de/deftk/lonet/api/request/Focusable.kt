package de.deftk.lonet.api.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Focusable {

    @SerialName("administration") ADMINISTRATION,
    @SerialName("mailbox") MAILBOX,
    @SerialName("messenger") MESSENGER,
    @SerialName("members") MEMBERS,
    @SerialName("addresses") ADDRESSES,
    @SerialName("calendar") CALENDAR,
    @SerialName("tasks") TASKS,
    @SerialName("notes") NOTES,
    @SerialName("board") BOARD,
    @SerialName("board_teacher") BOARD_TEACHER,
    @SerialName("board_pupil") BOARD_PUPIL,
    @SerialName("forum") FORUM,
    @SerialName("licenses") LICENSES,
    @SerialName("files") FILES,
    @SerialName("member") MEMBER,
    @SerialName("resource_management") RESOURCE_MANAGEMENT,
    @SerialName("external") EXTERNAL,
    @SerialName("session_files") SESSION_FILES,
    @SerialName("profile") PROFILE,
    @SerialName("messages") MESSAGES,
    @SerialName("wiki") WIKI,
    @SerialName("settings") SETTINGS,
    @SerialName("proxy") PROXY,
    @SerialName("trusts") TRUSTS,
    @SerialName("courselets") COURSELETS

}