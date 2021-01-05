package de.deftk.lonet.api.request.auth

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.request.handler.IRequestHandler

class AuthContext(override val requestUrl: String, override val requestHandler: IRequestHandler): IRequestContext {

    override val login: String
        get() = error("Operation not supported!")
    override var sessionId: String = ""
        get() = error("Operation not supported!")

}