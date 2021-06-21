package de.deftk.openww.api.model

import de.deftk.openww.api.request.handler.IRequestHandler

interface IRequestContext {

    val login: String
    var sessionId: String
    val requestUrl: String
    val requestHandler: IRequestHandler

}