package de.deftk.lonet.api.serialization

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ApiDate(@Serializable(with = DateSerializer::class) val date: Date)