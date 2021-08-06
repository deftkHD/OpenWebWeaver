package de.deftk.openww.api.model.feature.filestorage

import de.deftk.openww.api.serialization.BooleanFromIntSerializer
import de.deftk.openww.api.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class FileAggregation(
    @Serializable(with = BooleanFromIntSerializer::class)
    @SerialName("is_file")
    val isFile: Boolean,
    @Serializable(with = BooleanFromIntSerializer::class)
    @SerialName("is_folder")
    val isFolder: Boolean,
    val size: Int,
    @SerialName("newest_file")
    val newestFile: FileAggregationFilePreview? = null
)

@Serializable
data class FileAggregationFilePreview(
    val id: String,
    val created: WrappedDate
)

@Serializable
data class WrappedDate(
    @Serializable(with = DateSerializer::class)
    val date: Date
)