package de.deftk.lonet.api.model

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.abstract.IManageable
import de.deftk.lonet.api.model.abstract.ManageableType

data class RemoteManageable(private val login: String, private val name: String, private val type: ManageableType, val isOnline: Boolean): IManageable {

    companion object {
        fun fromJson(jsonObject: JsonObject): RemoteManageable {
            return RemoteManageable(
                    jsonObject.get("login").asString,
                    jsonObject.get("name_hr").asString,
                    ManageableType.getById(jsonObject.get("type").asInt),
                    jsonObject.get("is_online")?.asInt == 1
            )
        }
    }

    override fun getLogin() = login
    override fun getName() = name
    override fun getType() = type

}