package de.deftk.lonet.api.model.feature.messenger

import de.deftk.lonet.api.model.IRequestContext

interface IMessenger {

    fun readQuickMessages(exportSessionFile: Boolean? = null, context: IRequestContext): List<IQuickMessage>

}