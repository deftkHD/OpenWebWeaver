package de.deftk.openww.api.model.feature.forum

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.Quota

interface IForum {

    suspend fun getForumState(context: IRequestContext): Pair<Quota, ForumSettings>
    suspend fun getForumPosts(parentId: String? = null, context: IRequestContext): List<IForumPost>
    suspend fun addForumPost(
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