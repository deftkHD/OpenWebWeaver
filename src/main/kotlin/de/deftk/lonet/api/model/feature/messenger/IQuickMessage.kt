package de.deftk.lonet.api.model.feature.messenger

import de.deftk.lonet.api.model.RemoteScope
import java.util.*

interface IQuickMessage {

    val id: Int
    val from: RemoteScope
    val to: RemoteScope
    val text: String
    val date: Date
    val flags: String

}