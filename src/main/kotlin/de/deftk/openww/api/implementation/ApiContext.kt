package de.deftk.openww.api.implementation

import de.deftk.openww.api.model.IApiContext
import de.deftk.openww.api.model.IScope
import de.deftk.openww.api.model.RemoteScope
import de.deftk.openww.api.request.handler.IRequestHandler

class ApiContext(
    private val sessionId: String,
    private val user: User,
    private val groups: List<Group>,
    private val requestUrl: String,
    private var requestHandler: IRequestHandler
) : IApiContext {

    override fun getSessionId(): String = sessionId

    override fun getUser(): User = user

    override fun findOperatingScope(login: String): OperatingScope? {
        return groups.firstOrNull { it.login == login } ?: if (login == user.login) user else null
    }

    override fun mapOperatingScope(scope: RemoteScope): IScope {
        return findOperatingScope(scope.login) ?: scope
    }

    override fun getRequestURL(): String = requestUrl

    override fun getRequestHandler(): IRequestHandler = requestHandler

    override fun setRequestHandler(requestHandler: IRequestHandler) {
        this.requestHandler = requestHandler
    }
}