package de.deftk.openww.api.model.feature.messenger

import de.deftk.openww.api.model.RemoteScope
import de.deftk.openww.api.model.feature.FileDownloadUrl
import java.util.*

interface IQuickMessage {

    val id: Int
    val from: RemoteScope
    val to: RemoteScope
    val text: String?
    val date: Date
    val flags: String
    val fileName: String?
    val file: FileDownloadUrl?

}