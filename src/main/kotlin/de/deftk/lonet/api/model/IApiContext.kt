package de.deftk.lonet.api.model

import de.deftk.lonet.api.request.handler.IRequestHandler

interface IApiContext {

    fun getSessionId(): String

    fun getUser(): IUser

    fun findOperatingScope(login: String): IOperatingScope?
    fun mapOperatingScope(scope: RemoteScope): IScope

    fun getRequestURL(): String

    fun getRequestHandler(): IRequestHandler
    fun setRequestHandler(requestHandler: IRequestHandler)

}