package de.deftk.openww.api.model.feature.filestorage.proxy

import kotlinx.serialization.Serializable

@Serializable
data class ProxyNonce(
    val name: String,
    val size: Long,
    val nonce: String
)