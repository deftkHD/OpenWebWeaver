package de.deftk.openww.api.model.feature

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FileDownloadUrl(
    override val id: String? = null,
    override val name: String,
    override val size: Long? = null,
    @SerialName("download_url") override val url: String
) : FileUrl

@Serializable
data class FileUploadUrl(
    override val id: String? = null,
    override val name: String,
    override val size: Long? = null,
    @SerialName("upload_url") override val url: String
) : FileUrl

@Serializable
data class FilePreviewUrl(
    override val id: String? = null,
    override val name: String,
    override val size: Long? = null,
    @SerialName("preview_download_url") override val url: String
) : FileUrl

interface FileUrl {

    val id: String?
    val name: String?
    val size: Long?
    val url: String

}