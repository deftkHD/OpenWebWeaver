package de.deftk.openww.api.model.feature.forum

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.IModifiable

interface IForumPost: IModifiable {

    val id: String
    val parentId: String
    val title: String
    val text: String
    val icon: ForumPostIcon?
    val level: Int
    val isPinned: Boolean?
    val isLocked: Boolean?

    fun getComments(): List<IForumPost>
    fun commentLoaded(comment: IForumPost)

    fun delete(context: IRequestContext)

}