package de.deftk.openww.api.model.feature

interface IModifiable {
    val created: Modification
    fun getModified(): Modification
}