package de.deftk.openww.api.implementation

import de.deftk.openww.api.model.IApiContext
import de.deftk.openww.api.model.IScope
import de.deftk.openww.api.model.RemoteScope
import de.deftk.openww.api.request.handler.IRequestHandler

class ApiContext(
    override val sessionId: String,
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
}