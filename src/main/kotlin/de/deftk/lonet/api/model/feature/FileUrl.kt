package de.deftk.lonet.api.model.feature

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FileDownloadUrl(val id: String? = null, val name: String, val size: Long? = null, @SerialName("download_url") val url: String)

@Serializable
data class FileUploadUrl(val id: String? = null, val name: String, val size: Long? = null, @SerialName("upload_url") val url: String)

@Serializable
data class FilePreviewUrl(val id: String? = null, val name: String, val size: Long? = null, @SerialName("preview_url") val url: String)