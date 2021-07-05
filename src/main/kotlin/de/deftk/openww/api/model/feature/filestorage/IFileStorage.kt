package de.deftk.openww.api.model.feature.filestorage

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.Quota

interface IFileStorage: IRemoteFileProvider {

    suspend fun getFileStorageState(context: IRequestContext): Pair<FileStorageSettings, Quota>
    suspend fun getTrash(limit: Int? = null, context: IRequestContext): List<IRemoteFileProvider>

}