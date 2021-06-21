package de.deftk.openww.api.implementation.feature.wiki

import de.deftk.openww.api.model.feature.wiki.IWikiPage
import kotlinx.serialization.Serializable

@Serializable
data class WikiPage(
    override val name: String,
    override val exists: Boolean,
    override val source: String
) : IWikiPage {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WikiPage

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun toString(): String {
        return "WikiPage(name='$name')"
    }


}