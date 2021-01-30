package de.deftk.lonet.api

import de.deftk.lonet.api.auth.Credentials
import de.deftk.lonet.api.auth.Substitute
import de.deftk.lonet.api.exception.ApiException
import de.deftk.lonet.api.factory.IApiContextFactory
import de.deftk.lonet.api.implementation.ApiContext
import de.deftk.lonet.api.implementation.DefaultApiContextFactory
import de.deftk.lonet.api.model.IApiContext
import de.deftk.lonet.api.request.Focusable
import de.deftk.lonet.api.request.auth.AuthRequest
import de.deftk.lonet.api.request.handler.DefaultRequestHandler
import de.deftk.lonet.api.response.ResponseUtil
import de.deftk.lonet.api.utils.PlatformUtil
import kotlinx.serialization.json.*
import java.util.*

object LoNetClient {

    val json = Json {
        classDiscriminator = "class_type"
        ignoreUnknownKeys = true //FIXME this option should be set to false, but doing so will cause conflicts with the option above
    }

    private val apiContextFactories = mutableMapOf<Class<out IApiContext>, IApiContextFactory>()
    private val defaultApiContextFactory = DefaultApiContextFactory()

    fun registerApiContextFactory(contextClass: Class<out IApiContext>, factory: IApiContextFactory) {
        if (!apiContextFactories.containsKey(contextClass))
            apiContextFactories[contextClass] = factory
        else error("Factory already registered for class ${contextClass.name}")
    }

    @Throws(ApiException::class)
    fun login(credentials: Credentials): ApiContext {
        return login(credentials, ApiContext::class.java)
    }

    @Throws(ApiException::class)
    fun <T : IApiContext> login(credentials: Credentials, contextClass: Class<T>): T {
        return when {
            credentials.password != null -> login(credentials.username, credentials.password, contextClass)
            credentials.token != null -> loginToken(credentials.username, credentials.token, contextClass = contextClass)
            else -> throw IllegalArgumentException("Password or token must be set")
        }
    }

    @Throws(ApiException::class)
    fun login(username: String, password: String): ApiContext {
        return login(username, password, ApiContext::class.java)
    }

    @Throws(ApiException::class)
    fun <T : IApiContext> login(username: String, password: String, contextClass: Class<T>): T {
        val requestUrl = getRequestUrl(username)
        val request = AuthRequest(requestUrl, DefaultRequestHandler())
        request.addLoginRequest(AuthRequest.LoginRequest(username, password = password, getMiniature = true))
        request.addGetInformationRequest()
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
        val factory = apiContextFactories[contextClass] ?: defaultApiContextFactory
        return factory.createApiContext(response, requestUrl) as T
    }

    @Throws(ApiException::class)
    fun loginToken(username: String, token: String, removeTrust: Boolean = false): ApiContext {
        return loginToken(username, token, removeTrust, ApiContext::class.java)
    }

    @Throws(ApiException::class)
    fun <T : IApiContext> loginToken(username: String, token: String, removeTrust: Boolean = false, contextClass: Class<T>): T {
        val requestUrl = getRequestUrl(username)
        val nonceRequest = AuthRequest(requestUrl, DefaultRequestHandler())
        nonceRequest.addGetNonceRequest()
        val nonceResponse = nonceRequest.fireRequest()
        val nonceJson = nonceResponse.toJson().jsonArray[0].jsonObject["result"]!!.jsonObject["nonce"]!!.jsonObject
        val nonceId = nonceJson["id"]!!.jsonPrimitive.content
        val nonceKey = nonceJson["key"]!!.jsonPrimitive.content

        // generate salt
        val chars = "0123456789qwertyuiopasdfghjklzxcvbnmABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()
        val random = Random()
        val salt = StringBuilder()
        val size = random.nextInt(5) + 8
        for (i in 0 until size) {
            salt.append(chars[random.nextInt(62)])
        }

        val authRequest = AuthRequest(requestUrl, DefaultRequestHandler())
        authRequest.addLoginRequest(
            AuthRequest.LoginRequest(
                username,
                application = "wwa",
                hash = PlatformUtil.sha256Hash("$nonceKey$salt$token"),
                salt=salt.toString(),
                nonceId = nonceId,
                algorithm = AuthRequest.Algorithm.SHA256,
                getMiniature = true
            ))
        if (removeTrust) {
            authRequest.addSetFocusRequest(Focusable.TRUSTS, null as? String?)
            authRequest.addUnregisterMasterRequest()
        }
        authRequest.addGetInformationRequest()
        val response = authRequest.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
        val factory = apiContextFactories[contextClass] ?: defaultApiContextFactory
        return factory.createApiContext(response, requestUrl) as T
    }

