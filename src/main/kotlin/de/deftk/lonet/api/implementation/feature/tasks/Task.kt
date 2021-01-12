package de.deftk.lonet.api.implementation.feature.tasks

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.feature.Modification
import de.deftk.lonet.api.model.feature.tasks.ITask
import de.deftk.lonet.api.request.OperatingScopeApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import de.deftk.lonet.api.serialization.BooleanFromIntSerializer
import de.deftk.lonet.api.serialization.DateFromStringSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import java.util.*

@Serializable
class Task(
    override val id: String,
    private var title: String,
    private var description: String? = null,
    @SerialName("start_date")
    @Serializable(with = DateFromStringSerializer::class)
    private var startDate: Date? = null,
    @SerialName("due_date")
    @Serializable(with = DateFromStringSerializer::class)
    private var endDate: Date? = null,
    @Serializable(with = BooleanFromIntSerializer::class)
    private var completed: Boolean,
    override val created: Modification,
    private var modified: Modification
): ITask {

    var deleted = false
        private set

    override fun getModified(): Modification = modified
    override fun getTitle(): String = title
    override fun getDescription(): String? = description
    override fun getStartDate(): Date? = startDate
    override fun getEndDate(): Date? = endDate
    override fun isCompleted(): Boolean = completed

    override fun setTitle(title: String, context: IRequestContext) = edit(title = title, context = context)
    override fun setDescription(description: String, context: IRequestContext) = edit(description = description, context = context)
    override fun setStartDate(startDate: Date, context: IRequestContext) = edit(startDate = startDate, context = context)
    override fun setEndDate(endDate: Date, context: IRequestContext) = edit(endDate = endDate, context = context)
    override fun setCompleted(completed: Boolean, context: IRequestContext) = edit(completed = completed, context = context)

    override fun edit(title: String?, description: String?, completed: Boolean?, startDate: Date?, endDate: Date?, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        val id = request.addSetTaskRequest(id, completed, description, endDate?.time, startDate?.time, title)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(Json.decodeFromJsonElement(subResponse["entry"]!!))
    }

    override fun delete(context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        request.addDeleteTaskRequest(id)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
        deleted = true
    }

    private fun readFrom(task: Task) {
        title = task.title
        description = task.description
        startDate = task.startDate
        endDate = task.endDate
        completed = task.completed
        modified = task.modified
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

    override fun toString(): String {
        return "Task(title='$title')"
    }

}