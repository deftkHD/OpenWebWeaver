package de.deftk.openww.api.implementation.feature.courselets

import de.deftk.openww.api.WebWeaverClient
import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.FileDownloadUrl
import de.deftk.openww.api.model.feature.Modification
import de.deftk.openww.api.model.feature.courselets.ICourselet
import de.deftk.openww.api.model.feature.courselets.TemplatePackage
import de.deftk.openww.api.request.GroupApiRequest
import de.deftk.openww.api.response.ResponseUtil
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement

@Serializable
class Courselet(
    override val id: Int,
    override val title: String,
    override val mapping: String,
    @SerialName("is_link")
    override val isLink: Boolean,
    @SerialName("is_visible")
    override val isVisible: Boolean,
    @SerialName("is_template")
    override val isTemplate: Boolean,
    override val size: Int,
    override val created: Modification,
    @SerialName("modified")
    private val _modified: Modification
) : ICourselet {

    @SerialName("_modified")
    override var modified: Modification = _modified
        private set

    override suspend fun setSuspendDate(suspendData: String, ifLatest: Int, context: IRequestContext) {
        val request = GroupApiRequest(context)
        request.addSetCourseletSuspendDataRequest(id, suspendData, ifLatest)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override suspend fun getResults(context: IRequestContext): CourseletData {
        val request = GroupApiRequest(context)
        val id = request.addGetCourseletResultsRequest(id)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["courselet"]!!)
    }

    override suspend fun deleteResults(context: IRequestContext) {
        val request = GroupApiRequest(context)
        request.addDeleteCourseletResultsRequest(id)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override suspend fun addBookmark(context: IRequestContext) {
        val request = GroupApiRequest(context)
        request.addAddCourseletBookmarkRequest(id)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override suspend fun deleteBookmark(context: IRequestContext) {
        val request = GroupApiRequest(context)
        request.addDeleteCourseletBookmarkRequest(id)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override suspend fun export(pkg: TemplatePackage?, context: IRequestContext): FileDownloadUrl {
        val request = GroupApiRequest(context)
        val id = request.addExportCourseletRequest(id.toString(), pkg)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override suspend fun delete(context: IRequestContext) {
        val request = GroupApiRequest(context)
        request.addDeleteCourseletRequest(id.toString())
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun toString(): String {
        return "Courselet(title='$title')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Courselet) return false

        if (id != other.id) return false
        if (title != other.title) return false
        if (mapping != other.mapping) return false
        if (isLink != other.isLink) return false
        if (isVisible != other.isVisible) return false
        if (isTemplate != other.isTemplate) return false
        if (size != other.size) return false
        if (created != other.created) return false
        if (_modified != other._modified) return false
        if (modified != other.modified) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + mapping.hashCode()
        result = 31 * result + isLink.hashCode()
        result = 31 * result + isVisible.hashCode()
        result = 31 * result + isTemplate.hashCode()
        result = 31 * result + size
        result = 31 * result + created.hashCode()
        result = 31 * result + _modified.hashCode()
        result = 31 * result + modified.hashCode()
        return result
    }

}