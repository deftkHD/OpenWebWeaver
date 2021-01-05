package de.deftk.lonet.api.model.feature

import de.deftk.lonet.api.model.RemoteScope
import de.deftk.lonet.api.serialization.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
class Modification(
    @SerialName("user")
    val member: RemoteScope,
    @Serializable(with = DateSerializer::class)
    val date: Date
)