package de.deftk.lonet.api.model.feature.filestorage

import de.deftk.lonet.api.model.RemoteScope
import de.deftk.lonet.api.serialization.BooleanFromIntSerializer
import kotlinx.serialization.Serializable

@Serializable
data class DownloadNotification(
    val users: List<RemoteScope> = emptyList(),
    @Serializable(with = BooleanFromIntSerializer::class)
    val me: Boolean
)