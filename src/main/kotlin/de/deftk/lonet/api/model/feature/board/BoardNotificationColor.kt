package de.deftk.lonet.api.model.feature.board

import java.io.Serializable

enum class BoardNotificationColor(val id: Int) : Serializable {
    BLUE(0),
    GREEN(1),
    RED(2),
    YELLOW(3),
    WHITE(4);

    companion object {
        fun getById(id: Int?) = values().firstOrNull { it.id == id }
    }
}