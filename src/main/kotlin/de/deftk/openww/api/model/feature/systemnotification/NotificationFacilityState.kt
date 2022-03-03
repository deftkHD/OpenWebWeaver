package de.deftk.openww.api.model.feature.systemnotification

import kotlinx.serialization.Serializable

@Serializable
class NotificationFacilityState(val enabled: List<NotificationFacility>, val disabled: List<NotificationFacility>)