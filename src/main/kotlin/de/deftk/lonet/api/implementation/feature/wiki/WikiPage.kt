package de.deftk.lonet.api.implementation.feature.wiki

import de.deftk.lonet.api.model.feature.wiki.IWikiPage
import kotlinx.serialization.Serializable

@Serializable
data class WikiPage(
    private val name: String,
    private val exists: Boolean,
    private val source: String
) : IWikiPage {
    override fun getName(): String = name
    override fun exists(): Boolean = exists
    override fun getSource(): String = source
}