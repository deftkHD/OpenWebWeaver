package de.deftk.openww.api.implementation.feature.wiki

import de.deftk.openww.api.model.feature.wiki.IWikiPage
import kotlinx.serialization.Serializable

@Serializable
data class WikiPage(
    override val name: String,
    override val exists: Boolean,
    override val source: String
) : IWikiPage {

    override fun toString(): String {
        return "WikiPage(name='$name')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is WikiPage) return false

        if (name != other.name) return false
        if (exists != other.exists) return false
        if (source != other.source) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + exists.hashCode()
        result = 31 * result + source.hashCode()
        return result
    }

}