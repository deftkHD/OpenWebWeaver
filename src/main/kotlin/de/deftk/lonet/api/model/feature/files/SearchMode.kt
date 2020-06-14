package de.deftk.lonet.api.model.feature.files

import java.io.Serializable

enum class SearchMode(val id: String): Serializable {
    WORD_EQUALS("word_equals"),
    WORD_STARTS_WITH("word_starts_with"),
    WORD_CONTAINS("word_contains"),
    PHRASE("phrase");
}