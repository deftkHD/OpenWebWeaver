package de.deftk.openww.api.implementation.feature.forum

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.Modification
import de.deftk.openww.api.model.feature.forum.ForumPostIcon
import de.deftk.openww.api.model.feature.forum.IForumPost
import de.deftk.openww.api.request.GroupApiRequest
import de.deftk.openww.api.response.ResponseUtil
import de.deftk.openww.api.serialization.BooleanFromIntSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
class ForumPost(
    override val id: String,
    @SerialName("parent_id")
    override val parentId: String,
    override val title: String,
    override val text: String,
    override val icon: ForumPostIcon? = null,
    override val level: Int,
    @Serializable(with = BooleanFromIntSerializer::class)
    @SerialName("pinned")
    override val isPinned: Boolean? = null,
    @Serializable(with = BooleanFromIntSerializer::class)
    @SerialName("locked")
    override val isLocked: Boolean? = null,
    private val children: ChildrenData? = null,
    private val files: List<JsonElement> = emptyList(),
    override val created: Modification,
    @SerialName("modified")
    private val _modified: Modification
): IForumPost {

    private val comments = mutableListOf<ForumPost>()

    var deleted = false
        private set

    @SerialName("_modified")
    override var modified: Modification = _modified
        private set

    override fun getComments(): List<IForumPost> = comments

    override fun commentLoaded(comment: IForumPost) {
        check(comment is ForumPost)
        comments.add(comment)
    }

    override suspend fun delete(context: IRequestContext) {
        val request = GroupApiRequest(context)
        request.addDeleteForumPostRequest(id)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
        deleted = true
    }

    override fun toString(): String {
        return "ForumPost(title='$title')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ForumPost) return false

        if (id != other.id) return false
        if (parentId != other.parentId) return false
        if (title != other.title) return false
        if (text != other.text) return false
        if (icon != other.icon) return false
        if (level != other.level) return false
        if (isPinned != other.isPinned) return false
        if (isLocked != other.isLocked) return false
        if (children != other.children) return false
        if (files != other.files) return false
        if (created != other.created) return false
        if (_modified != other._modified) return false
        if (comments != other.comments) return false
        if (deleted != other.deleted) return false
        if (modified != other.modified) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + parentId.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + (icon?.hashCode() ?: 0)
        result = 31 * result + level
        result = 31 * result + (isPinned?.hashCode() ?: 0)
        result = 31 * result + (isLocked?.hashCode() ?: 0)
        result = 31 * result + (children?.hashCode() ?: 0)
        result = 31 * result + files.hashCode()
        result = 31 * result + created.hashCode()
        result = 31 * result + _modified.hashCode()
        result = 31 * result + comments.hashCode()
        result = 31 * result + deleted.hashCode()
        result = 31 * result + modified.hashCode()
        return result
    }

    @Serializable
    data class ChildrenData(val count: Int, val recent: Modification? = null)

}