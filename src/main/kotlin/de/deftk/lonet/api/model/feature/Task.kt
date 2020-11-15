package de.deftk.lonet.api.model.feature

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.abstract.AbstractOperator
import de.deftk.lonet.api.model.abstract.IManageable
import de.deftk.lonet.api.request.OperatorApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import de.deftk.lonet.api.utils.*
import java.io.Serializable
import java.util.*

class Task(val id: String, title: String?, description: String?, startDate: Date?, endDate: Date?, completed: Boolean, creationDate: Date, creationMember: IManageable, modificationDate: Date, modificationMember: IManageable, val operator: AbstractOperator) : Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject, operator: AbstractOperator): Task {
            val createdObject = jsonObject.get("created").asJsonObject
            val modifiedObject = jsonObject.get("modified").asJsonObject
            val task = Task(
                    jsonObject.get("id").asString,
                    null,
                    null,
                    null,
                    null,
                    jsonObject.getBoolOrNull("completed")!!,
                    createdObject.getApiDate("date"),
                    createdObject.getManageable("user", operator),
                    modifiedObject.getApiDate("date"),
                    modifiedObject.getManageable("user", operator),
                    operator
            )
            task.readFrom(jsonObject)
            return task
        }
    }

    var title = title
        private set
    var description = description
        private set
    var startDate = startDate
        private set
    var endDate = endDate
        private set
    var completed = completed
        private set
    var creationDate = creationDate
        private set
    var creationMember = creationMember
        private set
    var modificationDate = modificationDate
        private set
    var modificationMember = modificationMember
        private set


    fun edit(completed: Boolean? = null, description: String? = null, dueDate: Date? = null, startDate: Date? = null, title: String? = null) {
        val request = OperatorApiRequest(operator)
        val id = request.addSetTaskRequest(id, completed, description, dueDate, startDate, title)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(subResponse.get("entry").asJsonObject)
    }

    fun delete() {
        val request = OperatorApiRequest(operator)
        request.addDeleteTaskRequest(id)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    private fun readFrom(jsonObject: JsonObject) {
        title = jsonObject.getStringOrNull("title")
        description = jsonObject.getStringOrNull("description")
        startDate = jsonObject.getApiDateOrNull("start_date")
        endDate = jsonObject.getApiDateOrNull("due_date")
        completed = jsonObject.getBoolOrNull("completed")!!

        val createdObject = jsonObject.get("created").asJsonObject
        val modifiedObject = jsonObject.get("modified").asJsonObject

        creationDate = createdObject.getApiDate("date")
        creationMember = createdObject.getManageable("user", operator)
        modificationDate = modifiedObject.getApiDate("date")
        modificationMember = modifiedObject.getManageable("user", operator)
    }

    override fun toString(): String {
        return title ?: id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}