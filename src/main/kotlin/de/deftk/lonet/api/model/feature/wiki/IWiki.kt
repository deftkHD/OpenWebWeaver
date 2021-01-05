package de.deftk.lonet.api.model.feature.wiki

import de.deftk.lonet.api.model.IRequestContext

interface IWiki {

    fun getWikiPage(name: String?, context: IRequestContext): IWikiPage?

}