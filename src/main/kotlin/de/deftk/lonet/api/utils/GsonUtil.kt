package de.deftk.lonet.api.utils

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.abstract.AbstractOperator
import de.deftk.lonet.api.model.abstract.IManageable
import java.util.*

fun JsonObject.getStringOrNull(key: String): String? {
    if (has(key)) {
        val obj = get(key)
        return if (obj.isJsonNull) null else obj.asString
    }
    return null
}

fun JsonObject.getIntOrNull(key: String): Int? {
    if (has(key)) {
        val obj = get(key)
        return if (obj.isJsonNull) null else obj.asInt
    }
    return null
}

fun JsonObject.getApiDateOrNull(key: String): Date? {
    if (has(key)) {
        val obj = get(key)
        return if (obj.isJsonNull) null else Date(obj.asInt * 1000L)
    }
    return null
}

fun JsonObject.getApiDate(key: String): Date {
    return Date(get(key).asInt * 1000L)
}

fun JsonObject.getManageable(key: String, operator: AbstractOperator): IManageable {
    return operator.getContext().getOrCreateManageable(get(key).asJsonObject)
}

fun JsonObject.getBoolOrNull(key: String): Boolean? {
    if (has(key)) {
        val obj = get(key)
        if (obj.isJsonNull)
            return null
        return try {
            obj.asBoolean
        } catch (e: Exception) {
            try {
                obj.asString == "true"
            } catch (e: Exception) {
                obj.asInt == 1
            }
        }
    }
    return null
}