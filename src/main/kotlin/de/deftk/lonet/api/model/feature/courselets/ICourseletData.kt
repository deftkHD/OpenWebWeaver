package de.deftk.lonet.api.model.feature.courselets

import de.deftk.lonet.api.model.RemoteScope

interface ICourseletData {

    fun getId(): Int
    fun getTitle(): String
    fun getUser(): RemoteScope
    fun getConnection(): ICourseletConnection

}