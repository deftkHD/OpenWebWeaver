package de.deftk.openww.api.implementation.feature.tasks

import de.deftk.openww.api.WebWeaverClient
import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.Modification
import de.deftk.openww.api.model.feature.tasks.ITask
import de.deftk.openww.api.request.OperatingScopeApiRequest
import de.deftk.openww.api.response.ResponseUtil
import de.deftk.openww.api.serialization.BooleanFromIntSerializer
import de.deftk.openww.api.serialization.NullableDateFromStringSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement
import java.util.*

@Serializable
class Task(
    override val id: String,
    private var title: String,
    private var description: String? = null,
    @SerialName("start_date")
    @Serializable(with = NullableDateFromStringSerializer::class)
    private var startDate: Date? = null,
    @SerialName("due_date")
    @Serializable(with = NullableDateFromStringSerializer::class)
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

    override fun setTitle(title: String, context: IRequestContext) = edit(title, description, completed, startDate, endDate, context)
    override fun setDescription(description: String, context: IRequestContext) = edit(title, description, completed, startDate, endDate, context)
    override fun setStartDate(startDate: Date, context: IRequestContext) = edit(title, description, completed, startDate, endDate, context)
    override fun setEndDate(endDate: Date, context: IRequestContext) = edit(title, description, completed, startDate, endDate, context)
    override fun setCompleted(completed: Boolean, context: IRequestContext) = edit(title, description, completed, startDate, endDate, context)

    override fun edit(title: String, description: String?, completed: Boolean?, startDate: Date?, endDate: Date?, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        val id = request.addSetTaskRequest(id, completed, description, endDate?.time, startDate?.time, title)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse["entry"]!!))
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