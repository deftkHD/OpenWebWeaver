package de.deftk.lonet.api.implementation.feature.courselets

import de.deftk.lonet.api.model.RemoteScope
import de.deftk.lonet.api.model.feature.courselets.ICourseletData
import kotlinx.serialization.Serializable


@Serializable
class CourseletData(
    private val id: Int,
    private val title: String,
    private val user: RemoteScope,
    private val connection: CourseletConnection
): ICourseletData {

    override fun getId(): Int = id
    override fun getTitle(): String = title
    override fun getUser(): RemoteScope = user
    override fun getConnection(): CourseletConnection = connection

}