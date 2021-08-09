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

}