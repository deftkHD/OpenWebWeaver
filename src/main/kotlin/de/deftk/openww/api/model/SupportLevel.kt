package de.deftk.openww.api.model

/**
 * Status of a specific feature implementation. Used inside {@link de.deftk.openww.api.model.Feature Feature.kt}
 */
enum class SupportLevel {
    NOT_SUPPORTED,
    STABLE,
    UNFINISHED,
    UNSTABLE
}