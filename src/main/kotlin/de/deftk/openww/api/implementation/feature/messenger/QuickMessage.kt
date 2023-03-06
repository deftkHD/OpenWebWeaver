package de.deftk.openww.api.implementation.feature.messenger

import de.deftk.openww.api.model.RemoteScope
import de.deftk.openww.api.model.feature.FileDownloadUrl
import de.deftk.openww.api.model.feature.messenger.IQuickMessage
import de.deftk.openww.api.serialization.DateFromStringSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class QuickMessage(
    override val id: Int,
    override val from: RemoteScope,
    override val to: RemoteScope,
    override val text: String?,
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is QuickMessage) return false

        if (id != other.id) return false
        if (from != other.from) return false
        if (to != other.to) return false
        if (text != other.text) return false
        if (date != other.date) return false
        if (flags != other.flags) return false
        if (fileName != other.fileName) return false
        if (file != other.file) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + from.hashCode()
        result = 31 * result + to.hashCode()
        result = 31 * result + (text?.hashCode() ?: 0)
        result = 31 * result + date.hashCode()
        result = 31 * result + flags.hashCode()
        result = 31 * result + (fileName?.hashCode() ?: 0)
        result = 31 * result + (file?.hashCode() ?: 0)
        return result
    }

}