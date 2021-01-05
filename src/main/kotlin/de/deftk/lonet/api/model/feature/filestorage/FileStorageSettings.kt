package de.deftk.lonet.api.model.feature.filestorage

import de.deftk.lonet.api.serialization.BooleanFromIntSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FileStorageSettings(
    @Serializable(with = BooleanFromIntSerializer::class)
    @SerialName("hide_old_versions")
    val hideOldVersions: Boolean
)
