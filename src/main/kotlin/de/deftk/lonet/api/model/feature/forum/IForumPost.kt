package de.deftk.lonet.api.model.feature.forum

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.IModifiable

interface IForumPost: IModifiable {

    fun getId(): String
    fun getParentId(): String
    fun getTitle(): String
    fun getText(): String
    fun getIcon(): ForumPostIcon?
    fun getLevel(): Int
    fun getCommentCount(): Int?
    fun isPinned(): Boolean?
    fun isLocked(): Boolean?
    fun getComments(): MutableList<IForumPost>

    fun delete(context: IRequestContext)

}