package de.deftk.lonet.api.model.feature

interface IModifiable {
    val created: Modification
    fun getModified(): Modification
}