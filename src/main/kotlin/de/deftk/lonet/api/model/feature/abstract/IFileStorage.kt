package de.deftk.lonet.api.model.feature.abstract

import de.deftk.lonet.api.model.User
import de.deftk.lonet.api.model.feature.Quota
import de.deftk.lonet.api.model.feature.files.FileStorageSettings
import de.deftk.lonet.api.model.feature.files.OnlineFile

interface IFileStorage {

    fun getFileStorageState(user: User, overwriteCache: Boolean = false): Pair<FileStorageSettings, Quota>
    fun getFileStorageFiles(user: User, overwriteCache: Boolean = false): List<OnlineFile>

}