    @Throws(ApiException::class)
    fun <T : IApiContext> loginTokenCreateSubstitute(username: String, token: String, service: AuthRequest.SubstituteService, substituteTimeout: Int, substituteName: String? = null, contextClass: Class<T>): Pair<T, Substitute> {
        val requestUrl = getRequestUrl(username)
        val nonceRequest = AuthRequest(requestUrl, DefaultRequestHandler())
        nonceRequest.addGetNonceRequest()
        val nonceResponse = nonceRequest.fireRequest()
        val nonceJson = nonceResponse.toJson().jsonArray[0].jsonObject["result"]!!.jsonObject["nonce"]!!.jsonObject
        val nonceId = nonceJson["id"]!!.jsonPrimitive.content
        val nonceKey = nonceJson["key"]!!.jsonPrimitive.content

        // generate salt
        val chars = "0123456789qwertyuiopasdfghjklzxcvbnmABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()
        val random = Random()
        val salt = StringBuilder()
        val size = random.nextInt(5) + 8
        for (i in 0 until size) {
            salt.append(chars[random.nextInt(62)])
        }

        val authRequest = AuthRequest(requestUrl, DefaultRequestHandler())
        authRequest.addLoginRequest(
            AuthRequest.LoginRequest(
                username,
                application = "wwa",
                hash = PlatformUtil.sha256Hash("$nonceKey$salt$token"),
                salt=salt.toString(),
                nonceId = nonceId,
                algorithm = AuthRequest.Algorithm.SHA256,
                getMiniature = true
            ))
        authRequest.addSetFocusRequest(Focusable.TRUSTS, null as? String?)
        authRequest.addRegisterSubstituteRequest(service, substituteTimeout, substituteName)
        authRequest.addSetFocusRequest(Focusable.TRUSTS, null as? String?)
        authRequest.addGetInformationRequest()
        val response = authRequest.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
        val factory = apiContextFactories[contextClass] ?: defaultApiContextFactory
        return Pair(factory.createApiContext(response, requestUrl) as T, json.decodeFromJsonElement(ResponseUtil.getSubResponseResultByMethod(response.toJson(), "register_substitute")["substitute"]!!))
    }

    @Throws(ApiException::class)
    fun loginCreateToken(username: String, password: String, title: String, identity: String): Pair<ApiContext, String> {
        return loginCreateToken(username, password, title, identity, ApiContext::class.java)
    }

    @Throws(ApiException::class)
    fun <T : IApiContext> loginCreateToken(username: String, password: String, title: String, identity: String, contextClass: Class<T>): Pair<T, String> {
        val requestUrl = getRequestUrl(username)
        val request = AuthRequest(requestUrl, DefaultRequestHandler())
        request.addLoginRequest(AuthRequest.LoginRequest(username, password = password, getMiniature = true))
        request.addSetFocusRequest(Focusable.TRUSTS, null as? String?)
        request.addRegisterMasterRequest(AuthRequest.RegisterMasterRequest("wwa", remoteTitle = title, remoteIdentity = identity))
        request.addGetInformationRequest()
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
        val token = ResponseUtil.getSubResponseResultByMethod(response.toJson(), "register_master")["trust"]?.jsonObject?.get("token")?.jsonPrimitive?.content ?: throw ApiException("Failed to parse token")
        val factory = apiContextFactories[contextClass] ?: defaultApiContextFactory
        return Pair(factory.createApiContext(response, requestUrl) as T, token)
    }

    @Throws(ApiException::class)
    private fun getRequestUrl(username: String): String {
        val requestData = serializePostRequestData(mapOf(Pair("login", username)))
        val response = PlatformUtil.postRequest(
            "https://fork.webweaver.de/service/get_responsible_host.php",
            15000,
            "application/x-www-form-urlencoded",
            requestData
        )
        val indexOf: Int = response.text.indexOf("<host>") + 6
        val indexOf2: Int = response.text.indexOf("</host>")
        if (indexOf >= 0 && indexOf2 >= 0) {
            val urlBuilder = StringBuilder()
            urlBuilder.append("https://")
            urlBuilder.append(response.text.substring(indexOf, indexOf2))
            urlBuilder.append("/jsonrpc.php")
            return urlBuilder.toString()
        }
        throw ApiException("Invalid response (expected xml with host url)")
    }

    private fun serializePostRequestData(params: Map<String, String>): ByteArray {
        val sb = StringBuilder()
        var addAndOperator = false
        params.forEach { (key, value) ->
            if (addAndOperator)
                sb.append("&")
            else
                addAndOperator = true
            sb.append(PlatformUtil.urlEncodeUTF8(key))
            sb.append("=")
            sb.append(PlatformUtil.urlEncodeUTF8(value))
        }
        return sb.toString().toByteArray()
    }

}