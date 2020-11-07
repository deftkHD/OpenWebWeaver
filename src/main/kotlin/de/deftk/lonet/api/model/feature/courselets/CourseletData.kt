package de.deftk.lonet.api.model.feature.courselets

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Group
import de.deftk.lonet.api.model.abstract.IManageable

class CourseletData(val id: Int, val title: String, val user: IManageable, val connection: CourseletConnection, val courselet: Courselet, val group: Group) {

    companion object {
        fun fromJson(jsonObject: JsonObject, group: Group, courselet: Courselet): CourseletData {
            return CourseletData(
                    jsonObject.get("id").asInt,
                    jsonObject.get("title").asString,
                    group.getContext().getOrCreateManageable(jsonObject.get("user").asJsonObject),
                    CourseletConnection.fromJson(jsonObject.get("connection").asJsonObject, group, courselet),
                    courselet,
                    group
            )
        }
    }

}