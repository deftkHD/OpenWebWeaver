package de.deftk.lonet.api.model

//TODO update
enum class Feature(val supported: Boolean, val permissions: List<Permission>) {
    STATISTICS(false, listOf(Permission.STATISTICS)),
    ADMINISTRATION_GROUP(false, listOf(Permission.ADMINISTRATION_GROUP, Permission.ADMINISTRATION_GROUP_ADMIN)),
    ADMINISTRATION_USER(false, listOf(Permission.ADMINISTRATION_USER, Permission.ADMINISTRATION_USER_ADMIN)),
    ADMINISTRATION_ROOT(false, listOf(Permission.ADMINISTRATION_ROOT, Permission.ADMINISTRATION_ROOT_WRITE)),
    MESSENGER(false, listOf(Permission.MESSENGER, Permission.MESSENGER_WRITE)),
    MAILBOX(false, listOf(Permission.MAILBOX, Permission.MAILBOX_WRITE)),
    MEMBERS(false, listOf(Permission.MEMBERS, Permission.MEMBERS_WRITE)),
    ADDRESSES(false, listOf(Permission.ADDRESSES, Permission.ADDRESSES_WRITE)),
    CALENDAR(false, listOf(Permission.CALENDAR, Permission.CALENDAR_WRITE)),
    NOTES(false, listOf(Permission.NOTES, Permission.NOTES_WRITE)),
    BOARD(false, listOf(Permission.BOARD, Permission.BOARD_WRITE, Permission.BOARD_PUPIL, Permission.BOARD_PUPIL_WRITE, Permission.BOARD_TEACHER, Permission.BOARD_TEACHER_WRITE)),
    TASKS(false, listOf(Permission.TASKS, Permission.TASKS_WRITE)),
    COURSELETS(false, listOf(Permission.COURSELETS, Permission.COURSELETS_WRITE)),
    FORUM(false, listOf(Permission.FORUM, Permission.FORUM_WRITE)),
    WIKI(false, listOf(Permission.WIKI)),
    RESOURCEMANAGEMENT(false, listOf(Permission.RESOURCEMANAGEMENT, Permission.RESOURCEMANAGEMENT_WRITE, Permission.RESOURCEMANAGEMENT_ADMIN)),
    FILES(false, listOf(Permission.FILES, Permission.FILES_WRITE, Permission.FILES_ADMIN)),
    SESSION_FILES(false, listOf(Permission.SESSION_FILES)),
    LICENSES(false, listOf(Permission.LICENSES)),
    PROFILE(false, listOf(Permission.PROFILE)),
    MESSAGES(false, listOf(Permission.MESSAGES)),
    SETTINGS(false, listOf(Permission.SETTINGS)),
    PROXY(false, listOf(Permission.PROXY)),
    TRUSTS(false, listOf(Permission.TRUSTS)),
    PASSWORD(false, listOf(Permission.PASSWORD));

    companion object {
        @JvmStatic
        fun getAvailableFeatures(permission: Permission): List<Feature> {
            return values().filter { it.permissions.contains(permission) }
        }
    }
}