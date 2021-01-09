package de.deftk.lonet.api.model.feature.filestorage.session

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.FileDownloadUrl
import de.deftk.lonet.api.model.feature.FileUploadUrl
import de.deftk.lonet.api.model.feature.filestorage.io.FileChunk

interface ISessionFile {

    val id: String
    val name: String

    fun getSize(): Int
    fun getDownloadUrl(): String

    fun append(data: ByteArray, context: IRequestContext)
    fun download(limit: Int? = null, offset: Int? = null, context: IRequestContext): FileChunk
    fun queryDownloadUrl(context: IRequestContext): FileDownloadUrl
    fun queryUploadUrl(context: IRequestContext): FileUploadUrl
    fun delete(context: IRequestContext)

}