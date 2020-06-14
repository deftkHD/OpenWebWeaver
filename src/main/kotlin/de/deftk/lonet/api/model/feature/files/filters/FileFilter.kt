package de.deftk.lonet.api.model.feature.files.filters

import de.deftk.lonet.api.model.feature.files.SearchMode

data class FileFilter(val searchTerm: String, val searchMode: SearchMode)