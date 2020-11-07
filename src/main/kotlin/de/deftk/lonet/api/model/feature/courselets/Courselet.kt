package de.deftk.lonet.api.model.feature.courselets

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Group
import de.deftk.lonet.api.model.abstract.IManageable
import de.deftk.lonet.api.request.GroupApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import java.util.*

class Courselet(val id: Int, val title: String, val mapping: String, val isLink: Boolean, val isVisible: Boolean, val isTemplate: Boolean, val size: Int, val creationDate: Date, val creationMember: IManageable, val modificationDate: Date, val modificationMember: IManageable, val group: Group) {

    companion object {
        fun fromJson(jsonObject: JsonObject, group: Group): Courselet {
            val createdObject = jsonObject.get("created").asJsonObject
            val modifiedObject = jsonObject.get("modified").asJsonObject
            return Courselet(
                    jsonObject.get("id").asInt,
                    jsonObject.get("title").asString,
                    jsonObject.get("mapping").asString,
                    jsonObject.get("is_link").asString == "true",
                    jsonObject.get("is_visible").asString == "true",
                    jsonObject.get("is_template").asString == "true",
                    jsonObject.get("size").asInt,
                    Date(createdObject.get("date").asInt * 1000L),
                    group.getContext().getOrCreateManageable(createdObject.get("user").asJsonObject),
                    Date(modifiedObject.get("date").asInt * 1000L),
                    group.getContext().getOrCreateManageable(modifiedObject.get("user").asJsonObject),
                    group
            )
        }
    }

    fun setSuspendData(suspendData: String, ifLatest: Int) {
        val request = GroupApiRequest(group)
        request.addSetCourseletSuspendDataRequest(id, suspendData, ifLatest)[1]
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    fun getResults(): CourseletData {
        val request = GroupApiRequest(group)
        val id = request.addGetCourseletResultsRequest(id)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return CourseletData.fromJson(subResponse.get("courselet").asJsonObject, group, this)
    }

    fun deleteResults() {
        val request = GroupApiRequest(group)
        request.addDeleteCourseletResultsRequest(id)[1]
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    fun addBookmark() {
        val request = GroupApiRequest(group)
        request.addAddCourseletBookmarkRequest(id)[1]
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    fun deleteBookmark() {
        val request = GroupApiRequest(group)
        request.addDeleteCourseletBookmarkRequest(id)[1]
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    fun export(pkg: TemplatePackage? = null): CourseletDownload {
        val request = GroupApiRequest(group)
        val id = request.addExportCourseletRequest(this.id.toString(), pkg)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return CourseletDownload.fromJson(subResponse.get("file").asJsonObject)
    }

    fun delete() {
        val request = GroupApiRequest(group)
        request.addDeleteCourseletRequest(this.id.toString())
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }


}