package de.deftk.openww.api.model.feature.notes

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.IModifiable

interface INote: IModifiable {

    val id: String

    val title: String
    val text: String
    val color: NoteColor?

    suspend fun edit(title: String, text: String, color: NoteColor, context: IRequestContext)
    suspend fun delete(context: IRequestContext)

}