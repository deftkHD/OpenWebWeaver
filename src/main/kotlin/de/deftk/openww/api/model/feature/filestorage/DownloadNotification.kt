package de.deftk.openww.api.model.feature.filestorage

import de.deftk.openww.api.model.RemoteScope
import de.deftk.openww.api.serialization.BooleanFromIntSerializer
import kotlinx.serialization.Serializable

@Serializable
data class DownloadNotification(
    val users: List<RemoteScope> = emptyList(),
    @Serializable(with = BooleanFromIntSerializer::class)
    val me: Boolean
)