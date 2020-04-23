package de.deftk.lonet.api.model.feature.abstract

import de.deftk.lonet.api.model.feature.files.OnlineFile

interface IFilePrimitive {

    fun getFileStorageFiles(overwriteCache: Boolean = false): List<OnlineFile>

    fun createFolder(name: String, description: String? = null): OnlineFile


}