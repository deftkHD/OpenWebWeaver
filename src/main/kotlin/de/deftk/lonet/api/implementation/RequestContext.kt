package de.deftk.lonet.api.implementation

import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.request.handler.IRequestHandler

class RequestContext(
    override val login: String,
    override var sessionId: String,
    override val requestUrl: String,
    override val requestHandler: IRequestHandler
) : IRequestContext