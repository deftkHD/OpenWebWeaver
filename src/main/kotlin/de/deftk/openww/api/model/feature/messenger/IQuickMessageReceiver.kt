package de.deftk.openww.api.model.feature.messenger

import de.deftk.openww.api.model.IRequestContext

interface IQuickMessageReceiver {

    fun readQuickMessages(exportSessionFile: Boolean? = null, context: IRequestContext): List<IQuickMessage>

}