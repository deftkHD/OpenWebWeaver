package de.deftk.lonet.api.model.feature.abstract

import de.deftk.lonet.api.model.Member
import de.deftk.lonet.api.model.User

interface IMemberList {

    fun getMembers(user: User, overwriteCache: Boolean = false): List<Member>

}