package de.deftk.lonet.api.model.feature.forum

import java.io.Serializable

enum class ForumPostIcon(val id: Int) : Serializable {
    INFORMATION(0),
    HUMOR(1),
    QUESTION(2),
    ANSWER(3),
    UP_VOTE(4),
    DOWN_VOTE(5);

    companion object {
        fun getById(id: Int?): ForumPostIcon? {
            return values().firstOrNull { it.id == id }
        }
    }

}