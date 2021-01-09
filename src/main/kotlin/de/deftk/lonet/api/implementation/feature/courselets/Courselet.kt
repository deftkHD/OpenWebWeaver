package de.deftk.lonet.api.implementation.feature.courselets

import de.deftk.lonet.api.LoNetClient
import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.FileDownloadUrl
import de.deftk.lonet.api.model.feature.Modification
import de.deftk.lonet.api.model.feature.courselets.ICourselet
import de.deftk.lonet.api.model.feature.courselets.TemplatePackage
import de.deftk.lonet.api.request.GroupApiRequest
import de.deftk.lonet.api.response.ResponseUtil
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
    private val modified: Modification
): ICourselet {

    override fun getModified(): Modification = modified

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
        return LoNetClient.json.decodeFromJsonElement(subResponse["courselet"]!!)
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
        return LoNetClient.json.decodeFromJsonElement(subResponse["file"]!!)
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

}