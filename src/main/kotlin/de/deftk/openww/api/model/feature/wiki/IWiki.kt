package de.deftk.openww.api.model.feature.wiki

import de.deftk.openww.api.model.IRequestContext

interface IWiki {

    fun getWikiPage(name: String?, context: IRequestContext): IWikiPage?

}