package de.deftk.lonet.api.model.feature.wiki

interface IWikiPage {

    fun getName(): String
    fun exists(): Boolean
    fun getSource(): String

}