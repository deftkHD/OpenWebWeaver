package de.deftk.lonet.api.model.feature.courselets

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TemplatePackage {
    @SerialName("offline")
    OFFLINE,

    @SerialName("scorm")
    SCORM
}