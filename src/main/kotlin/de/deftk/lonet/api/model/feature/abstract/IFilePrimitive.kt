package de.deftk.lonet.api.model.feature.abstract

import de.deftk.lonet.api.model.feature.files.OnlineFile
import de.deftk.lonet.api.model.feature.files.filters.FileFilter

interface IFilePrimitive {

    fun getFileStorageFiles(filter: FileFilter? = null, overwriteCache: Boolean = false): List<OnlineFile>

    fun createFolder(name: String, description: String? = null): OnlineFile


}