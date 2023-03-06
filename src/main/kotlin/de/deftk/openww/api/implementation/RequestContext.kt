package de.deftk.openww.api.implementation

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.request.handler.IRequestHandler

class RequestContext(
    override val login: String,
    override var sessionId: String,
    override val postMaxSize: Int,
    override val requestUrl: String,
    override val requestHandler: IRequestHandler
) : IRequestContext {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RequestContext) return false

        if (login != other.login) return false
        if (sessionId != other.sessionId) return false
        if (postMaxSize != other.postMaxSize) return false
        if (requestUrl != other.requestUrl) return false
        if (requestHandler != other.requestHandler) return false

        return true
    }

    override fun hashCode(): Int {
        var result = login.hashCode()
        result = 31 * result + sessionId.hashCode()
        result = 31 * result + postMaxSize
        result = 31 * result + requestUrl.hashCode()
        result = 31 * result + requestHandler.hashCode()
        return result
    }
}