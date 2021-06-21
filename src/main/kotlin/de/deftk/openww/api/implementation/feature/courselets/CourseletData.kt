package de.deftk.openww.api.implementation.feature.courselets

import de.deftk.openww.api.model.RemoteScope
import de.deftk.openww.api.model.feature.courselets.ICourseletData
import kotlinx.serialization.Serializable


@Serializable
data class CourseletData(
    override val id: Int,
    override val title: String,
    override val user: RemoteScope,
    override val connection: CourseletConnection
) : ICourseletData {

    override fun toString(): String {
        return "CourseletData(title='$title')"
    }
}