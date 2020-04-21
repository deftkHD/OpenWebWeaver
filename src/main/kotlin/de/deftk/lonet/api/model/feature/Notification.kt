package de.deftk.lonet.api.model.feature

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Member
import java.io.Serializable
import java.util.*

class Notification(jsonObject: JsonObject, val group: Member): Serializable {

    val id: String = jsonObject.get("id").asString
    val title: String? = jsonObject.get("title")?.asString
    val text: String? = jsonObject.get("text")?.asString
    val color = NotificationColor.getById(jsonObject.get("color").asInt)
    val creationDate: Date
    val creationMember: Member
    val modificationDate: Date
    val modificationMember: Member

    init {
        val createdObject = jsonObject.get("created").asJsonObject
        creationDate = Date(createdObject.get("date").asLong * 1000)
        creationMember = Member(createdObject.get("user").asJsonObject, null)
        val modifiedObject = jsonObject.get("modified").asJsonObject
        modificationDate = Date(modifiedObject.get("date").asLong * 1000)
        modificationMember = Member(modifiedObject.get("user").asJsonObject, null)
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

    enum class NotificationColor(val id: Int): Serializable {
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