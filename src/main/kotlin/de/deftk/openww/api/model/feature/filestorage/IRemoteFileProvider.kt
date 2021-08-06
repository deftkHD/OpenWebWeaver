package de.deftk.openww.api.model.feature.filestorage

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.filestorage.filter.FileFilter
import de.deftk.openww.api.model.feature.filestorage.session.ISessionFile

interface IRemoteFileProvider {

    val name: String

    suspend fun getRootFile(context: IRequestContext): IRemoteFile
    suspend fun getFiles(limit: Int? = null, offset: Int? = null, filter: FileFilter? = null, context: IRequestContext): List<IRemoteFile>
    suspend fun addFile(name: String, data: ByteArray, description: String? = null, context: IRequestContext): IRemoteFile
    suspend fun addSparseFile(name: String, size: Int, description: String? = null, context: IRequestContext): IRemoteFile
    suspend fun importSessionFile(sessionFile: ISessionFile, createCopy: Boolean? = null, description: String? = null, context: IRequestContext): IRemoteFile
    suspend fun addFolder(name: String, description: String? = null, context: IRequestContext): IRemoteFile

    suspend fun setReadable(readable: Boolean, context: IRequestContext)
    suspend fun setWritable(writable: Boolean, context: IRequestContext)

}