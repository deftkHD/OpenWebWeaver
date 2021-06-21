package de.deftk.openww.api.model.feature.courselets

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TemplatePackage {
    @SerialName("offline")
    OFFLINE,

    @SerialName("scorm")
    SCORM
}