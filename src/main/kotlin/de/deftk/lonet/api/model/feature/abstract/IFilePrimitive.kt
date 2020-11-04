package de.deftk.lonet.api.model.feature.abstract

import de.deftk.lonet.api.model.feature.files.OnlineFile
import de.deftk.lonet.api.model.feature.files.filters.FileFilter

interface IFilePrimitive {

    fun getFiles(filter: FileFilter? = null): List<OnlineFile>

    fun addFolder(name: String, description: String? = null): OnlineFile

    fun getTrash(limit: Int? = null): List<OnlineFile>


}