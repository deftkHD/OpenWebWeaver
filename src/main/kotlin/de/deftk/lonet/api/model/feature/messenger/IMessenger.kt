package de.deftk.lonet.api.model.feature.messenger

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.RemoteScope
import de.deftk.lonet.api.model.feature.filestorage.session.ISessionFile

interface IMessenger : IQuickMessageReceiver {

    fun getUsers(getMiniatures: Boolean? = null, onlineOnly: Boolean? = null, context: IRequestContext): List<RemoteScope>

    fun sendQuickMessage(login: String, importSessionFile: ISessionFile? = null, text: String? = null, context: IRequestContext): IQuickMessage

    fun addChat(login: String, context: IRequestContext): RemoteScope

    fun removeChat(login: String, context: IRequestContext): RemoteScope

    fun getHistory(exportSessionFile: Boolean? = null, startId: Int? = null, context: IRequestContext): List<IQuickMessage>

}