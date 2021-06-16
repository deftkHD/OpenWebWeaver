package de.deftk.lonet.api.model

import de.deftk.lonet.api.model.Permission.*
import de.deftk.lonet.api.model.SupportLevel.*

enum class Feature(val supportLevel: SupportLevel, vararg possiblePermissions: Permission) {
    STATISTICS(NOT_SUPPORTED, SYSADMIN),

    ADMINISTRATION_GROUP(NOT_SUPPORTED, ADMIN, ADMIN_WRITE, ADMIN_ADMIN),
    ADMINISTRATION_USER(NOT_SUPPORTED, ADMIN, ADMIN_WRITE, ADMIN_ADMIN),
    ADMINISTRATION_ROOT(NOT_SUPPORTED, ADMIN, ADMIN_WRITE, ADMIN_ADMIN),

    MESSENGER(STABLE, Permission.MESSENGER, MESSENGER_WRITE, MESSENGER_ADMIN),
    MAILBOX(STABLE, Permission.MAILBOX, MAILBOX_WRITE, MAILBOX_ADMIN),
    MEMBERS(STABLE, Permission.MEMBERS, MEMBERS_WRITE, MEMBERS_ADMIN),
    ADDRESSES(STABLE, Permission.ADDRESSES, ADDRESSES_WRITE, ADDRESSES_ADMIN),
    CALENDAR(STABLE, Permission.CALENDAR, CALENDAR_WRITE, CALENDAR_ADMIN),
    NOTES(STABLE, Permission.NOTES, NOTES_WRITE, NOTES_ADMIN),
    BOARD(STABLE, Permission.BOARD, BOARD_WRITE, BOARD_ADMIN),
    BOARD_PUPIL(STABLE, Permission.BOARD_PUPIL, BOARD_PUPIL_WRITE, BOARD_PUPIL_ADMIN),
    BOARD_TEACHER(STABLE, Permission.BOARD_TEACHER, BOARD_TEACHER_WRITE, BOARD_TEACHER_ADMIN),
    TASKS(STABLE, Permission.TASKS, TASKS_WRITE, TASKS_ADMIN),
    BOOKS(NOT_SUPPORTED, Permission.BOOKS, BOOKS_WRITE, BOOKS_ADMIN),
    BOOKMARKS(NOT_SUPPORTED, Permission.BOOKMARKS, BOOKMARKS_WRITE , BOOKMARKS_ADMIN),
    COURSELETS(UNFINISHED, Permission.COURSELETS, COURSELETS_WRITE, COURSELETS_ADMIN),
    FORUM(STABLE, Permission.FORUM, FORUM_WRITE, FORUM_ADMIN),
    WIKI(STABLE, Permission.WIKI, WIKI_WRITE, WIKI_ADMIN),
    RESOURCEMANAGEMENT(NOT_SUPPORTED, Permission.RESOURCEMANAGEMENT, RESOURCEMANAGEMENT_WRITE, RESOURCEMANAGEMENT_ADMIN),
    FILES(STABLE, Permission.FILES, FILES_WRITE, FILES_ADMIN),
    CHAT(NOT_SUPPORTED, Permission.CHAT, CHAT_WRITE, CHAT_ADMIN),
    BLOG(NOT_SUPPORTED, Permission.BLOG, BLOG_WRITE, BLOG_ADMIN),
    SHOWCASE(NOT_SUPPORTED, Permission.SHOWCASE, SHOWCASE_WRITE, SHOWCASE_ADMIN),
    WEBSITE(NOT_SUPPORTED, Permission.WEBSITE, WEBSITE_WRITE, WEBSITE_ADMIN),
    WS_GEN(NOT_SUPPORTED, Permission.WS_GEN, WS_GEN_WRITE, WS_GEN_ADMIN),
    POLL(NOT_SUPPORTED, Permission.POLL, POLL_WRITE, POLL_ADMIN),
    LEARNING_PLAN(NOT_SUPPORTED, Permission.LEARNING_PLAN, LEARNING_PLAN_WRITE, LEARNING_PLAN_ADMIN),
    TIMETABLE(NOT_SUPPORTED, Permission.TIMETABLE, TIMETABLE_WRITE, TIMETABLE_ADMIN),
    MAILING_LIST(NOT_SUPPORTED, MAILING_LISTS, MAILING_LISTS_WRITE, MAILING_LISTS_ADMIN),
    META(NOT_SUPPORTED, Permission.META, META_WRITE, META_ADMIN),
    LEARNING_LOG(NOT_SUPPORTED, Permission.LEARNING_LOG, LEARNING_LOG_WRITE, LEARNING_LOG_ADMIN),
    WALL(NOT_SUPPORTED, Permission.WALL, WALL_WRITE, WALL_ADMIN),
    SUBSTITUTION_PLAN(NOT_SUPPORTED, Permission.SUBSTITUTION_PLAN, SUBSTITUTION_PLAN_WRITE, SUBSTITUTION_PLAN_ADMIN),
    RESOURCE_MANAGEMENT(NOT_SUPPORTED, Permission.RESOURCE_MANAGEMENT, RESOURCE_MANAGEMENT_WRITE, RESOURCE_MANAGEMENT_ADMIN),
    CONSULTATION_HOURS(NOT_SUPPORTED, Permission.CONSULTATION_HOURS, CONSULTATION_HOURS_WRITE, CONSULTATION_HOURS_ADMIN),
    IMAGES(NOT_SUPPORTED, Permission.IMAGES, IMAGES_WRITE, IMAGES_ADMIN),
    FORMS(NOT_SUPPORTED, Permission.FORMS, FORMS_WRITE, FORMS_ADMIN),

    PROFILE(NOT_SUPPORTED, SELF),
    SESSION_FILES(STABLE, SELF),
    LICENSES(NOT_SUPPORTED, SELF),
    MESSAGES(STABLE, SELF),
    TRUSTS(STABLE, SELF),
    SETTINGS(STABLE, SELF),
    PROXY(NOT_SUPPORTED, SELF),

    PASSWORD(NOT_SUPPORTED, Permission.PASSWORD),
    CLIENT(STABLE, Permission.CLIENT);

    val permissions: Array<out Permission> = possiblePermissions

    fun isAvailable(permissions: List<Permission?>): Boolean {
        this.permissions.forEach { permission ->
            if (permissions.contains(permission))
                return true
        }
        return false
    }

    companion object {
        fun getAvailableFeatures(permission: Permission): List<Feature> {
            return values().filter { it.permissions.contains(permission) }
        }
    }
}