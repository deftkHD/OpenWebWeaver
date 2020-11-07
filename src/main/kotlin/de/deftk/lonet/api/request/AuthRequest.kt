package de.deftk.lonet.api.request

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.Group
import de.deftk.lonet.api.model.User
import de.deftk.lonet.api.model.abstract.AbstractOperator
import de.deftk.lonet.api.model.abstract.IContext
import de.deftk.lonet.api.model.abstract.IManageable
import de.deftk.lonet.api.response.ApiResponse
import java.io.Serializable
import java.security.MessageDigest
import java.util.*

open class AuthRequest(val requestUrl: String) : ApiRequest(), Serializable {

    fun addChangePasswordRequest(str: String) {
        val obj = JsonObject()
        obj.addProperty("password", str)
        addRequest("change_password", obj)
    }

    fun addLoginPasswordRequest(login: String, password: String) {
        val obj = JsonObject()
        obj.addProperty("login", login)
        obj.addProperty("password", password)
        obj.addProperty("get_miniature", true)
        addRequest("login", obj)
    }

    fun addRegisterMasterRequest(title: String, ident: String) {
        val obj = JsonObject()
        obj.addProperty("remote_application", "wwa")
        obj.addProperty("remote_title", title)
        obj.addProperty("remote_ident", ident)
        addRequest("register_master", obj)
    }

    fun addUnregisterMasterRequest() {
        addRequest("unregister_master", null)
    }

    fun addGetUrlForAutologinRequest(targetUrlPath: String?) {
        val obj = JsonObject()
        obj.addProperty("enslave_session", true)
        obj.addProperty("disable_reception_of_quick_messages", true)
        obj.addProperty("disable_logout", true)
        obj.addProperty("ping_master", 1)
        obj.addProperty("locale", Locale.getDefault().language)
        if (targetUrlPath?.isNotEmpty() == true) {
            obj.addProperty("target_url_path", targetUrlPath)
        }
        addRequest("get_url_for_autologin", obj)
    }

    fun addLoginNonceRequest(login: String, token: String, nonceId: String, nonceKey: String) {
        val chars = "0123456789qwertyuiopasdfghjklzxcvbnmABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()
        val random = Random()
        val salt = StringBuilder()
        val size = random.nextInt(5) + 8
        for (i in 0 until size) {
            salt.append(chars[random.nextInt(62)])
        }
        val obj = JsonObject()
        obj.addProperty("application", "wwa")
        obj.addProperty("hash", sha256Hash("$nonceKey$salt$token"))
        obj.addProperty("salt", salt.toString())
        obj.addProperty("nonce_id", nonceId)
        obj.addProperty("algorithm", "sha256")
        obj.addProperty("login", login)
        obj.addProperty("get_miniature", true)
        addRequest("login", obj)
    }

    fun addGetNonceRequest() {
        addRequest("get_nonce", null)
    }

    fun addGetProfileRequest() {
        val obj = JsonObject()
        obj.addProperty("export_image", 1)
        addRequest("get_profile", obj)
    }

    override fun fireRequest(context: IContext): ApiResponse {
        error("Operation not supported!")
    }

    fun fireRequest(): ApiResponse {
        return super.fireRequest(AuthContext(requestUrl))
    }

    private fun sha256Hash(str: String): String {
        return try {
            val digest = MessageDigest.getInstance("SHA-256")
            digest.reset()
            digest.update(str.toByteArray(Charsets.UTF_8))
            return byteToHex(digest.digest() ?: ByteArray(0))
        } catch (e: Exception) {
            e.printStackTrace()
            str
        }
    }

    private fun byteToHex(bytes: ByteArray): String {
        val formatter = Formatter()
        bytes.forEach { byte ->
            formatter.format("%02x", byte)
        }
        val formatted = formatter.toString()
        formatter.close()
        return formatted
    }

    class AuthContext(private val apiUrl: String) : IContext {

        override fun getSessionId(): String {
            error("Operation not supported!")
        }

        override fun setSessionId(sessionId: String) {
            error("Operation not supported!")
        }

        override fun getUser(): User {
            error("Operation not supported!")
        }

        override fun getGroups(): List<Group> {
            error("Operation not supported!")
        }

        override fun getOperator(login: String): AbstractOperator {
            error("Operation not supported!")
        }

        override fun getOrCreateManageable(jsonObject: JsonObject): IManageable {
            error("Operation not supported!")
        }

        override fun getRequestUrl(): String {
            return apiUrl
        }
    }

}