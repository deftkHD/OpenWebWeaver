package de.deftk.lonet.api.model.feature.notes

import de.deftk.lonet.api.model.IRequestContext

interface INotebook {

    fun getNotes(context: IRequestContext): List<INote>
    fun addNote(note: INote, context: IRequestContext): INote
    fun addNote(text: String, title: String, color: NoteColor? = null, context: IRequestContext): INote

}