package de.deftk.lonet.api.model

import de.deftk.lonet.api.model.feature.board.IBoard
import de.deftk.lonet.api.model.feature.courselets.ICourselets
import de.deftk.lonet.api.model.feature.forum.IForum
import de.deftk.lonet.api.model.feature.wiki.IWiki

interface IGroup: IOperatingScope, IBoard, IForum, IWiki, ICourselets {

    fun getReducedRights(): List<Permission>
    fun getMemberRights(): List<Permission>

    fun getMembers(context: IRequestContext): List<RemoteScope>

}