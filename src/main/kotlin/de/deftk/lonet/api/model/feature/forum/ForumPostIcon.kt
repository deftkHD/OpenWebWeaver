package de.deftk.lonet.api.model.feature.forum

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ForumPostIcon {
    @SerialName("0")
    INFORMATION,

    @SerialName("1")
    HUMOR,

    @SerialName("2")
    QUESTION,

    @SerialName("3")
    ANSWER,

    @SerialName("4")
    UPVOTE,

    @SerialName("5")
    DOWNVOTE

}