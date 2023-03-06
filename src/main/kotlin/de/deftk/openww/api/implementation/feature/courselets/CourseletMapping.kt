package de.deftk.openww.api.implementation.feature.courselets

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.courselets.ICourseletMapping
import de.deftk.openww.api.request.GroupApiRequest
import de.deftk.openww.api.response.ResponseUtil
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

@Serializable
class CourseletMapping(
    override val id: Int,
    @SerialName("name")
    private var _name: String
) : ICourseletMapping {

    @SerialName("_name")
    override var name: String = _name

    override suspend fun setName(name: String, context: IRequestContext) {
        val request = GroupApiRequest(context)
        val id = request.addSetCourseletMappingRequest(id, name)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(Json.decodeFromJsonElement(subResponse["mapping"]!!))
    }

    override suspend fun delete(context: IRequestContext) {
        val request = GroupApiRequest(context)
        request.addDeleteCourseletMappingRequest(id)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    private fun readFrom(mapping: CourseletMapping) {
        name = mapping.name
    }

    override fun toString(): String {
        return "CourseletMapping(name='$name')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CourseletMapping) return false

        if (id != other.id) return false
        if (_name != other._name) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + _name.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

}