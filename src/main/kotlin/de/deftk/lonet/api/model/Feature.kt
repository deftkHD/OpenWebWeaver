package de.deftk.lonet.api.model

import java.io.Serializable

enum class Feature(val permissions: List<Permission>): Serializable {
    STATISTICS(listOf(Permission.STATISTICS)),
    ADMINISTRATION_GROUP(listOf(Permission.ADMINISTRATION_GROUP, Permission.ADMINISTRATION_GROUP_ADMIN)),
    ADMINISTRATION_USER(listOf(Permission.ADMINISTRATION_USER, Permission.ADMINISTRATION_USER_ADMIN)),
    ADMINISTRATION_ROOT(listOf(Permission.ADMINISTRATION_ROOT, Permission.ADMINISTRATION_ROOT_WRITE)),
    MESSENGER(listOf(Permission.MESSENGER, Permission.MESSENGER_WRITE)),
    MAILBOX(listOf(Permission.MAILBOX, Permission.MAILBOX_WRITE, Permission.MAILBOX_ADMIN)),
    MEMBERS(listOf(Permission.MEMBERS, Permission.MEMBERS_WRITE, Permission.MEMBERS_ADMIN)),
    ADDRESSES(listOf(Permission.ADDRESSES, Permission.ADDRESSES_WRITE, Permission.ADDRESSES_ADMIN)),
    CALENDAR(listOf(Permission.CALENDAR, Permission.CALENDAR_WRITE, Permission.CALENDAR_ADMIN)),
    NOTES(listOf(Permission.NOTES, Permission.NOTES_WRITE)),
    BOARD(listOf(Permission.BOARD, Permission.BOARD_WRITE, Permission.BOARD_ADMIN, Permission.BOARD_PUPIL, Permission.BOARD_PUPIL_WRITE, Permission.BOARD_PUPIL_ADMIN, Permission.BOARD_TEACHER, Permission.BOARD_TEACHER_WRITE)),
    TASKS(listOf(Permission.TASKS, Permission.TASKS_WRITE, Permission.TASKS_ADMIN)),
    COURSELETS(listOf(Permission.COURSELETS, Permission.COURSELETS_WRITE, Permission.COURSELETS_ADMIN)),
    FORUM(listOf(Permission.FORUM, Permission.FORUM_WRITE, Permission.FORUM_ADMIN)),
    WIKI(listOf(Permission.WIKI, Permission.WIKI_ADMIN)),
    RESOURCEMANAGEMENT(listOf(Permission.RESOURCEMANAGEMENT, Permission.RESOURCEMANAGEMENT_WRITE, Permission.RESOURCEMANAGEMENT_ADMIN)),
    FILES(listOf(Permission.FILES, Permission.FILES_WRITE, Permission.FILES_ADMIN)),
    CHAT(listOf(Permission.CHAT, Permission.CHAT_WRITE, Permission.CHAT_ADMIN)),
    BLOG(listOf(Permission.BLOG, Permission.BLOG_ADMIN)),
    SHOWCASE(listOf(Permission.SHOWCASE, Permission.SHOWCASE_WRITE)),
    WEBSITE(listOf(Permission.WEBSITE, Permission.WEBSITE_ADMIN)),
    WS_GEN(listOf(Permission.WS_GEN, Permission.WS_GEN_ADMIN)),
    POLL(listOf(Permission.POLL, Permission.POLL_ADMIN)),
    LEARNING_PLAN(listOf(Permission.LEARNING_PLAN, Permission.LEARNING_PLAN_ADMIN)),
    TIMETABLE(listOf(Permission.TIMETABLE, Permission.TIMETABLE_WRITE)),
    BOOKS(listOf(Permission.BOOKS_WRITE)),
    BOOKMARKS(listOf(Permission.BOOKMARKS, Permission.BOOKMARKS_ADMIN)),
    MAILING_LIST(listOf(Permission.MAILING_LISTS_ADMIN)),
    SESSION_FILES(listOf(Permission.SESSION_FILES)),
    LICENSES(listOf(Permission.LICENSES)),
    PROFILE(listOf(Permission.PROFILE)),
    MESSAGES(listOf(Permission.MESSAGES)),
    SETTINGS(listOf(Permission.SETTINGS)),
    PROXY(listOf(Permission.PROXY)),
    TRUSTS(listOf(Permission.TRUSTS)),
    PASSWORD(listOf(Permission.PASSWORD)),
    META(listOf(Permission.META, Permission.META_ADMIN)),
    CLIENT(listOf(Permission.CLIENT));

    fun isAvailable(permissions: List<Permission>): Boolean {
        this.permissions.forEach { permission ->
            if (permissions.contains(permission))
                return true
        }
        return false
    }

    companion object {
        @JvmStatic
        fun getAvailableFeatures(permission: Permission): List<Feature> {
            return values().filter { it.permissions.contains(permission) }
        }
    }
}