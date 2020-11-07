package de.deftk.lonet.api.model

import java.io.Serializable

enum class Permission(val id: String): Serializable {
    SYSADMIN("sysadmin"),

    ADMIN("admin"),
    ADMIN_WRITE("admin_write"),
    ADMIN_ADMIN("admin_admin"),

    MESSENGER("messenger"),
    MESSENGER_WRITE("messenger_write"),
    MESSENGER_ADMIN("messenger_admin"),

    MAILBOX("mail"),
    MAILBOX_WRITE("mail_write"),
    MAILBOX_ADMIN("mail_admin"),

    MAILING_LISTS("mailing_lists"),
    MAILING_LISTS_WRITE("mailing_lists_write"),
    MAILING_LISTS_ADMIN("mailing_lists_admin"),

    MEMBERS("members"),
    MEMBERS_WRITE("members_write"),
    MEMBERS_ADMIN("members_admin"),

    ADDRESSES("address"),
    ADDRESSES_WRITE("address_write"),
    ADDRESSES_ADMIN("address_admin"),

    CALENDAR("calendar"),
    CALENDAR_WRITE("calendar_write"),
    CALENDAR_ADMIN("calendar_admin"),

    CHAT("chat"),
    CHAT_WRITE("chat_write"),
    CHAT_ADMIN("chat_admin"),

    NOTES("notes"),
    NOTES_WRITE("notes_write"),
    NOTES_ADMIN("notes_admin"),

    BOOKS("books"),
    BOOKS_WRITE("books_write"),
    BOOKS_ADMIN("books_admin"),

    BOOKMARKS("bookmarks"),
    BOOKMARKS_WRITE("bookmarks_write"),
    BOOKMARKS_ADMIN("bookmarks_admin"),

    BOARD("board"),
    BOARD_WRITE("board_write"),
    BOARD_ADMIN("board_admin"),

    BOARD_PUPIL("board_pupil"),
    BOARD_PUPIL_WRITE("board_pupil_write"),
    BOARD_PUPIL_ADMIN("board_pupil_admin"),

    TASKS("tasks"),
    TASKS_WRITE("tasks_write"),
    TASKS_ADMIN("tasks_admin"),

    COURSELETS("courselets"),
    COURSELETS_WRITE("courselets_write"),
    COURSELETS_ADMIN("courselets_admin"),

    FORUM("forum"),
    FORUM_WRITE("forum_write"),
    FORUM_ADMIN("forum_admin"),

    WIKI("wiki"),
    WIKI_WRITE("wiki_write"),
    WIKI_ADMIN("wiki_admin"),

    RESOURCEMANAGEMENT("resourcemanagement"),
    RESOURCEMANAGEMENT_WRITE("resourcemanagement_write"),
    RESOURCEMANAGEMENT_ADMIN("resourcemanagement_admin"),

    FILES("files"),
    FILES_WRITE("files_write"),
    FILES_ADMIN("files_admin"),

    POLL("poll"),
    POLL_WRITE("poll_write"),
    POLL_ADMIN("poll_admin"),

    TIMETABLE("timetable"),
    TIMETABLE_WRITE("timetable_write"),
    TIMETABLE_ADMIN("timetable_admin"),

    LEARNING_PLAN("learning_plan"),
    LEARNING_PLAN_WRITE("learning_plan_write"),
    LEARNING_PLAN_ADMIN("learning_plan_admin"),

    WS_GEN("ws_gen"),
    WS_GEN_WRITE("ws_gen_write"),
    WS_GEN_ADMIN("ws_gen_admin"),

    WEBSITE("website"),
    WEBSITE_WRITE("website_write"),
    WEBSITE_ADMIN("website_admin"),

    SHOWCASE("showcase"),
    SHOWCASE_WRITE("showcase_write"),
    SHOWCASE_ADMIN("showcase_admin"),

    BLOG("blog"),
    BLOG_WRITE("blog_write"),
    BLOG_ADMIN("blog_admin"),

    META("meta"),
    META_WRITE("meta_write"),
    META_ADMIN("meta_admin"),

    CLIENT("client"),

    SELF("self"),

    PASSWORD("password"),

    UNKNOWN("");

    companion object {
        @JvmStatic
        fun getByName(name: String): Permission {
            return values().firstOrNull { it.id == name } ?: UNKNOWN
        }
    }
}