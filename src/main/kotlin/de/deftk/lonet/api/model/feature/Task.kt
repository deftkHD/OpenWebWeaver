package de.deftk.lonet.api.model.feature

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Member
import java.io.Serializable
import java.util.*

class Task(val id: String, val title: String?, val description: String?, val startDate: Date?, val endDate: Date?, val completed: Boolean, val creationDate: Date, val creationMember: Member, val modificationDate: Date, val modificationMember: Member, val member: Member) : Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject, member: Member): Task {
            val createdObject = jsonObject.get("created").asJsonObject
            val modifiedObject = jsonObject.get("modified").asJsonObject
            return Task(
                    jsonObject.get("id").asString,
                    jsonObject.get("title")?.asString,
                    jsonObject.get("description")?.asString,
                    if (jsonObject.get("start_date").asInt != 0) Date(jsonObject.get("start_date").asLong * 1000) else null,
                    if (jsonObject.get("due_date").asInt != 0) Date(jsonObject.get("due_date").asLong * 1000) else null,
                    jsonObject.get("completed").asInt == 1,
                    Date(createdObject.get("date").asLong * 1000),
                    Member.fromJson(createdObject.get("user").asJsonObject, null),
                    Date(modifiedObject.get("date").asLong * 1000),
                    Member.fromJson(modifiedObject.get("user").asJsonObject, null),
                    member
            )
        }
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