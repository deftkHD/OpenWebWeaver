package de.deftk.lonet.api.model.feature.filestorage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class FileType {

    @SerialName("file")
    FILE,

    @SerialName("folder")
    FOLDER

}