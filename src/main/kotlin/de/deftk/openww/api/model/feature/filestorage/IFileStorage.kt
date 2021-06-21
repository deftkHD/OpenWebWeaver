package de.deftk.openww.api.model.feature.filestorage

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.Quota

interface IFileStorage: IRemoteFileProvider {

    fun getFileStorageState(context: IRequestContext): Pair<FileStorageSettings, Quota>
    fun getTrash(limit: Int? = null, context: IRequestContext): List<IRemoteFileProvider>

}