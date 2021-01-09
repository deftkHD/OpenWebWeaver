package de.deftk.lonet.api.implementation.feature.messenger

import de.deftk.lonet.api.model.RemoteScope
import de.deftk.lonet.api.model.feature.messenger.IQuickMessage
import de.deftk.lonet.api.serialization.DateFromStringSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class QuickMessage(
    override val id: Int,
    override val from: RemoteScope,
    override val to: RemoteScope,
    override val text: String,
    @Serializable(with = DateFromStringSerializer::class)
    override val date: Date,
    override val flags: String
): IQuickMessage