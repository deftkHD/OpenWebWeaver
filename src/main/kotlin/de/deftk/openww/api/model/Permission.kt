package de.deftk.openww.api.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = PermissionSerializer::class)
enum class Permission(val id: String) {

    @SerialName("sysadmin") SYSADMIN("sysadmin"),

    @SerialName("admin") ADMIN_READ("admin"),
    @SerialName("admin_write") ADMIN_WRITE("admin_write"),
    @SerialName("admin_admin") ADMIN_ADMIN("admin_admin"),

    @SerialName("messenger") MESSENGER_READ("messenger"),
    @SerialName("messenger_write") MESSENGER_WRITE("messenger_write"),
    @SerialName("messenger_admin") MESSENGER_ADMIN("messenger_admin"),

    @SerialName("mail") MAILBOX_READ("mail"),
    @SerialName("mail_write") MAILBOX_WRITE("mail_write"),
    @SerialName("mail_admin") MAILBOX_ADMIN("mail_admin"),

    @SerialName("mailing_lists") MAILING_LISTS_READ("mailing_lists"),
    @SerialName("mailing_lists_write") MAILING_LISTS_WRITE("mailing_lists_write"),
    @SerialName("mailing_lists_admin") MAILING_LISTS_ADMIN("mailing_lists_admin"),

    @SerialName("members") MEMBERS_READ("members"),
    @SerialName("members_write") MEMBERS_WRITE("members_write"),
    @SerialName("members_admin") MEMBERS_ADMIN("members_admin"),

    @SerialName("address") ADDRESSES_READ("address"),
    @SerialName("address_write") ADDRESSES_WRITE("address_write"),
    @SerialName("address_admin") ADDRESSES_ADMIN("address_admin"),

    @SerialName("calendar") CALENDAR_READ("calendar"),
    @SerialName("calendar_write") CALENDAR_WRITE("calendar_write"),
    @SerialName("calendar_admin") CALENDAR_ADMIN("calendar_admin"),

    @SerialName("chat") CHAT_READ("chat"),
    @SerialName("chat_write") CHAT_WRITE("chat_write"),
    @SerialName("chat_admin") CHAT_ADMIN("chat_admin"),

    @SerialName("notes") NOTES_READ("notes"),
    @SerialName("notes_write") NOTES_WRITE("notes_write"),
    @SerialName("notes_admin") NOTES_ADMIN("notes_admin"),

    @SerialName("books") BOOKS_READ("books"),
    @SerialName("books_write") BOOKS_WRITE("books_write"),
    @SerialName("books_admin") BOOKS_ADMIN("books_admin"),

    @SerialName("bookmarks") BOOKMARKS_READ("bookmarks"),
    @SerialName("bookmarks_write") BOOKMARKS_WRITE("bookmarks_write"),
    @SerialName("bookmarks_admin") BOOKMARKS_ADMIN("bookmarks_admin"),

    @SerialName("board") BOARD_READ("board"),
    @SerialName("board_write") BOARD_WRITE("board_write"),
    @SerialName("board_admin") BOARD_ADMIN("board_admin"),

    @SerialName("board_pupil") BOARD_PUPIL_READ("board_pupil"),
    @SerialName("board_pupil_write") BOARD_PUPIL_WRITE("board_pupil_write"),
    @SerialName("board_pupil_admin") BOARD_PUPIL_ADMIN("board_pupil_admin"),

    @SerialName("board_teacher") BOARD_TEACHER_READ("board_teacher"),
    @SerialName("board_teacher_write") BOARD_TEACHER_WRITE("board_teacher_write"),
    @SerialName("board_teacher_admin") BOARD_TEACHER_ADMIN("board_teacher_admin"),

    @SerialName("tasks") TASKS_READ("tasks"),
    @SerialName("tasks_write") TASKS_WRITE("tasks_write"),
    @SerialName("tasks_admin") TASKS_ADMIN("tasks_admin"),

    @SerialName("courselets") COURSELETS_READ("courselets"),
    @SerialName("courselets_write") COURSELETS_WRITE("courselets_write"),
    @SerialName("courselets_admin") COURSELETS_ADMIN("courselets_admin"),

    @SerialName("forum") FORUM_READ("forum"),
    @SerialName("forum_write") FORUM_WRITE("forum_write"),
    @SerialName("forum_admin") FORUM_ADMIN("forum_admin"),

    @SerialName("wiki") WIKI_READ("wiki"),
    @SerialName("wiki_write") WIKI_WRITE("wiki_write"),
    @SerialName("wiki_admin") WIKI_ADMIN("wiki_admin"),

    @SerialName("resourcemanagement") RESOURCEMANAGEMENT_READ("resourcemanagement"),
    @SerialName("resourcemanagement_write") RESOURCEMANAGEMENT_WRITE("resourcemanagement_write"),
    @SerialName("resourcemanagement_admin") RESOURCEMANAGEMENT_ADMIN("resourcemanagement_admin"),

    @SerialName("files") FILES_READ("files"),
    @SerialName("files_write") FILES_WRITE("files_write"),
    @SerialName("files_admin") FILES_ADMIN("files_admin"),

