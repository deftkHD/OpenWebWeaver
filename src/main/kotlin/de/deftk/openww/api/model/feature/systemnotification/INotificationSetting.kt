package de.deftk.openww.api.model.feature.systemnotification

import de.deftk.openww.api.model.IRequestContext

interface INotificationSetting {

    val type: Int
    val obj: String
    val name: String
    val facilities: NotificationFacilityState

    suspend fun setFacilities(facilities: NotificationFacilityState, context: IRequestContext)
    suspend fun setFacilities(enabled: List<NotificationFacility>, disabled: List<NotificationFacility>, context: IRequestContext)

}