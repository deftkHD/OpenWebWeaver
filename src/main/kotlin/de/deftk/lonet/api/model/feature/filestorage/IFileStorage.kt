package de.deftk.lonet.api.model.feature.filestorage

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.Quota

interface IFileStorage: IRemoteFileProvider {

    fun getFileStorageState(context: IRequestContext): Pair<FileStorageSettings, Quota>
    fun getTrash(limit: Int? = null, context: IRequestContext): List<IRemoteFileProvider>

}