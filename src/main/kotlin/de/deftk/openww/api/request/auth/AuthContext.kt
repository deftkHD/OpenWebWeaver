package de.deftk.openww.api.request.auth

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.request.handler.IRequestHandler

class AuthContext(override val requestUrl: String, override val requestHandler: IRequestHandler): IRequestContext {

    override val login: String
        get() = error("Operation not supported!")
    override var sessionId: String = ""
        get() = error("Operation not supported!")
    override val postMaxSize: Int = 5000

}