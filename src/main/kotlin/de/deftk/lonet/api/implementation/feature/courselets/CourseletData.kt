package de.deftk.lonet.api.implementation.feature.courselets

import de.deftk.lonet.api.model.RemoteScope
import de.deftk.lonet.api.model.feature.courselets.ICourseletData
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