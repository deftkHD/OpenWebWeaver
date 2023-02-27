package de.deftk.openww.api.model

import de.deftk.openww.api.request.handler.IRequestHandler

interface IApiContext {

    val sessionId: String
    val serverVersion: String
    val clientVersion: String
    val clientUrl: String
    val postMaxSize: Int
    val timezone: String
    val locale: String
    val info: String
    val customTranslationsUrl: String
    val customSkinningUrl: String
    val adminTypes: List<Int>
    val customOptions: String
    val user: IUser

    fun findOperatingScope(login: String): IOperatingScope?
    fun mapOperatingScope(scope: RemoteScope): IScope
    fun userContext(): IRequestContext
    fun requestContext(scope: IOperatingScope): IRequestContext

    val requestUrl: String

    var requestHandler: IRequestHandler

}