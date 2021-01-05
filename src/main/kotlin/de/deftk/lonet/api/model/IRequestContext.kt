package de.deftk.lonet.api.model

import de.deftk.lonet.api.request.handler.IRequestHandler

interface IRequestContext {

    val login: String
    var sessionId: String
    val requestUrl: String
    val requestHandler: IRequestHandler

}