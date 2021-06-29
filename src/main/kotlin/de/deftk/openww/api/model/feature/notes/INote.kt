package de.deftk.openww.api.model.feature.notes

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.IModifiable

interface INote: IModifiable {

    val id: String

    val title: String
    val text: String
    val color: NoteColor?

    fun edit(title: String? = null, text: String? = null, color: NoteColor? = null, context: IRequestContext)
    fun delete(context: IRequestContext)

}