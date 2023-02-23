package de.deftk.openww.api.model.feature.messenger

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.RemoteScope
import de.deftk.openww.api.model.feature.filestorage.session.ISessionFile

interface IMessenger : IQuickMessageReceiver {

    suspend fun getUsers(getMiniatures: Boolean? = null, onlineOnly: Boolean? = null, context: IRequestContext): List<RemoteScope>
    suspend fun sendQuickMessage(login: String, importSessionFile: ISessionFile? = null, text: String? = null, context: IRequestContext): IQuickMessage
    suspend fun addChat(login: String, context: IRequestContext): RemoteScope
    suspend fun removeChat(login: String, context: IRequestContext): RemoteScope
    suspend fun getHistory(exportSessionFile: Boolean? = null, startId: Int? = null, context: IRequestContext): List<IQuickMessage>
    suspend fun blockUser(login: String, context: IRequestContext): RemoteScope
    suspend fun unblockUser(login: String, context: IRequestContext): RemoteScope
    suspend fun getBlockList(context: IRequestContext): List<RemoteScope>

}