package de.deftk.openww.api.model.feature.messenger

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.RemoteScope
import de.deftk.openww.api.model.feature.filestorage.session.ISessionFile

interface IMessenger : IQuickMessageReceiver {

    fun getUsers(getMiniatures: Boolean? = null, onlineOnly: Boolean? = null, context: IRequestContext): List<RemoteScope>

    fun sendQuickMessage(login: String, importSessionFile: ISessionFile? = null, text: String? = null, context: IRequestContext): IQuickMessage

    fun addChat(login: String, context: IRequestContext): RemoteScope

    fun removeChat(login: String, context: IRequestContext): RemoteScope

    fun getHistory(exportSessionFile: Boolean? = null, startId: Int? = null, context: IRequestContext): List<IQuickMessage>

}