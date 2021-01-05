package de.deftk.lonet.api.model.feature.filestorage

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.filestorage.filter.FileFilter
import de.deftk.lonet.api.model.feature.filestorage.session.ISessionFile

interface IRemoteFileProvider {

    val name: String

    fun getFiles(limit: Int? = null, offset: Int? = null, filter: FileFilter? = null, context: IRequestContext): List<IRemoteFile>
    fun addFile(name: String, data: ByteArray, description: String? = null, context: IRequestContext): IRemoteFile
    fun addSparseFile(name: String, size: Int, description: String? = null, context: IRequestContext): IRemoteFile
    fun importSessionFile(sessionFile: ISessionFile, createCopy: Boolean? = null, description: String? = null, context: IRequestContext): IRemoteFile
    fun addFolder(name: String, description: String? = null, context: IRequestContext): IRemoteFile

    fun setReadable(readable: Boolean, context: IRequestContext)
    fun setWritable(writable: Boolean, context: IRequestContext)

}