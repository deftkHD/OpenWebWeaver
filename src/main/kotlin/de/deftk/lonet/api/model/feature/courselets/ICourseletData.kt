package de.deftk.lonet.api.model.feature.courselets

import de.deftk.lonet.api.model.RemoteScope

interface ICourseletData {

    val id: Int
    val title: String
    val user: RemoteScope
    val connection: ICourseletConnection

}