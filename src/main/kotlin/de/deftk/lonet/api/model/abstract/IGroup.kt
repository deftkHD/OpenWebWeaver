package de.deftk.lonet.api.model.abstract

import de.deftk.lonet.api.model.feature.abstract.IForum
import de.deftk.lonet.api.model.feature.abstract.IMemberList
import de.deftk.lonet.api.model.feature.abstract.IBoard
import de.deftk.lonet.api.model.feature.abstract.IWiki

interface IGroup: IBoard, IForum, IMemberList, IWiki {
}