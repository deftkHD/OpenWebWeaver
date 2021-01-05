package de.deftk.lonet.api.model.feature.forum

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForumSettings(
    @SerialName("create_thread")
    val createThread: String,
    @SerialName("alternate_view")
    val alternateView: Int
)