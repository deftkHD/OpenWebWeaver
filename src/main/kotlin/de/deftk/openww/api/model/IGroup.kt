package de.deftk.openww.api.model

import de.deftk.openww.api.model.feature.board.IBoard
import de.deftk.openww.api.model.feature.courselets.ICourselets
import de.deftk.openww.api.model.feature.forum.IForum
import de.deftk.openww.api.model.feature.messenger.IQuickMessageReceiver
import de.deftk.openww.api.model.feature.wiki.IWiki

interface IGroup: IOperatingScope, IBoard, IForum, IWiki, ICourselets, IQuickMessageReceiver {

    fun getReducedRights(): List<Permission>
    fun getMemberRights(): List<Permission>

    fun getMembers(miniatures: Boolean? = null, onlineOnly: Boolean? = null, context: IRequestContext): List<RemoteScope>

}