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

    override fun setSuspendDate(suspendData: String, ifLatest: Int, context: IRequestContext) {
        val request = GroupApiRequest(context)
        request.addSetCourseletSuspendDataRequest(id, suspendData, ifLatest)[1]
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun getResults(context: IRequestContext): CourseletData {
        val request = GroupApiRequest(context)
        val id = request.addGetCourseletResultsRequest(id)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["courselet"]!!)
    }

    override fun deleteResults(context: IRequestContext) {
        val request = GroupApiRequest(context)
        request.addDeleteCourseletResultsRequest(id)[1]
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun addBookmark(context: IRequestContext) {
        val request = GroupApiRequest(context)
        request.addAddCourseletBookmarkRequest(id)[1]
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun deleteBookmark(context: IRequestContext) {
        val request = GroupApiRequest(context)
        request.addDeleteCourseletBookmarkRequest(id)[1]
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun export(pkg: TemplatePackage?, context: IRequestContext): FileDownloadUrl {
        val request = GroupApiRequest(context)
        val id = request.addExportCourseletRequest(id.toString(), pkg)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override fun delete(context: IRequestContext) {
        val request = GroupApiRequest(context)
        request.addDeleteCourseletRequest(id.toString())
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Courselet

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    override fun toString(): String {
        return "Courselet(title='$title')"
    }

}