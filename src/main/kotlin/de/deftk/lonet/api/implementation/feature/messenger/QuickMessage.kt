package de.deftk.lonet.api.implementation.feature.messenger

import de.deftk.lonet.api.model.RemoteScope
import de.deftk.lonet.api.model.feature.FileDownloadUrl
import de.deftk.lonet.api.model.feature.messenger.IQuickMessage
import de.deftk.lonet.api.serialization.DateFromStringSerializer
import kotlinx.serialization.SerialName
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
    override val flags: String,
    @SerialName("file_name")
    override val fileName: String? = null,
    override val file: FileDownloadUrl? = null
): IQuickMessage {

    override fun toString(): String {
        return "QuickMessage(from=$from, text='$text')"
    }
}