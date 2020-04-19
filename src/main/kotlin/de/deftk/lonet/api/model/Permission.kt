package de.deftk.lonet.api.model

enum class Permission(val supported: Boolean, val permission: String) {
    STATISTICS(false, "sysadmin"),
    ADMINISTRATION_GROUP(false, "admin"),
    ADMINISTRATION_GROUP_ADMIN(false, "admin_admin"),
    ADMINISTRATION_USER(false, "admin"),
    ADMINISTRATION_USER_ADMIN(false, "admin_admin"),
    ADMINISTRATION_ROOT(false, "admin"),
    ADMINISTRATION_ROOT_WRITE(false, "admin_write"),
    MESSENGER(false, "messenger"),
    MESSENGER_WRITE(false, "messenger_write"),
    MAILBOX(false, "mail"),
    MAILBOX_WRITE(false, "mail_write"),
    MAILBOX_ADMIN(false, "mail_admin"),
    MAILING_LISTS_ADMIN(false, "mailing_lists_admin"),
    MEMBERS(false, "members"),
    MEMBERS_WRITE(false, "members_write"),
    MEMBERS_ADMIN(false, "members_admin"),
    ADDRESSES(false, "address"),
    ADDRESSES_WRITE(false, "address_write"),
    ADDRESSES_ADMIN(false, "address_admin"),
    CALENDAR(false, "calendar"),
    CALENDAR_WRITE(false, "calendar_write"),
    CALENDAR_ADMIN(false, "calendar_admin"),
    CHAT(false, "chat"),
    CHAT_WRITE(false, "chat_write"),
    CHAT_ADMIN(false, "chat_admin"),
    NOTES(false, "notes"),
    NOTES_WRITE(false, "notes_write"),
    BOOKS_WRITE(false, "books_write"),
    BOOKMARKS(false, "bookmarks"),
    BOOKMARKS_ADMIN(false, "bookmarks_admin"),
    BOARD(false, "board"),
    BOARD_WRITE(false, "board_write"),
    BOARD_ADMIN(false, "board_admin"),
    BOARD_TEACHER(false, "board"),
    BOARD_TEACHER_WRITE(false, "board_write"),
    BOARD_PUPIL(false, "board_pupil"),
    BOARD_PUPIL_WRITE(false, "board_pupil_write"),
    TASKS(true, "tasks"),
    TASKS_WRITE(false, "tasks_write"),
    TASKS_ADMIN(false, "tasks_admin"),
    COURSELETS(false, "courselets"),
    COURSELETS_WRITE(false, "courselets_write"),
    COURSELETS_ADMIN(false, "courselets_admin"),
    FORUM(false, "forum"),
    FORUM_WRITE(false, "forum_write"),
    FORUM_ADMIN(false, "forum_admin"),
    WIKI(false, "wiki"),
    RESOURCEMANAGEMENT(false, "resourcemanagement"),
    RESOURCEMANAGEMENT_WRITE(false, "resourcemanagement_write"),
    RESOURCEMANAGEMENT_ADMIN(false, "resourcemanagement_admin"),
    FILES(false, "files"),
    FILES_WRITE(false, "files_write"),
    FILES_ADMIN(false, "files_admin"),
    POLL(false, "poll"),
    TIMETABLE(false, "timetable"),
    TIMETABLE_WRITE(false, "timetable_write"),
    LEARNING_PLAN(false, "learning_plan"),
    WS_GEN(false, "ws_gen"),
    WEBSITE(false, "website"),
    SHOWCASE(false, "showcase"),
    BLOG(false, "blog"),
    CLIENT(false, "client"),
    SESSION_FILES(false, "self"),
    LICENSES(false, "self"),
    PROFILE(false, "profile"),
    MESSAGES(true, "self"),
    SETTINGS(false, "settings"),
    PROXY(false, "self"),
    TRUSTS(false, "self"),
    PASSWORD(false, "password"),
    META(false, "meta"),
    UNKNOWN(true, "");

    companion object {
        @JvmStatic
        fun getByName(name: String): List<Permission> {
            val list = values().filter { it.permission == name }
            return if (list.isEmpty()) {
                println("unknown permission: $name")
                listOf(UNKNOWN)
            } else list
        }
    }
}