package de.deftk.lonet.api.model.feature

interface IModifiable {
    fun getCreated(): Modification
    fun getModified(): Modification
}