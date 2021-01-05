package de.deftk.lonet.api.implementation.feature.courselets

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.FileDownloadUrl
import de.deftk.lonet.api.model.feature.Modification
import de.deftk.lonet.api.model.feature.courselets.ICourselet
import de.deftk.lonet.api.model.feature.courselets.TemplatePackage
import de.deftk.lonet.api.request.GroupApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

@Serializable
class Courselet(
    private val id: Int,
    private val title: String,
    private val mapping: String,
    @SerialName("is_link")
    private val isLink: Boolean,
    @SerialName("is_visible")
    private val isVisible: Boolean,
    @SerialName("is_template")
    private val isTemplate: Boolean,
    private val size: Int,
    private val created: Modification,
    private val modified: Modification
): ICourselet {

    override fun getCreated(): Modification = created
    override fun getModified(): Modification = modified
    override fun getId(): Int = id
    override fun getTitle(): String = title
    override fun getMapping(): String = mapping
    override fun isLink(): Boolean = isLink
    override fun isVisible(): Boolean = isVisible
    override fun isTemplate(): Boolean = isTemplate
    override fun getSize(): Int = size

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
        return Json.decodeFromJsonElement(subResponse["courselet"]!!)
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
        return Json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override fun delete(context: IRequestContext) {
        val request = GroupApiRequest(context)
        request.addDeleteCourseletRequest(id.toString())
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }
}