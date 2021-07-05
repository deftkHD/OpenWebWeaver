package de.deftk.openww.api.model.feature.notes

import de.deftk.openww.api.model.IRequestContext

interface INotebook {

    suspend fun getNotes(context: IRequestContext): List<INote>
    suspend fun addNote(note: INote, context: IRequestContext): INote
    suspend fun addNote(text: String, title: String, color: NoteColor? = null, context: IRequestContext): INote

}