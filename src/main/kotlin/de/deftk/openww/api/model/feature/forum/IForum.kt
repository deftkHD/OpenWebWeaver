package de.deftk.openww.api.model.feature.forum

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.Quota

interface IForum {

    fun getForumState(context: IRequestContext): Pair<Quota, ForumSettings>
    fun getForumPosts(parentId: String? = null, context: IRequestContext): List<IForumPost>
    fun addForumPost(
        title: String,
        text: String,
        icon: ForumPostIcon,
        parent: IForumPost? = null,
        importSessionFile: String? = null,
        importSessionFiles: Array<String>? = null,
        replyNotificationMe: Boolean? = null,
        context: IRequestContext
    ): IForumPost

}