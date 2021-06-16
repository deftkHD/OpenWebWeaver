package de.deftk.lonet.api.model.feature.notes

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.IModifiable

interface INote: IModifiable {

    val id: String

    fun getTitle(): String
    fun getText(): String
    fun getColor(): NoteColor?

    fun edit(title: String? = null, text: String? = null, color: NoteColor? = null, context: IRequestContext)
    fun delete(context: IRequestContext)

}