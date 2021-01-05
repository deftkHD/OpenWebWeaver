package de.deftk.lonet.api.model.feature.filestorage.filter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FileFilter(val searchTerm: String, val searchMode: SearchMode)

@Serializable
enum class SearchMode {
    @SerialName("word_equals")
    WORD_EQUALS,

    @SerialName("word_starts_with")
    WORD_STARTS_WITH,

    @SerialName("word_contains")
    WORD_CONTAINS,

    @SerialName("phrase")
    PHRASE

}