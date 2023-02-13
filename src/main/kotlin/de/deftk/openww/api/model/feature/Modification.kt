package de.deftk.openww.api.model.feature

import de.deftk.openww.api.model.RemoteScope
import de.deftk.openww.api.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
class Modification(
    @SerialName("user")
    val member: RemoteScope,
    @Serializable(with = DateSerializer::class)
    val date: Date?
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Modification

        if (member != other.member) return false
        if (date != other.date) return false

        return true
    }

    override fun hashCode(): Int {
        var result = member.hashCode()
        result = 31 * result + date.hashCode()
        return result
    }
}