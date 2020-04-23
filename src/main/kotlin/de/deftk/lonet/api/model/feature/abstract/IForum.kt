package de.deftk.lonet.api.model.feature.abstract

import de.deftk.lonet.api.model.feature.Quota
import de.deftk.lonet.api.model.feature.forum.ForumPost
import de.deftk.lonet.api.model.feature.forum.ForumSettings

interface IForum {

    fun getForumState(overwriteCache: Boolean = false): Pair<Quota, ForumSettings>
    fun getForumPosts(parentId: String? = null, overwriteCache: Boolean = false): List<ForumPost>

}