package de.deftk.openww.api.model.feature.filestorage

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.IUser
import de.deftk.openww.api.model.feature.FileDownloadUrl
import de.deftk.openww.api.model.feature.FilePreviewUrl
import de.deftk.openww.api.model.feature.IModifiable
import de.deftk.openww.api.model.feature.filestorage.io.FileChunk
import de.deftk.openww.api.model.feature.filestorage.proxy.ProxyNonce
import de.deftk.openww.api.model.feature.filestorage.session.ISessionFile

interface IRemoteFile: IRemoteFileProvider, IModifiable {

    val id: String
    val type: FileType

    val parentId: String?
    val ordinal: Int?
    val description: String?
    val size: Long
    val readable: Boolean?
    val writable: Boolean?
    val sparse: Boolean?
    val sparseKey: String?
    val preview: Boolean?
    val mine: Boolean?
    val shared: Boolean?
    val empty: Boolean?
    val effectiveRead: Boolean?
    val effectiveCreate: Boolean?
    val effectiveModify: Boolean?
    val effectiveDelete: Boolean?
    val downloadNotification: DownloadNotification?
    val aggregation: FileAggregation?

    suspend fun download(limit: Int? = null, offset: Int? = null, context: IRequestContext): FileChunk
    suspend fun getPreviewUrl(context: IRequestContext): FilePreviewUrl
    suspend fun getDownloadUrl(context: IRequestContext): FileDownloadUrl
    suspend fun getProxyNonce(context: IRequestContext): ProxyNonce
    suspend fun delete(context: IRequestContext)
    suspend fun setName(name: String, context: IRequestContext)
    suspend fun setDescription(description: String, context: IRequestContext)
    suspend fun setDownloadNotificationAddLogin(login: String, context: IRequestContext)
    suspend fun setDownloadNotificationDeleteLogin(login: String, context: IRequestContext)
    suspend fun setDownloadNotificationMe(receive: Boolean, context: IRequestContext)
    suspend fun setUploadNotificationAddLogin(login: String, context: IRequestContext)
    suspend fun setUploadNotificationDeleteLogin(login: String, context: IRequestContext)
    suspend fun setUploadNotificationMe(receive: Boolean, context: IRequestContext)
    suspend fun exportSessionFile(user: IUser, context: IRequestContext): ISessionFile

    suspend fun setFile(name: String, description: String?, downloadNotificationAddLogin: String?, downloadNotificationDeleteLogin: String?, downloadNotificationMe: Boolean?, context: IRequestContext)
    suspend fun setFolder(name: String, description: String?, readable: Boolean?, writable: Boolean?, uploadNotificationAddLogin: String?, uploadNotificationDeleteLogin: String?, uploadNotificationMe: Boolean?, context: IRequestContext)

}