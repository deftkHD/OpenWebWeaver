package de.deftk.openww.api.implementation.feature.notes

import de.deftk.openww.api.WebWeaverClient
import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.Modification
import de.deftk.openww.api.model.feature.notes.INote
import de.deftk.openww.api.model.feature.notes.NoteColor
import de.deftk.openww.api.request.UserApiRequest
import de.deftk.openww.api.response.ResponseUtil
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement

@Serializable
class Note(
    override val id: String,
    @SerialName("title")
    private var _title: String,
    @SerialName("text")
    private var _text: String,
    @SerialName("color")
    private var _color: NoteColor? = null,
    override val created: Modification,
    @SerialName("modified")
    private var _modified: Modification
) : INote {

    var deleted = false
        private set

    @SerialName("_modified")
    override var modified: Modification = _modified
        private set

    @SerialName("_title")
    override var title: String = _title
        private set

    @SerialName("_text")
    override var text: String = _text
        private set

    @SerialName("_color")
    override var color: NoteColor? = _color

    override suspend fun edit(title: String, text: String, color: NoteColor, context: IRequestContext) {
        val request = UserApiRequest(context)
        val id = request.addSetNoteRequest(id, text, title, color)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse["entry"]!!))
    }

    override suspend fun delete(context: IRequestContext) {
        val request = UserApiRequest(context)
        request.addDeleteNoteRequest(id)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
        deleted = true
    }

    private fun readFrom(note: Note) {
        title = note.title
        text = note.text
        color = note.color
        modified = note.modified
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Note

        if (id != other.id) return false
        if (created != other.created) return false
        if (modified != other.modified) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + created.hashCode()
        result = 31 * result + modified.hashCode()
        return result
    }


}