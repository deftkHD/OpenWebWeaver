package de.deftk.lonet.api.model

import de.deftk.lonet.api.request.handler.IRequestHandler

interface IApiContext {

    fun getSessionId(): String

    @Deprecated("not sure if this is needed and usefull")
    fun setSessionId(sessionId: String)

    fun getUser(): IUser

    fun findScope(scope: RemoteScope): IScope

    fun getRequestURL(): String

    fun getRequestHandler(): IRequestHandler
    fun setRequestHandler(requestHandler: IRequestHandler)

}