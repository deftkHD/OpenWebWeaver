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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CourseletData) return false

        if (id != other.id) return false
        if (title != other.title) return false
        if (user != other.user) return false
        if (connection != other.connection) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + user.hashCode()
        result = 31 * result + connection.hashCode()
        return result
    }

}