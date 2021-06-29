package de.deftk.openww.api.model.feature.notes

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.IModifiable

interface INote: IModifiable {

    val id: String

    fun getTitle(): String
    fun getText(): String
    fun getColor(): NoteColor?

    fun edit(title: String, text: String, color: NoteColor? = null, context: IRequestContext)
    fun delete(context: IRequestContext)

}