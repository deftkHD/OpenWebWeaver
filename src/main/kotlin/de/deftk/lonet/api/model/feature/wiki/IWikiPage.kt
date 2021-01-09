package de.deftk.lonet.api.model.feature.wiki

interface IWikiPage {

    val name: String
    val exists: Boolean
    val source: String

}