package de.deftk.openww.api.implementation.feature.systemnotification

import de.deftk.openww.api.WebWeaverClient
import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.systemnotification.INotificationSetting
import de.deftk.openww.api.model.feature.systemnotification.NotificationFacility
import de.deftk.openww.api.model.feature.systemnotification.NotificationFacilityState
import de.deftk.openww.api.request.UserApiRequest
import de.deftk.openww.api.response.ResponseUtil
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.decodeFromJsonElement

@kotlinx.serialization.Serializable
class NotificationSetting(
    @SerialName("type")
    override val type: Int,
    @SerialName("object")
    override val obj: String,
    @SerialName("name")
    override val name: String,
    @SerialName("facilities")
    private val _facilities: NotificationFacilityState
) : INotificationSetting {

    @SerialName("_facilities")
    override var facilities: NotificationFacilityState = _facilities
        private set

    override suspend fun setFacilities(facilities: NotificationFacilityState, context: IRequestContext) {
        setFacilities(facilities.enabled, facilities.disabled, context)
    }

    override suspend fun setFacilities(enabled: List<NotificationFacility>, disabled: List<NotificationFacility>, context: IRequestContext) {
        val request = UserApiRequest(context)
        val id = request.addSetSystemNotificationFacilitiesRequest(
            type,
            getState(NotificationFacility.DIGEST),
            getState(NotificationFacility.DIGEST_WEEKLY),
            getState(NotificationFacility.MAIL),
            getState(NotificationFacility.NORMAL),
            getState(NotificationFacility.PUSH_NOTIFICATION),
            getState(NotificationFacility.QUICK_MESSAGE),
            getState(NotificationFacility.SMS)
        )
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        readFrom(WebWeaverClient.json.decodeFromJsonElement(subResponse["message"]!!))
    }

    private fun readFrom(notificationSetting: NotificationSetting) {
        facilities = notificationSetting.facilities
    }

    private fun getState(facility: NotificationFacility): Boolean? {
        return if (facilities.enabled.contains(facility)) {
            true
        } else if (facilities.disabled.contains(facility)) {
            false
        } else {
            null
        }
    }

    override fun toString(): String {
        return "NotificationSetting(type=$type, obj='$obj', name='$name', facilities=$facilities)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NotificationSetting) return false

        if (type != other.type) return false
        if (obj != other.obj) return false
        if (name != other.name) return false
        if (_facilities != other._facilities) return false
        if (facilities != other.facilities) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type
        result = 31 * result + obj.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + _facilities.hashCode()
        result = 31 * result + facilities.hashCode()
        return result
    }

}