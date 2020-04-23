package de.deftk.lonet.api.model.feature

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.abstract.AbstractOperator
import de.deftk.lonet.api.model.abstract.IManageable
import java.io.Serializable
import java.util.*

class Notification(val id: String, val title: String?, val text: String?, val color: NotificationColor, val creationDate: Date, val creationMember: IManageable, val modificationDate: Date, val modificationMember: IManageable, val operator: AbstractOperator) : Serializable {

    companion object {
        fun fromJson(jsonObject: JsonObject, operator: AbstractOperator): Notification {
            val createdObject = jsonObject.get("created").asJsonObject
            val modifiedObject = jsonObject.get("modified").asJsonObject
            return Notification(
                    jsonObject.get("id").asString,
                    jsonObject.get("title")?.asString,
                    jsonObject.get("text")?.asString,
                    NotificationColor.getById(jsonObject.get("color").asInt),
                    Date(createdObject.get("date").asLong * 1000),
                    operator.getContext().getOrCreateManageable(createdObject.get("user").asJsonObject),
                    Date(modifiedObject.get("date").asLong * 1000),
                    operator.getContext().getOrCreateManageable(modifiedObject.get("user").asJsonObject),
                    operator
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

    enum class NotificationColor(val id: Int) : Serializable {
        BLUE(0),
        GREEN(1),
        RED(2),
        YELLOW(3),
        WHITE(4),
        UNKNOWN(-1);

        companion object {
            fun getById(id: Int) = values().firstOrNull { it.id == id } ?: UNKNOWN
        }
    }

}