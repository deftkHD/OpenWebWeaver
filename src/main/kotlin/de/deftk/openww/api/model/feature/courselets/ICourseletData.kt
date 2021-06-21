package de.deftk.openww.api.model.feature.courselets

import de.deftk.openww.api.model.RemoteScope

interface ICourseletData {

    val id: Int
    val title: String
    val user: RemoteScope
    val connection: ICourseletConnection

}