package de.deftk.lonet.api.model.feature.courselets

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Group
import de.deftk.lonet.api.model.abstract.IManageable
import de.deftk.lonet.api.utils.getManageable

class CourseletData(val id: Int, val title: String, val user: IManageable, val connection: CourseletConnection, val courselet: Courselet, val group: Group) {

    companion object {
        fun fromJson(jsonObject: JsonObject, group: Group, courselet: Courselet): CourseletData {
            return CourseletData(
                    jsonObject.get("id").asInt,
                    jsonObject.get("title").asString,
                    jsonObject.getManageable("user", group),
                    CourseletConnection.fromJson(jsonObject.get("connection").asJsonObject, group, courselet),
                    courselet,
                    group
            )
        }
    }

}