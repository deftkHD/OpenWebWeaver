package de.deftk.openww.api.implementation

import de.deftk.openww.api.model.*
import de.deftk.openww.api.request.handler.IRequestHandler

class ApiContext(
    override val sessionId: String,
    override val serverVersion: String,
    override val clientVersion: String,
    override val clientUrl: String,
    override val postMaxSize: Int,
    override val timezone: String,
    override val locale: String,
    override val info: String,
    override val customTranslationsUrl: String,
    override val customSkinningUrl: String,
    override val adminTypes: List<Int>,
    override val customOptions: String,
    override val user: User,
    private val groups: List<Group>,
    override val requestUrl: String,
    override var requestHandler: IRequestHandler
) : IApiContext {

    override fun findOperatingScope(login: String): OperatingScope? {
        return groups.firstOrNull { it.login == login } ?: if (login == user.login) user else null
    }

    override fun mapOperatingScope(scope: RemoteScope): IScope {
        return findOperatingScope(scope.login) ?: scope
    }

    override fun userContext(): IRequestContext = user.getRequestContext(this)

    override fun requestContext(scope: IOperatingScope): IRequestContext = scope.getRequestContext(this)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ApiContext) return false

        if (sessionId != other.sessionId) return false
        if (serverVersion != other.serverVersion) return false
        if (clientVersion != other.clientVersion) return false
        if (clientUrl != other.clientUrl) return false
        if (postMaxSize != other.postMaxSize) return false
        if (timezone != other.timezone) return false
        if (locale != other.locale) return false
        if (info != other.info) return false
        if (customTranslationsUrl != other.customTranslationsUrl) return false
        if (customSkinningUrl != other.customSkinningUrl) return false
        if (adminTypes != other.adminTypes) return false
        if (customOptions != other.customOptions) return false
        if (user != other.user) return false
        if (groups != other.groups) return false
        if (requestUrl != other.requestUrl) return false
        if (requestHandler != other.requestHandler) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sessionId.hashCode()
        result = 31 * result + serverVersion.hashCode()
        result = 31 * result + clientVersion.hashCode()
        result = 31 * result + clientUrl.hashCode()
        result = 31 * result + postMaxSize
        result = 31 * result + timezone.hashCode()
        result = 31 * result + locale.hashCode()
        result = 31 * result + info.hashCode()
        result = 31 * result + customTranslationsUrl.hashCode()
        result = 31 * result + customSkinningUrl.hashCode()
        result = 31 * result + adminTypes.hashCode()
        result = 31 * result + customOptions.hashCode()
        result = 31 * result + user.hashCode()
        result = 31 * result + groups.hashCode()
        result = 31 * result + requestUrl.hashCode()
        result = 31 * result + requestHandler.hashCode()
        return result
    }


}