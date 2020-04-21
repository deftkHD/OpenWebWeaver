package de.deftk.lonet.api.model

import java.io.Serializable

//TODO update
enum class Feature(val supported: Boolean, val permissions: List<Permission>): Serializable {
    STATISTICS(false, listOf(Permission.STATISTICS)),
    ADMINISTRATION_GROUP(false, listOf(Permission.ADMINISTRATION_GROUP, Permission.ADMINISTRATION_GROUP_ADMIN)),
    ADMINISTRATION_USER(false, listOf(Permission.ADMINISTRATION_USER, Permission.ADMINISTRATION_USER_ADMIN)),
    ADMINISTRATION_ROOT(false, listOf(Permission.ADMINISTRATION_ROOT, Permission.ADMINISTRATION_ROOT_WRITE)),
    MESSENGER(false, listOf(Permission.MESSENGER, Permission.MESSENGER_WRITE)),
    MAILBOX(false, listOf(Permission.MAILBOX, Permission.MAILBOX_WRITE, Permission.MAILBOX_ADMIN)),
    MEMBERS(false, listOf(Permission.MEMBERS, Permission.MEMBERS_WRITE, Permission.MEMBERS_ADMIN)),
    ADDRESSES(false, listOf(Permission.ADDRESSES, Permission.ADDRESSES_WRITE, Permission.ADDRESSES_ADMIN)),
    CALENDAR(false, listOf(Permission.CALENDAR, Permission.CALENDAR_WRITE, Permission.CALENDAR_ADMIN)),
    NOTES(false, listOf(Permission.NOTES, Permission.NOTES_WRITE)),
    BOARD(false, listOf(Permission.BOARD, Permission.BOARD_WRITE, Permission.BOARD_ADMIN, Permission.BOARD_PUPIL, Permission.BOARD_PUPIL_WRITE, Permission.BOARD_PUPIL_ADMIN, Permission.BOARD_TEACHER, Permission.BOARD_TEACHER_WRITE)),
    TASKS(false, listOf(Permission.TASKS, Permission.TASKS_WRITE, Permission.TASKS_ADMIN)),
    COURSELETS(false, listOf(Permission.COURSELETS, Permission.COURSELETS_WRITE, Permission.COURSELETS_ADMIN)),
    FORUM(false, listOf(Permission.FORUM, Permission.FORUM_WRITE, Permission.FORUM_ADMIN)),
    WIKI(false, listOf(Permission.WIKI, Permission.WIKI_ADMIN)),
    RESOURCEMANAGEMENT(false, listOf(Permission.RESOURCEMANAGEMENT, Permission.RESOURCEMANAGEMENT_WRITE, Permission.RESOURCEMANAGEMENT_ADMIN)),
    FILES(false, listOf(Permission.FILES, Permission.FILES_WRITE, Permission.FILES_ADMIN)),
    CHAT(false, listOf(Permission.CHAT, Permission.CHAT_WRITE, Permission.CHAT_ADMIN)),
    BLOG(false, listOf(Permission.BLOG, Permission.BLOG_ADMIN)),
    SHOWCASE(false, listOf(Permission.SHOWCASE, Permission.SHOWCASE_WRITE)),
    WEBSITE(false, listOf(Permission.WEBSITE, Permission.WEBSITE_ADMIN)),
    WS_GEN(false, listOf(Permission.WS_GEN, Permission.WS_GEN_ADMIN)),
    POLL(false, listOf(Permission.POLL, Permission.POLL_ADMIN)),
    LEARNING_PLAN(false, listOf(Permission.LEARNING_PLAN, Permission.LEARNING_PLAN_ADMIN)),
    TIMETABLE(false, listOf(Permission.TIMETABLE, Permission.TIMETABLE_WRITE)),
    BOOKS(false, listOf(Permission.BOOKS_WRITE)),
    BOOKMARKS(false, listOf(Permission.BOOKMARKS, Permission.BOOKMARKS_ADMIN)),
    MAILING_LIST(false, listOf(Permission.MAILING_LISTS_ADMIN)),
    SESSION_FILES(false, listOf(Permission.SESSION_FILES)),
    LICENSES(false, listOf(Permission.LICENSES)),
    PROFILE(false, listOf(Permission.PROFILE)),
    MESSAGES(false, listOf(Permission.MESSAGES)),
    SETTINGS(false, listOf(Permission.SETTINGS)),
    PROXY(false, listOf(Permission.PROXY)),
    TRUSTS(false, listOf(Permission.TRUSTS)),
    PASSWORD(false, listOf(Permission.PASSWORD)),
    META(false, listOf(Permission.META, Permission.META_ADMIN)),
    CLIENT(false, listOf(Permission.CLIENT));

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