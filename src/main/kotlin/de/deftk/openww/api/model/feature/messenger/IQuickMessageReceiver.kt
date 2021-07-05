package de.deftk.openww.api.model.feature.messenger

import de.deftk.openww.api.model.IRequestContext

interface IQuickMessageReceiver {

    suspend fun readQuickMessages(exportSessionFile: Boolean? = null, context: IRequestContext): List<IQuickMessage>

}