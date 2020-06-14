package de.deftk.lonet.api.model.feature.contact

enum class Gender(val id: Int) {
    MALE(1),
    FEMALE(2),
    OTHER(3),
    UNKNOWN(-1);

    companion object {
        fun getById(id: Int): Gender {
            val gender = values().firstOrNull { it.id == id } ?: UNKNOWN
            if (gender == UNKNOWN && id != -1)
                println("Unknown gender: $id")
            return gender
        }
    }
}