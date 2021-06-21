package de.deftk.openww.api.model.feature.filestorage

import de.deftk.openww.api.serialization.BooleanFromIntSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FileStorageSettings(
    @Serializable(with = BooleanFromIntSerializer::class)
    @SerialName("hide_old_versions")
    val hideOldVersions: Boolean
)
