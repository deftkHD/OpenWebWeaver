package de.deftk.openww.api.model.feature.filestorage.session

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.FileDownloadUrl
import de.deftk.openww.api.model.feature.FileUploadUrl
import de.deftk.openww.api.model.feature.filestorage.io.FileChunk

interface ISessionFile {

    val id: String
    val name: String

    val size: Int
    val downloadUrl: String

    suspend fun append(data: ByteArray, context: IRequestContext)
    suspend fun download(limit: Int? = null, offset: Int? = null, context: IRequestContext): FileChunk
    suspend fun queryDownloadUrl(context: IRequestContext): FileDownloadUrl
    suspend fun queryUploadUrl(context: IRequestContext): FileUploadUrl
    suspend fun delete(context: IRequestContext)

}