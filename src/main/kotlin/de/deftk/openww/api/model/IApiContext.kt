package de.deftk.openww.api.model

import de.deftk.openww.api.request.handler.IRequestHandler

interface IApiContext {

    val sessionId: String
    val user: IUser

    fun findOperatingScope(login: String): IOperatingScope?
    fun mapOperatingScope(scope: RemoteScope): IScope

    val requestUrl: String

    var requestHandler: IRequestHandler

}