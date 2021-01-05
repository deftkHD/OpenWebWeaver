package de.deftk.lonet.api.implementation

import de.deftk.lonet.api.LoNetClient
import de.deftk.lonet.api.implementation.feature.filestorage.session.SessionFile
import de.deftk.lonet.api.implementation.feature.mailbox.EmailFolder
import de.deftk.lonet.api.implementation.feature.mailbox.EmailSignature
import de.deftk.lonet.api.implementation.feature.systemnotification.SystemNotification
import de.deftk.lonet.api.model.*
import de.deftk.lonet.api.model.feature.Quota
import de.deftk.lonet.api.model.feature.ServiceType
import de.deftk.lonet.api.model.feature.mailbox.ReferenceMode
import de.deftk.lonet.api.request.OperatingScopeApiRequest
import de.deftk.lonet.api.request.UserApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import de.deftk.lonet.api.serialization.BooleanFromIntSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

@Serializable
data class User(
    @SerialName("user")
    private val userData: UserData,
    @SerialName("member")
    private val groups: List<Group> = emptyList()
) : IUser, OperatingScope() {

    override val login: String
        get() = userData.login
    override val name: String
        get() = userData.name
    override val type: Int
        get() = userData.type
    override val baseRights: List<Permission>
        get() = userData.baseRights
    override val effectiveRights: List<Permission>
        get() = userData.effectiveRights

    override fun getBaseUser(): RemoteScope = userData.baseUser

    override fun getFullName(): String = userData.fullName

    override fun getGTAC(): GTAC = userData.gtac

    override fun getSystemNotifications(context: IRequestContext): List<SystemNotification> {
        val request = UserApiRequest(context)
        val id = request.addGetSystemNotificationsRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["messages"]?.jsonArray?.map { Json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override fun addSessionFile(name: String, data: ByteArray, context: IRequestContext): SessionFile {
        val request = UserApiRequest(context)
        val id = request.addAddSessionFileRequest(name, data)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override fun getGroups(): List<Group> = groups

    override fun passwordMustChange(): Boolean = userData.passwordMustChange

    override fun getAutoLoginUrl(disableLogout: Boolean?, disableReceptionOfQuickMessages: Boolean?, ensalveSession: Boolean?, locale: Locale?, pingMaster: Boolean?, sessionTimeout: Int?, targetData: JsonElement?, targetIframes: Boolean?, targetUrlPath: String?, context: IRequestContext): String {
        val request = UserApiRequest(context)
        val id = request.addGetAutoLoginUrlRequest(disableLogout, disableReceptionOfQuickMessages, ensalveSession, locale, pingMaster, sessionTimeout, targetData, targetIframes, targetUrlPath)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["url"]!!.jsonPrimitive.content
    }

    override fun logout(context: IRequestContext) {
        val request = UserApiRequest(context)
        request.addRequest("logout", null)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun logoutDestroyToken(token: String, context: IRequestContext) {
        val request = UserApiRequest(context)
        request.addRequest("logout", null)
        val tmpContext = LoNetClient.loginToken(login, token, true, ApiContext::class.java)
        tmpContext.getUser().logout(context)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun checkSession(context: IRequestContext): Boolean {
        val request = UserApiRequest(context)
        val response = request.fireRequest()
        return runCatching { ResponseUtil.checkSuccess(response.toJson()) }.isSuccess
    }

    override fun registerService(type: ServiceType, token: String, application: String?, generateSecret: String?, isOnline: Boolean?, managedObjects: String?, unmanagedPriority: Int?, context: IRequestContext) {
        val request = UserApiRequest(context)
        request.addRegisterServiceRequest(type, token, application, generateSecret, isOnline, managedObjects, unmanagedPriority)[1]
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun unregisterService(type: ServiceType, token: String, context: IRequestContext) {
        val request = UserApiRequest(context)
        request.addUnregisterServiceRequest(type, token)[1]
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun getEmailStatus(context: IRequestContext): Pair<Quota, Int> {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetEmailStateRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Pair(
            Json.decodeFromJsonElement(subResponse["quota"]!!.jsonObject),
            subResponse["unread_messages"]!!.jsonPrimitive.int
        )
    }

    override fun getEmailQuota(context: IRequestContext): Quota {
        return getEmailStatus(context).first
    }

    override fun getUnreadEmailCount(context: IRequestContext): Int {
        return getEmailStatus(context).second
    }

    override fun getEmailFolders(context: IRequestContext): List<EmailFolder> {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetEmailFoldersRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["folders"]!!.jsonArray.map { Json.decodeFromJsonElement(it) }
    }

    override fun addEmailFolder(name: String, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        request.addAddEmailFolderRequest(name)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun sendEmail(
        to: String,
        subject: String,
        plainBody: String,
        addToSentFolder: Boolean?,
        cc: String?,
        bcc: String?,
        importSessionFiles: Array<JsonElement>?,
        referenceFolderId: String?,
        referenceMessageId: Int?,
        referenceMode: ReferenceMode?,
        text: String?,
        context: IRequestContext
    ) {
        val request = OperatingScopeApiRequest(context)
        request.addSendEmailRequest(to, subject, plainBody, text, bcc, cc)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun getEmailSignature(context: IRequestContext): EmailSignature {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetEmailSignatureRequest()[1]
        val response = request.fireRequest()
        return Json.decodeFromJsonElement(ResponseUtil.getSubResponseResult(response.toJson(), id)["signature"]!!)
    }

    @Serializable
    data class UserData(
        val login: String,
        @SerialName("name_hr")
        val name: String,
        val type: Int,
        @SerialName("fullname")
        val fullName: String,
        @SerialName("base_user")
        val baseUser: RemoteScope,
        @SerialName("base_rights")
        val baseRights: List<Permission> = emptyList(),
        @SerialName("effective_rights")
        val effectiveRights: List<Permission> = emptyList(),
        @SerialName("password_must_change")
        @Serializable(with = BooleanFromIntSerializer::class)
        val passwordMustChange: Boolean,
        val gtac: GTAC
    )

}