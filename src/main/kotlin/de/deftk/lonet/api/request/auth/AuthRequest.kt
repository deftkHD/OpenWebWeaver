package de.deftk.lonet.api.request.auth

import de.deftk.lonet.api.LoNetClient
import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.request.ApiRequest
import de.deftk.lonet.api.request.handler.IRequestHandler
import de.deftk.lonet.api.response.ApiResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.put

class AuthRequest(private val requestUrl: String, private val requestHandler: IRequestHandler) : ApiRequest() {

    override fun fireRequest(context: IRequestContext): ApiResponse {
        error("Operation not supported!")
    }

    fun fireRequest(): ApiResponse {
        return super.fireRequest(AuthContext(requestUrl, requestHandler))
    }

    fun addChangePasswordRequest(newPassword: String): Int {
        val params = buildJsonObject {
            put("password", newPassword)
        }
        return addRequest("change_password", params)
    }

    fun addLoginRequest(request: LoginRequest): Int {
        return addRequest("login", LoNetClient.json.encodeToJsonElement(request).jsonObject)
    }

    fun addRegisterMasterRequest(request: RegisterMasterRequest): Int {
        return addRequest("register_master", LoNetClient.json.encodeToJsonElement(request).jsonObject)
    }

    fun addUnregisterMasterRequest(): Int {
        return addRequest("unregister_master", null)
    }

    fun addGetNonceRequest(): Int {
        return addRequest("get_nonce", null)
    }

    @Serializable
    data class LoginRequest(
        val login: String,
        val algorithm: Algorithm? = null,
        val application: String? = null,
        val crypt: String? = null,
        @SerialName("get_miniature") val getMiniature: Boolean? = null,
        val hash: String? = null,
        @SerialName("is_hidden") val isHidden: Boolean? = null,
        @SerialName("is_online") val isOnline: Boolean? = null,
        @SerialName("is_volatile") val isVolatile: Boolean? = null,
        @SerialName("nonce_id") val nonceId: String? = null,
        val password: String? = null,
        val salt: String? = null
    )

    @Serializable
    data class RegisterMasterRequest(
        @SerialName("remote_application") val remoteApplication: String,
        @SerialName("remote_title") val remoteTitle: String,
        @SerialName("is_bidirectional") val isBidirectional: Boolean? = null,
        @SerialName("is_replication") val isReplication: Boolean? = null,
        @SerialName("remote_host") val remoteHost: String? = null,
        @SerialName("remote_ident") val remoteIdentity: String? = null
    )

    @Serializable
    enum class Algorithm {
        @SerialName("md5")
        MD5,

        @SerialName("sha1")
        SHA1,

        @SerialName("sha256")
        SHA256,

        @SerialName("sha512")
        SHA512
    }

}