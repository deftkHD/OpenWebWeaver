package de.deftk.lonet.api.model.feature

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Member
import java.util.*

class OnlineFile(jsonObject: JsonObject) {

    private val id = jsonObject.get("id").asString
    private val parentId = jsonObject.get("parent_id").asString
    private val ordinal = jsonObject.get("ordinal").asInt
    private val name = jsonObject.get("name").asString
    private val description = jsonObject.get("description").asString
    private val type = jsonObject.get("type").asString
    private val size = jsonObject.get("size").asLong
    private val readable = jsonObject.get("readable").asInt == 1
    private val writable = jsonObject.get("writable").asInt == 1
    private val sparse = jsonObject.get("sparse").asInt == 1
    private val mine = jsonObject.get("mine").asInt == 1
    private val creationDate: Date
    private val creationMember: Member
    private val modificationDate: Date
    private val modificationMember: Member

    init {
        val createdObject = jsonObject.get("created").asJsonObject
        creationDate = Date(createdObject.get("date").asLong * 1000)
        creationMember = Member(createdObject.get("user").asJsonObject, null)

        val modifiedObject = jsonObject.get("modified").asJsonObject
        modificationDate = Date(modifiedObject.get("date").asLong * 1000)
        modificationMember = Member(modifiedObject.get("user").asJsonObject, null)
    }

    fun getDownloadUrl(sessionId: String) {
        TODO("not implemented yet")
    }

    fun delete(sessionId: String) {
        TODO("not implemented yet")
    }



}