    @SerialName("poll") POLL_READ("poll"),
    @SerialName("poll_write") POLL_WRITE("poll_write"),
    @SerialName("poll_admin") POLL_ADMIN("poll_admin"),

    @SerialName("timetable") TIMETABLE_READ("timetable"),
    @SerialName("timetable_write") TIMETABLE_WRITE("timetable_write"),
    @SerialName("timetable_admin") TIMETABLE_ADMIN("timetable_admin"),

    @SerialName("learning_plan") LEARNING_PLAN_READ("learning_plan"),
    @SerialName("learning_plan_write") LEARNING_PLAN_WRITE("learning_plan_write"),
    @SerialName("learning_plan_admin") LEARNING_PLAN_ADMIN("learning_plan_admin"),

    @SerialName("ws_gen") WS_GEN_READ("ws_gen"),
    @SerialName("ws_gen_write") WS_GEN_WRITE("ws_gen_write"),
    @SerialName("ws_gen_admin") WS_GEN_ADMIN("ws_gen_admin"),

    @SerialName("website") WEBSITE_READ("website"),
    @SerialName("website_write") WEBSITE_WRITE("website_write"),
    @SerialName("website_admin") WEBSITE_ADMIN("website_admin"),

    @SerialName("showcase") SHOWCASE_READ("showcase"),
    @SerialName("showcase_write") SHOWCASE_WRITE("showcase_write"),
    @SerialName("showcase_admin") SHOWCASE_ADMIN("showcase_admin"),

    @SerialName("blog") BLOG_READ("blog"),
    @SerialName("blog_write") BLOG_WRITE("blog_write"),
    @SerialName("blog_admin") BLOG_ADMIN("blog_admin"),

    @SerialName("meta") META_READ("meta"),
    @SerialName("meta_write") META_WRITE("meta_write"),
    @SerialName("meta_admin") META_ADMIN("meta_admin"),

    @SerialName("learning_log") LEARNING_LOG_READ("learning_log"),
    @SerialName("learning_log_write") LEARNING_LOG_WRITE("learning_log_write"),
    @SerialName("learning_log_admin") LEARNING_LOG_ADMIN("learning_log_admin"),

    @SerialName("wall") WALL_READ("wall"),
    @SerialName("wall_write") WALL_WRITE("wall_write"),
    @SerialName("wall_admin") WALL_ADMIN("wall_admin"),

    @SerialName("substitution_plan") SUBSTITUTION_PLAN_READ("substitution_plan"),
    @SerialName("substitution_plan_write") SUBSTITUTION_PLAN_WRITE("substitution_plan_write"),
    @SerialName("substitution_plan_admin") SUBSTITUTION_PLAN_ADMIN("substitution_plan_admin"),

    @SerialName("resource_management") RESOURCE_MANAGEMENT_READ("resource_management"),
    @SerialName("resource_management_write") RESOURCE_MANAGEMENT_WRITE("resource_management_write"),
    @SerialName("resource_management_admin") RESOURCE_MANAGEMENT_ADMIN("resource_management_admin"),

    @SerialName("consultation_hours") CONSULTATION_HOURS_READ("consultation_hours"),
    @SerialName("consultation_hours_write") CONSULTATION_HOURS_WRITE("consultation_hours_write"),
    @SerialName("consultation_hours_admin") CONSULTATION_HOURS_ADMIN("consultation_hours_admin"),

    @SerialName("images") IMAGES_READ("images"),
    @SerialName("images_write") IMAGES_WRITE("images_write"),
    @SerialName("images_admin") IMAGES_ADMIN("images_admin"),

    @SerialName("forms") FORMS_READ("forms"),
    @SerialName("forms_write") FORMS_WRITE("forms_write"),
    @SerialName("forms_admin") FORMS_ADMIN("forms_admin"),

    @SerialName("conference") CONFERENCE_READ("conference"),
    @SerialName("conference_write") CONFERENCE_WRITE("conference_write"),
    @SerialName("conference_admin") CONFERENCE_ADMIN("conference_admin"),

    @SerialName("client") CLIENT("client"),

    @SerialName("settings") SETTINGS("settings"),

    @SerialName("self") SELF("self"),

    @SerialName("profile") PROFILE_READ("profile"),
    @SerialName("profile_write") PROFILE_WRITE("profile_write"),
    @SerialName("profile_admin") PROFILE_ADMIN("profile_admin"),

    @SerialName("password") PASSWORD("password"),

    UNKNOWN("");

    companion object {
        fun getByName(name: String): Permission? {
            return values().firstOrNull { it.id == name }
        }
    }
}

object PermissionSerializer : KSerializer<Permission> {

    private val className = this::class.qualifiedName!!
    private val lookup = Permission.values().associateBy({ it }, { it.id })
    private val revLookup = Permission.values().associateBy { it.id }
    override val descriptor = PrimitiveSerialDescriptor(className, PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: Permission) = encoder.encodeString(lookup.getValue(value))
    override fun deserialize(decoder: Decoder) = revLookup.getOrDefault(decoder.decodeString(), Permission.UNKNOWN)

}