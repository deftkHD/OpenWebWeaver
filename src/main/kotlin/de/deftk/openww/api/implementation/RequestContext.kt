package de.deftk.openww.api.implementation

import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.request.handler.IRequestHandler

class RequestContext(
    override val login: String,
    override var sessionId: String,
    override val postMaxSize: Int,
    override val requestUrl: String,
    override val requestHandler: IRequestHandler
) : IRequestContext