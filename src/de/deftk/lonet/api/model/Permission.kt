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
    MEMBERS(false, "members"),
    MEMBERS_WRITE(false, "members_write"),
    ADDRESSES(false, "address"),
    ADDRESSES_WRITE(false, "address_write"),
    CALENDAR(false, "calendar"),
    CALENDAR_WRITE(false, "calender_write"),
    NOTES(false, "notes"),
    NOTES_WRITE(false, "notes_write"),
    BOARD(false, "board"),
    BOARD_WRITE(false, "board_write"),
    BOARD_TEACHER(false, "board"),
    BOARD_TEACHER_WRITE(false, "board_write"),
    BOARD_PUPIL(false, "board"),
    BOARD_PUPIL_WRITE(false, "board_write"),
    TASKS(true, "tasks"),
    TASKS_WRITE(false, "tasks_write"),
    COURSELETS(false, "courselets"),
    COURSELETS_WRITE(false, "courselets_write"),
    FORUM(false, "forum"),
    FORUM_WRITE(false, "forum_write"),
    WIKI(false, "wiki"),
    RESOURCEMANAGEMENT(false, "resourcemanagement"),
    RESOURCEMANAGEMENT_WRITE(false, "resourcemanagement_write"),
    RESOURCEMANAGEMENT_ADMIN(false, "resourcemanagement_admin"),
    FILES(false, "files"),
    FILES_ADMIN(false, "files_admin"),
    SESSION_FILES(false, "self"),
    LICENSES(false, "self"),
    PROFILE(false, "self"),
    MESSAGES(true, "self"),
    SETTINGS(false, "self"),
    PROXY(false, "self"),
    TRUSTS(false, "self"),
    PASSWORD(false, "password"),
    UNKNOWN(true, "");

    companion object {
        @JvmStatic
        fun getByName(name: String): List<Permission> {
            val list = values().filter { it.permission == name }
            return if(list.isEmpty())
                listOf(UNKNOWN)
            else list
        }
    }
}