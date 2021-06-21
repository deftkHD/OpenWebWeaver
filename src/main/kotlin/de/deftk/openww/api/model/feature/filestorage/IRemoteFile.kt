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

    fun getParentId(): String?
    fun getOrdinal(): Int?
    fun getDescription(): String?
    fun getSize(): Long
    fun isReadable(): Boolean?
    fun isWritable(): Boolean?
    fun isSparseFile(): Boolean?
    fun getSparseKey(): String?
    fun hasPreview(): Boolean?
    fun isMine(): Boolean?
    fun isShared(): Boolean?
    fun effectiveRead(): Boolean?
    fun effectiveCreate(): Boolean?
    fun effectiveModify(): Boolean?
    fun effectiveDelete(): Boolean?
    fun getDownloadNotification(): DownloadNotification?

    fun download(limit: Int? = null, offset: Int? = null, context: IRequestContext): FileChunk
    fun getPreviewUrl(context: IRequestContext): FilePreviewUrl
    fun getDownloadUrl(context: IRequestContext): FileDownloadUrl
    fun getProxyNonce(context: IRequestContext): ProxyNonce
    fun delete(context: IRequestContext)
    fun setName(name: String, context: IRequestContext)
    fun setDescription(description: String, context: IRequestContext)
    fun setDownloadNotificationAddLogin(login: String, context: IRequestContext)
    fun setDownloadNotificationDeleteLogin(login: String, context: IRequestContext)
    fun setDownloadNotificationMe(receive: Boolean, context: IRequestContext)
    fun setUploadNotificationAddLogin(login: String, context: IRequestContext)
    fun setUploadNotificationDeleteLogin(login: String, context: IRequestContext)
    fun setUploadNotificationMe(receive: Boolean, context: IRequestContext)
    fun exportSessionFile(user: IUser, context: IRequestContext): ISessionFile

}