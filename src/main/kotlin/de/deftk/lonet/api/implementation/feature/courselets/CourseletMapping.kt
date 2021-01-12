package de.deftk.lonet.api.implementation.feature.courselets

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.courselets.ICourseletMapping
import de.deftk.lonet.api.request.GroupApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

@Serializable
class CourseletMapping(
    override val id: Int,
    private var name: String
) : ICourseletMapping {

    override fun getName(): String = name

    override fun setName(name: String, context: IRequestContext) {
        val request = GroupApiRequest(context)
        val id = request.addSetCourseletMappingRequest(id, name)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(Json.decodeFromJsonElement(subResponse["mapping"]!!))
    }

    override fun delete(context: IRequestContext) {
        val request = GroupApiRequest(context)
        request.addDeleteCourseletMappingRequest(id)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    private fun readFrom(mapping: CourseletMapping) {
        name = mapping.name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CourseletMapping

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    override fun toString(): String {
        return "CourseletMapping(name='$name')"
    }

}