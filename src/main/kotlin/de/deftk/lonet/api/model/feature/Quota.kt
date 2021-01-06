package de.deftk.lonet.api.model.feature

import kotlinx.serialization.Serializable

@Serializable
data class Quota(
    val usage: Long,
    val free: Long,
    val limit: Long,
    val sequence: Int,
    val updated: Long,
    val version: Int
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Quota

        if (usage != other.usage) return false
        if (free != other.free) return false
        if (limit != other.limit) return false
        if (sequence != other.sequence) return false
        if (updated != other.updated) return false
        if (version != other.version) return false

        return true
    }

    override fun hashCode(): Int {
        var result = usage.hashCode()
        result = 31 * result + free.hashCode()
        result = 31 * result + limit.hashCode()
        result = 31 * result + sequence
        result = 31 * result + updated.hashCode()
        result = 31 * result + version
        return result
    }
}