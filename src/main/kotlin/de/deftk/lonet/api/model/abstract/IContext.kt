package de.deftk.lonet.api.model.abstract

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Group
import de.deftk.lonet.api.model.User
import java.io.Serializable

interface IContext: Serializable {

    fun getSessionId(): String
    fun setSessionId(sessionId: String)

    fun getUser(): User
    fun getGroups(): List<Group>
    fun getOperator(login: String): AbstractOperator?
    fun getOrCreateManageable(jsonObject: JsonObject): IManageable

    fun getRequestUrl(): String

}