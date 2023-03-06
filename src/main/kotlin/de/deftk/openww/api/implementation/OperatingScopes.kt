package de.deftk.openww.api.implementation

import de.deftk.openww.api.WebWeaverClient
import de.deftk.openww.api.exception.ApiException
import de.deftk.openww.api.implementation.feature.board.BoardNotification
import de.deftk.openww.api.implementation.feature.calendar.Appointment
import de.deftk.openww.api.implementation.feature.contacts.Contact
import de.deftk.openww.api.implementation.feature.courselets.Courselet
import de.deftk.openww.api.implementation.feature.courselets.CourseletMapping
import de.deftk.openww.api.implementation.feature.filestorage.RemoteFile
import de.deftk.openww.api.implementation.feature.filestorage.session.SessionFile
import de.deftk.openww.api.implementation.feature.forum.ForumPost
import de.deftk.openww.api.implementation.feature.mailbox.EmailFolder
import de.deftk.openww.api.implementation.feature.mailbox.EmailSignature
import de.deftk.openww.api.implementation.feature.messenger.QuickMessage
import de.deftk.openww.api.implementation.feature.notes.Note
import de.deftk.openww.api.implementation.feature.profile.UserProfile
import de.deftk.openww.api.implementation.feature.systemnotification.NotificationSetting
import de.deftk.openww.api.implementation.feature.systemnotification.SystemNotification
import de.deftk.openww.api.implementation.feature.tasks.Task
import de.deftk.openww.api.implementation.feature.wiki.WikiPage
import de.deftk.openww.api.model.*
import de.deftk.openww.api.model.Locale
import de.deftk.openww.api.model.feature.FileDownloadUrl
import de.deftk.openww.api.model.feature.Quota
import de.deftk.openww.api.model.feature.ServiceType
import de.deftk.openww.api.model.feature.board.BoardNotificationColor
import de.deftk.openww.api.model.feature.board.IBoardNotification
import de.deftk.openww.api.model.feature.calendar.IAppointment
import de.deftk.openww.api.model.feature.contacts.Gender
import de.deftk.openww.api.model.feature.contacts.IContact
import de.deftk.openww.api.model.feature.filestorage.FileStorageSettings
import de.deftk.openww.api.model.feature.filestorage.IRemoteFile
import de.deftk.openww.api.model.feature.filestorage.filter.FileFilter
import de.deftk.openww.api.model.feature.filestorage.session.ISessionFile
import de.deftk.openww.api.model.feature.forum.ForumPostIcon
import de.deftk.openww.api.model.feature.forum.ForumSettings
import de.deftk.openww.api.model.feature.forum.IForumPost
import de.deftk.openww.api.model.feature.mailbox.IEmail
import de.deftk.openww.api.model.feature.mailbox.IEmailFolder
import de.deftk.openww.api.model.feature.mailbox.ReferenceMode
import de.deftk.openww.api.model.feature.messenger.IQuickMessage
import de.deftk.openww.api.model.feature.notes.INote
import de.deftk.openww.api.model.feature.notes.NoteColor
import de.deftk.openww.api.model.feature.tasks.ITask
import de.deftk.openww.api.request.GroupApiRequest
import de.deftk.openww.api.request.OperatingScopeApiRequest
import de.deftk.openww.api.request.UserApiRequest
import de.deftk.openww.api.response.ResponseUtil
import de.deftk.openww.api.serialization.BooleanFromIntSerializer
import de.deftk.openww.api.utils.PlatformUtil
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*
import java.text.DateFormat
import java.util.*

@Serializable
sealed class OperatingScope : IOperatingScope {

    abstract override val login: String
    abstract override val name: String
    abstract override val type: Int
    abstract override val baseRights: List<Permission>
    abstract override val effectiveRights: List<Permission>

    override fun getRequestContext(apiContext: IApiContext): IRequestContext =
        RequestContext(login, apiContext.sessionId, apiContext.postMaxSize, apiContext.requestUrl, apiContext.requestHandler)

    override suspend fun getAppointments(context: IRequestContext): List<Appointment> {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetAppointmentsRequest()
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]!!.jsonArray.map { WebWeaverClient.json.decodeFromJsonElement(it) }
    }

    override suspend fun addAppointment(appointment: IAppointment, context: IRequestContext): Appointment {
        return addAppointment(
            appointment.title,
            appointment.description,
            appointment.endDate,
            appointment.endDateIso,
            appointment.location,
            appointment.rrule,
            appointment.startDate,
            appointment.startDateIso,
            appointment.uid,
            context
        )
    }

    override suspend fun addAppointment(title: String, description: String?, endDate: Date?, endDateIso: String?, location: String?, rrule: String?, startDate: Date?, startDateIso: String?, uid: String?, context: IRequestContext): Appointment {
        val request = OperatingScopeApiRequest(context)
        val id = request.addAddAppointmentRequest(title, description, endDate?.time, endDateIso, location, rrule, startDate?.time, startDateIso, login)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["entry"]!!)
    }

    override suspend fun getContacts(context: IRequestContext): List<Contact> {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetContactsRequest()
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]?.jsonArray?.map { WebWeaverClient.json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override suspend fun addContact(contact: IContact, context: IRequestContext): Contact {
        return addContact(contact.birthday, contact.businessCity, contact.businessCoords, contact.businessCountry, contact.businessFax, contact.businessPhone, contact.businessPostalCode, contact.businessState, contact.businessStreet, contact.businessStreet2, contact.businessStreet3, contact.categories, contact.company, contact.companyType, contact.email2Address, contact.email3Address, contact.emailAddress, contact.firstName, contact.fullName, contact.gender, contact.hobby, contact.homeCity, contact.homeCoords, contact.homeCountry, contact.homeFax, contact.homePhone, contact.homePostalCode, contact.homeState, contact.homeStreet, contact.homeStreet2, contact.homeStreet3, contact.jobTitle, contact.jobTitle2, contact.lastName, contact.middleName, contact.mobilePhone, contact.nickName, contact.notes, contact.subjects, contact.suffix, contact.title, contact.uid, contact.webPage, context)
    }

    override suspend fun addContact(
        birthday: String?,
        businessCity: String?,
        businessCoords: String?,
        businessCountry: String?,
        businessFax: String?,
        businessPhone: String?,
        businessPostalCode: String?,
        businessState: String?,
        businessStreet: String?,
        businessStreet2: String?,
        businessStreet3: String?,
        categories: String?,
        company: String?,
        companyType: String?,
        email2Address: String?,
        email3Address: String?,
        emailAddress: String?,
        firstName: String?,
        fullName: String?,
        gender: Gender?,
        hobby: String?,
        homeCity: String?,
        homeCoords: String?,
        homeCountry: String?,
        homeFax: String?,
        homePhone: String?,
        homePostalCode: String?,
        homeState: String?,
        homeStreet: String?,
        homeStreet2: String?,
        homeStreet3: String?,
        jobTitle: String?,
        jobTitle2: String?,
        lastName: String?,
        middleName: String?,
        mobilePhone: String?,
        nickName: String?,
        notes: String?,
        subjects: String?,
        suffix: String?,
        title: String?,
        uid: String?,
        webPage: String?,
        context: IRequestContext
    ): Contact {
        val request = OperatingScopeApiRequest(context)
        val id = request.addAddContactRequest(birthday, businessCity, businessCoords, businessCountry, businessFax, businessPhone, businessPostalCode, businessState, businessStreet, businessStreet2, businessStreet3, categories, company, companyType, email2Address, email3Address, emailAddress, firstName, fullName, gender, hobby, homeCity, homeCoords, homeCountry, homeFax, homePhone, homePostalCode, homeState, homeStreet, homeStreet2, homeStreet3, jobTitle, jobTitle2, lastName, middleName, mobilePhone, nickName, notes, subjects, suffix, title, uid, webPage, login)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["entry"]!!)
    }

    override suspend fun getRootFile(context: IRequestContext): IRemoteFile {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetFileStorageFilesRequest(
            folderId = "/",
            getFolder = true,
            getFiles = false,
            getFolders = false,
            recursive = false
        )
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]!!.jsonArray.map { WebWeaverClient.json.decodeFromJsonElement<RemoteFile>(it) }[0]
    }

    override suspend fun getFiles(limit: Int?, offset: Int?, filter: FileFilter?, context: IRequestContext): List<RemoteFile> {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetFileStorageFilesRequest(
            "/",
            recursive = false,
            getFiles = true,
            getFolders = true,
            searchString = filter?.searchTerm,
            searchMode = filter?.searchMode
        )
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]?.jsonArray?.map { WebWeaverClient.json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override suspend fun addFile(name: String, data: ByteArray, description: String?, context: IRequestContext): RemoteFile {
        val request = OperatingScopeApiRequest(context)
        val id = request.addAddFileRequest(PlatformUtil.base64EncodeToString(data), "/", name, description)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override suspend fun addSparseFile(name: String, size: Int, description: String?, context: IRequestContext): RemoteFile {
        val request = OperatingScopeApiRequest(context)
        val id = request.addAddSparseFileRequest("/", name, size, description, login)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override suspend fun importSessionFile(sessionFile: ISessionFile, createCopy: Boolean?, description: String?, context: IRequestContext): RemoteFile {
        val request = OperatingScopeApiRequest(context)
        val id = request.addImportSessionFileRequest(sessionFile.id, createCopy, description, folderId = "/")
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override suspend fun addFolder(name: String, description: String?, context: IRequestContext): RemoteFile {
        val request = OperatingScopeApiRequest(context)
        val id = request.addAddFolderRequest("/", name, description)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["folder"]!!)
    }

    override suspend fun setReadable(readable: Boolean, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        request.addSetFolderRequest("/", null, name, readable, null, null, null, null)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override suspend fun setWritable(writable: Boolean, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        request.addSetFolderRequest("/", null, name, null, null, null, null, writable)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override suspend fun getFileStorageState(context: IRequestContext): Pair<FileStorageSettings, Quota> {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetFileStorageStateRequest()
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Pair(
            WebWeaverClient.json.decodeFromJsonElement(subResponse["settings"]!!.jsonObject),
            WebWeaverClient.json.decodeFromJsonElement(subResponse["quota"]!!.jsonObject)
        )
    }

    override suspend fun getTrash(limit: Int?, context: IRequestContext): List<RemoteFile> {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetTrashRequest(limit)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["files"]!!.jsonArray.map { WebWeaverClient.json.decodeFromJsonElement(it) }
    }

    override suspend fun getTasks(context: IRequestContext): List<Task> {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetTasksRequest()
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]?.jsonArray?.map { WebWeaverClient.json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override suspend fun addTask(task: ITask, context: IRequestContext): ITask {
        return addTask(task.title, task.completed, task.description, task.dueDate?.time, task.startDate?.time, context)
    }

    override suspend fun addTask(title: String, completed: Boolean?, description: String?, dueDate: Long?, startDate: Long?, context: IRequestContext): Task {
        val request = OperatingScopeApiRequest(context)
        val id = request.addAddTaskRequest(title, completed, description, dueDate, startDate)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["entry"]!!)
    }

    override suspend fun readQuickMessages(exportSessionFile: Boolean?, context: IRequestContext): List<QuickMessage> {
        val request = GroupApiRequest(context)
        val id = request.addReadQuickMessagesRequest(exportSessionFile)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["messages"]!!.jsonArray.map { WebWeaverClient.json.decodeFromJsonElement(it) }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OperatingScope) return false

        if (login != other.login) return false
        if (name != other.name) return false
        if (type != other.type) return false
        if (baseRights != other.baseRights) return false
        if (effectiveRights != other.effectiveRights) return false

        return true
    }

    override fun hashCode(): Int {
        var result = login.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + type
        result = 31 * result + baseRights.hashCode()
        result = 31 * result + effectiveRights.hashCode()
        return result
    }

}

@Serializable
data class User(
    @SerialName("user")
    private val userData: UserData,
    @SerialName("member")
    private val groups: List<Group> = emptyList()
) : OperatingScope(), IUser {

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
    override val baseUser: RemoteScope
        get() = userData.baseUser
    override val fullName: String
        get() = userData.fullName
    override val gtac: GTAC
        get() = userData.gtac

    override suspend fun getProfile(exportImage: Boolean?, context: IRequestContext): UserProfile {
        val request = UserApiRequest(context)
        val id = request.addGetProfileRequest(exportImage)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["profile"]!!)
    }

    override suspend fun exportProfileImage(context: IRequestContext): SessionFile {
        val request = UserApiRequest(context)
        val id = request.addExportProfileImageRequest()
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["image"]!!)
    }

    override suspend fun deleteProfileImage(context: IRequestContext) {
        val request = UserApiRequest(context)
        request.addDeleteProfileImageRequest()
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override suspend fun importProfileImage(sessionFile: ISessionFile, context: IRequestContext) {
        val request = UserApiRequest(context)
        request.addImportProfileImageRequest(sessionFile.id)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override suspend fun getSystemNotifications(context: IRequestContext): List<SystemNotification> {
        val request = UserApiRequest(context)
        val id = request.addGetSystemNotificationsRequest()
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["messages"]?.jsonArray?.map { WebWeaverClient.json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override suspend fun getSystemNotificationSettings(context: IRequestContext): List<NotificationSetting> {
        val request = UserApiRequest(context)
        val id = request.addGetSystemNotificationSettingsRequest()
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["messages"]?.jsonArray?.map { WebWeaverClient.json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override suspend fun addSessionFile(name: String, data: ByteArray, context: IRequestContext): SessionFile {
        val request = UserApiRequest(context)
        val base64 = PlatformUtil.base64EncodeToString(data)
        val id = request.addAddSessionFileRequest(name, base64)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override fun getGroups(): List<Group> = groups

    override fun passwordMustChange(): Boolean = userData.passwordMustChange

    override suspend fun getAutoLoginUrl(disableLogout: Boolean?, disableReceptionOfQuickMessages: Boolean?, enslaveSession: Boolean?, locale: Locale?, pingMaster: Boolean?, sessionTimeout: Int?, targetData: JsonElement?, targetIframes: Boolean?, targetUrlPath: String?, context: IRequestContext): String {
        val request = UserApiRequest(context)
        val id = request.addGetAutoLoginUrlRequest(disableLogout, disableReceptionOfQuickMessages, enslaveSession, locale, pingMaster, sessionTimeout, targetData, targetIframes, targetUrlPath)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["url"]!!.jsonPrimitive.content
    }

    override suspend fun logout(context: IRequestContext) {
        val request = UserApiRequest(context)
        request.addRequest("logout", null)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override suspend fun logoutDestroyToken(token: String, context: IRequestContext) {
        logout(context)
        WebWeaverClient.loginToken(login, token, true, ApiContext::class.java)
    }

    override suspend fun checkSession(context: IRequestContext): Boolean {
        val request = UserApiRequest(context)
        val response = request.fireRequest()
        return runCatching { ResponseUtil.checkSuccess(response.toJson()) }.isSuccess
    }

    override suspend fun registerService(type: ServiceType, token: String, application: String?, generateSecret: String?, isOnline: Boolean?, managedObjects: String?, unmanagedPriority: Int?, context: IRequestContext) {
        val request = UserApiRequest(context)
        request.addRegisterServiceRequest(type, token, application, generateSecret, isOnline, managedObjects, unmanagedPriority)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override suspend fun unregisterService(type: ServiceType, token: String, context: IRequestContext) {
        val request = UserApiRequest(context)
        request.addUnregisterServiceRequest(type, token)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override suspend fun getEmailStatus(context: IRequestContext): Pair<Quota, Int> {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetEmailStateRequest()
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Pair(
            WebWeaverClient.json.decodeFromJsonElement(subResponse["quota"]!!.jsonObject),
            subResponse["unread_messages"]!!.jsonPrimitive.int
        )
    }

    override suspend fun getEmailQuota(context: IRequestContext): Quota {
        return getEmailStatus(context).first
    }

    override suspend fun getUnreadEmailCount(context: IRequestContext): Int {
        return getEmailStatus(context).second
    }

    override suspend fun getEmailFolders(context: IRequestContext): List<EmailFolder> {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetEmailFoldersRequest()
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["folders"]!!.jsonArray.map { WebWeaverClient.json.decodeFromJsonElement(it) }
    }

    override suspend fun addEmailFolder(name: String, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        request.addAddEmailFolderRequest(name)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override suspend fun sendEmail(
        to: String,
        subject: String,
        plainBody: String,
        addToSentFolder: Boolean?,
        cc: String?,
        bcc: String?,
        importSessionFiles: List<ISessionFile>?,
        referenceFolderId: String?,
        referenceMessageId: Int?,
        referenceMode: ReferenceMode?,
        text: String?,
        context: IRequestContext
    ) {
        val request = OperatingScopeApiRequest(context)
        request.addSendEmailRequest(to, subject, plainBody, text, bcc, cc, importSessionFiles, referenceFolderId, referenceMessageId, referenceMode)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override suspend fun answerEmail(
        toAddress: String,
        message: String,
        cc: String?,
        bcc: String?,
        importSessionFiles: List<ISessionFile>?,
        toFolder: IEmailFolder,
        toEmail: IEmail,
        context: IRequestContext
    ) {
        val dst = toEmail.from?.firstOrNull { it.address.equals(toAddress, true) } ?: toEmail.from?.firstOrNull()
        if (toEmail.plainBody == null) {
            toEmail.read(toFolder, true, context)
        }
        var oldMsg = ">" + (toEmail.plainBody?.replace("\n", "\n>") ?: toEmail.text?.replace("\n", "\n>") ?: "")
        if (oldMsg.endsWith("\n>")) oldMsg = oldMsg.substring(0, oldMsg.length - 2)
        val sb = StringBuilder()
        if (dst != null) {
            if (dst.name.isNotBlank()) {
                sb.append(dst.name).append(" <").append(dst.address).append(">")
            } else {
                sb.append(dst.address)
            }
        } else {
            sb.append(toAddress)
        }
        val msg = "$message\n\n${DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM).format(toEmail.date)} $sb:\n\n${oldMsg}"
        sendEmail(
            toAddress,
            "Re: ${toEmail.subject}",
            msg,
            true,
            null,
            null,
            null,
            toFolder.id,
            toEmail.id,
            ReferenceMode.ANSWERED,
            null,
            context
        )
    }

    override suspend fun forwardEmail(
        toAddress: String,
        message: String,
        cc: String?,
        bcc: String?,
        importSessionFiles: List<ISessionFile>?,
        fwdFolder: IEmailFolder,
        fwdEmail: IEmail,
        context: IRequestContext
    ) {
        val fromAddr = fwdEmail.from?.firstOrNull { it.address.equals(toAddress, true) } ?: fwdEmail.from?.firstOrNull()
        if (fwdEmail.plainBody == null) {
            fwdEmail.read(fwdFolder, true, context)
        }
        var oldMsg = ">" + (fwdEmail.plainBody?.replace("\n", "\n>") ?: fwdEmail.text?.replace("\n", "\n>") ?: "")
        if (oldMsg.endsWith("\n>")) oldMsg = oldMsg.substring(0, oldMsg.length - 2)
        val sbFrom = StringBuilder()
        if (fromAddr != null) {
            if (fromAddr.name.isNotBlank()) {
                sbFrom.append(fromAddr.name).append(" <").append(fromAddr.address).append(">")
            } else {
                sbFrom.append(fromAddr.address)
            }
        }
        val msg = "$message\n\n${DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM).format(fwdEmail.date)} $sbFrom:\n\n${oldMsg}"
        sendEmail(
            toAddress,
            "Fwd: ${fwdEmail.subject}",
            msg,
            true,
            null,
            null,
            null,
            fwdFolder.id,
            fwdEmail.id,
            ReferenceMode.FORWARDED,
            null,
            context
        )
    }

    override suspend fun getEmailSignature(context: IRequestContext): EmailSignature {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetEmailSignatureRequest()
        val response = request.fireRequest()
        return WebWeaverClient.json.decodeFromJsonElement(ResponseUtil.getSubResponseResult(response.toJson(), id)["signature"]!!)
    }

    override suspend fun getAllTasks(context: IApiContext): List<Pair<ITask, IOperatingScope>> {
        check(context is ApiContext)
        val request = UserApiRequest(getRequestContext(context))
        val taskIds = request.addGetAllTasksRequest(this)
        val response = request.fireRequest().toJson()
        ResponseUtil.checkSuccess(response)
        val responses = response.jsonArray.map { it.jsonObject }
        val tasks = mutableListOf<Pair<ITask, IOperatingScope>>()

        var scopeName: String? = null
        responses.forEach { subResponse ->
            val result = subResponse["result"]?.jsonObject ?: error("Response has no result")
            val method = result["method"]?.jsonPrimitive?.content
            val id = subResponse["id"]?.jsonPrimitive?.int
            if (method == "set_focus") {
                scopeName = result["user"]!!.jsonObject["login"]!!.jsonPrimitive.content
            } else if (taskIds.contains(id)) {
                assert(scopeName != null) { "Task request can't be performed outside a scope" }
                val scope = context.findOperatingScope(scopeName!!) ?: error("Invalid scope: \"$scopeName\"")
                result["entries"]!!.jsonArray.forEach { taskResponse ->
                    tasks.add(Pair(WebWeaverClient.json.decodeFromJsonElement<Task>(taskResponse.jsonObject), scope))
                }
            }
        }
        return tasks
    }

    override suspend fun getAllBoardNotifications(context: IApiContext): List<Pair<IBoardNotification, IGroup>> {
        check(context is ApiContext)
        val request = UserApiRequest(getRequestContext(context))
        val notificationIds = request.addGetAllBoardNotificationsRequest(this)
        val response = request.fireRequest().toJson()
        ResponseUtil.checkSuccess(response)
        val responses = response.jsonArray.map { it.jsonObject }
        val notifications = mutableListOf<Pair<IBoardNotification, IGroup>>()

        var scopeName: String? = null
        responses.forEach { subResponse ->
            val result = subResponse["result"]?.jsonObject ?: error("Response has no result")
            val method = result["method"]?.jsonPrimitive?.content
            val id = subResponse["id"]?.jsonPrimitive?.int
            if (method == "set_focus") {
                scopeName = result["user"]!!.jsonObject["login"]!!.jsonPrimitive.content
            } else if (notificationIds.contains(id)) {
                assert(scopeName != null) { "BoardNotification request can't be performed outside a scope" }
                val scope = context.findOperatingScope(scopeName!!) ?: error("Invalid scope: \"$scopeName\"")
                check(scope is IGroup) { "User can't have any board notifications" }
                result["entries"]!!.jsonArray.forEach { notificationResponse ->
                    notifications.add(Pair(WebWeaverClient.json.decodeFromJsonElement<BoardNotification>(notificationResponse.jsonObject), scope))
                }
            }
        }
        return notifications
    }

    override suspend fun getAllPupilBoardNotifications(context: IApiContext): List<Pair<IBoardNotification, IGroup>> {
        check(context is ApiContext)
        val request = UserApiRequest(getRequestContext(context))
        val notificationIds = request.addGetAllPupilBoardNotificationsRequest(this)
        val response = request.fireRequest().toJson()
        ResponseUtil.checkSuccess(response)
        val responses = response.jsonArray.map { it.jsonObject }
        val notifications = mutableListOf<Pair<IBoardNotification, IGroup>>()

        var scopeName: String? = null
        responses.forEach { subResponse ->
            val result = subResponse["result"]?.jsonObject ?: error("Response has no result")
            val method = result["method"]?.jsonPrimitive?.content
            val id = subResponse["id"]?.jsonPrimitive?.int
            if (method == "set_focus") {
                scopeName = result["user"]!!.jsonObject["login"]!!.jsonPrimitive.content
            } else if (notificationIds.contains(id)) {
                assert(scopeName != null) { "BoardNotification request can't be performed outside a scope" }
                val scope = context.findOperatingScope(scopeName!!) ?: error("Invalid scope: \"$scopeName\"")
                check(scope is IGroup) { "User can't have any board notifications" }
                result["entries"]!!.jsonArray.forEach { notificationResponse ->
                    notifications.add(Pair(WebWeaverClient.json.decodeFromJsonElement<BoardNotification>(notificationResponse.jsonObject), scope))
                }
            }
        }
        return notifications
    }

    override suspend fun getAllTeacherBoardNotifications(context: IApiContext): List<Pair<IBoardNotification, IGroup>> {
        check(context is ApiContext)
        val request = UserApiRequest(getRequestContext(context))
        val notificationIds = request.addGetAllTeacherBoardNotificationsRequest(this)
        val response = request.fireRequest().toJson()
        ResponseUtil.checkSuccess(response)
        val responses = response.jsonArray.map { it.jsonObject }
        val notifications = mutableListOf<Pair<IBoardNotification, IGroup>>()

        var scopeName: String? = null
        responses.forEach { subResponse ->
            val result = subResponse["result"]?.jsonObject ?: error("Response has no result")
            val method = result["method"]?.jsonPrimitive?.content
            val id = subResponse["id"]?.jsonPrimitive?.int
            if (method == "set_focus") {
                scopeName = result["user"]!!.jsonObject["login"]!!.jsonPrimitive.content
            } else if (notificationIds.contains(id)) {
                assert(scopeName != null) { "BoardNotification request can't be performed outside a scope" }
                val scope = context.findOperatingScope(scopeName!!) ?: error("Invalid scope: \"$scopeName\"")
                check(scope is IGroup) { "User can't have any board notifications" }
                result["entries"]!!.jsonArray.forEach { notificationResponse ->
                    notifications.add(Pair(WebWeaverClient.json.decodeFromJsonElement<BoardNotification>(notificationResponse.jsonObject), scope))
                }
            }
        }
        return notifications
    }

    override suspend fun getUsers(getMiniatures: Boolean?, onlineOnly: Boolean?, context: IRequestContext): List<RemoteScope> {
        val request = UserApiRequest(context)
        val id = request.addGetMessengerUsersRequest(getMiniatures, onlineOnly)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["users"]?.jsonArray?.map { WebWeaverClient.json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override suspend fun sendQuickMessage(login: String, importSessionFile: ISessionFile?, text: String?, context: IRequestContext): QuickMessage {
        val request = UserApiRequest(context)
        val id = request.addSendQuickMessageRequest(login, importSessionFile, text)
        val response = request.fireRequest()
        return WebWeaverClient.json.decodeFromJsonElement(ResponseUtil.getSubResponseResult(response.toJson(), id)["message"]!!)
    }

    override suspend fun addChat(login: String, context: IRequestContext): RemoteScope {
        val request = UserApiRequest(context)
        val id = request.addAddChatRequest(login)
        val response = request.fireRequest()
        return WebWeaverClient.json.decodeFromJsonElement(ResponseUtil.getSubResponseResult(response.toJson(), id)["user"]!!)
    }

    override suspend fun removeChat(login: String, context: IRequestContext): RemoteScope {
        val request = UserApiRequest(context)
        val id = request.addRemoveChatRequest(login)
        val response = request.fireRequest()
        return WebWeaverClient.json.decodeFromJsonElement(ResponseUtil.getSubResponseResult(response.toJson(), id)["user"]!!)
    }

    override suspend fun getHistory(exportSessionFile: Boolean?, startId: Int?, context: IRequestContext): List<QuickMessage> {
        val request = UserApiRequest(context)
        val id = request.addGetHistoryRequest(exportSessionFile, startId)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["messages"]?.jsonArray?.map { WebWeaverClient.json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override suspend fun blockUser(login: String, context: IRequestContext): RemoteScope {
        val request = UserApiRequest(context)
        val id = request.addBlockMessengerUserRequest(login)
        val response = request.fireRequest()
        return WebWeaverClient.json.decodeFromJsonElement(ResponseUtil.getSubResponseResult(response.toJson(), id)["user"]!!)
    }

    override suspend fun unblockUser(login: String, context: IRequestContext): RemoteScope {
        val request = UserApiRequest(context)
        val id = request.addUnblockMessengerUserRequest(login)
        val response = request.fireRequest()
        return WebWeaverClient.json.decodeFromJsonElement(ResponseUtil.getSubResponseResult(response.toJson(), id)["user"]!!)
    }

    override suspend fun getBlockList(context: IRequestContext): List<RemoteScope> {
        val request = UserApiRequest(context)
        val id = request.addGetMessengerBlocklistRequest()
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["users"]?.jsonArray?.map { WebWeaverClient.json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override suspend fun getNotes(context: IRequestContext): List<Note> {
        val request = UserApiRequest(context)
        val id = request.addGetNotesRequest()
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]?.jsonArray?.map { WebWeaverClient.json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override suspend fun addNote(note: INote, context: IRequestContext): Note {
        return addNote(note.text, note.title, note.color, context)
    }

    override suspend fun addNote(text: String, title: String, color: NoteColor?, context: IRequestContext): Note {
        val request = UserApiRequest(context)
        val id = request.addAddNoteRequest(text, title, color)
        val response = request.fireRequest()
        return WebWeaverClient.json.decodeFromJsonElement(ResponseUtil.getSubResponseResult(response.toJson(), id)["entry"]!!)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false
        if (!super.equals(other)) return false

        if (userData != other.userData) return false
        if (groups != other.groups) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + userData.hashCode()
        result = 31 * result + groups.hashCode()
        return result
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

@Serializable
class Group(
    override val login: String,
    @SerialName("name_hr")
    override val name: String,
    override val type: Int,
    @SerialName("base_rights")
    override val baseRights: List<Permission> = emptyList(),
    @SerialName("effective_rights")
    override val effectiveRights: List<Permission> = emptyList(),
    @SerialName("reduced_rights")
    private val _reducedRights: List<Permission> = emptyList(),
    @SerialName("member_rights")
    private val _memberRights: List<Permission> = emptyList()
) : IGroup, OperatingScope() {

    override var reducedRights: List<Permission> = _reducedRights
        private set

    override var memberRights: List<Permission> = _memberRights
        private set

    override suspend fun getMembers(miniatures: Boolean?, onlineOnly: Boolean?, context: IRequestContext): List<RemoteScope> {
        val request = GroupApiRequest(context)
        val id = request.addGetMembersRequest(miniatures, onlineOnly)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["users"]?.jsonArray?.map { WebWeaverClient.json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override suspend fun sendGlobalQuickMessage(sessionFile: ISessionFile?, text: String?, context: IRequestContext): QuickMessage {
        val request = GroupApiRequest(context)
        val id = request.addSendQuickMessageRequest(sessionFile?.id, text)
        val response = request.fireRequest()
        return WebWeaverClient.json.decodeFromJsonElement(ResponseUtil.getSubResponseResult(response.toJson(), id)["message"]!!)
    }

    override suspend fun getBoardNotifications(context: IRequestContext): List<BoardNotification> {
        val request = GroupApiRequest(context)
        val id = request.addGetBoardNotificationsRequest()
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]?.jsonArray?.map { WebWeaverClient.json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override suspend fun addBoardNotification(notification: IBoardNotification, context: IRequestContext): BoardNotification {
        return addBoardNotification(notification.title, notification.text, notification.color, null, context)
    }

    override suspend fun addBoardNotification(title: String, text: String, color: BoardNotificationColor?, killDate: Date?, context: IRequestContext): BoardNotification {
        val request = GroupApiRequest(context)
        val id = request.addAddBoardNotificationRequest(title, text, color, killDate?.time)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["entry"]!!)
    }

    override suspend fun getTeacherBoardNotifications(context: IRequestContext): List<BoardNotification> {
        val request = GroupApiRequest(context)
        val id = request.addGetTeacherBoardNotificationsRequest()
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]?.jsonArray?.map { WebWeaverClient.json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override suspend fun addTeacherBoardNotification(notification: IBoardNotification, context: IRequestContext): BoardNotification {
        return addTeacherBoardNotification(notification.title, notification.text, notification.color, null, context)
    }

    override suspend fun addTeacherBoardNotification(title: String, text: String, color: BoardNotificationColor?, killDate: Date?, context: IRequestContext): BoardNotification {
        val request = GroupApiRequest(context)
        val id = request.addAddTeacherBoardNotificationRequest(title, text, color, killDate?.time)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["entry"]!!)
    }

    override suspend fun getPupilBoardNotifications(context: IRequestContext): List<BoardNotification> {
        val request = GroupApiRequest(context)
        val id = request.addGetPupilBoardNotificationsRequest()
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]?.jsonArray?.map { WebWeaverClient.json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override suspend fun addPupilBoardNotification(notification: IBoardNotification, context: IRequestContext): BoardNotification {
        return addPupilBoardNotification(notification.title, notification.text, notification.color, null, context)
    }

    override suspend fun addPupilBoardNotification(title: String, text: String, color: BoardNotificationColor?, killDate: Date?, context: IRequestContext): BoardNotification {
        val request = GroupApiRequest(context)
        val id = request.addAddPupilBoardNotificationRequest(title, text, color, killDate?.time)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["entry"]!!)
    }

    override suspend fun getForumState(context: IRequestContext): Pair<Quota, ForumSettings> {
        val request = GroupApiRequest(context)
        val id = request.addGetForumStateRequest()
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Pair(Json.decodeFromJsonElement(subResponse["quota"]!!.jsonObject), WebWeaverClient.json.decodeFromJsonElement(subResponse["settings"]!!.jsonObject))
    }

    override suspend fun getForumPosts(parentId: String?, context: IRequestContext): List<IForumPost> {
        val request = GroupApiRequest(context)
        val id = request.addGetForumPostsRequest(parentId = parentId)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]!!.jsonArray.map { WebWeaverClient.json.decodeFromJsonElement<ForumPost>(it) }
    }

    override suspend fun getForumPostsTree(parentId: String?, context: IRequestContext): List<ForumPost> {
        val request = GroupApiRequest(context)
        val id = request.addGetForumPostsRequest(parentId = parentId)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        val allPosts = subResponse["entries"]!!.jsonArray.map { WebWeaverClient.json.decodeFromJsonElement<ForumPost>(it) }
        // build tree structure
        val rootPosts = mutableListOf<ForumPost>()
        val tmpPosts = mutableMapOf<String, ForumPost>()
        allPosts.forEach { post -> tmpPosts[post.id] = post }
        allPosts.forEach { post ->
            if (post.parentId != "0") {
                val parent = tmpPosts[post.parentId] ?: throw ApiException("Comment has invalid parent!")
                parent.commentLoaded(post)
            } else {
                rootPosts.add(post)
            }
        }
        return rootPosts
    }

    override suspend fun addForumPost(title: String, text: String, icon: ForumPostIcon, parent: IForumPost?, importSessionFile: String?, importSessionFiles: Array<String>?, replyNotificationMe: Boolean?, context: IRequestContext): ForumPost {
        val request = GroupApiRequest(context)
        val id = request.addAddForumPostRequest(title, text, icon, parent?.id ?: "0", importSessionFile, importSessionFiles, replyNotificationMe)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["entry"]!!)
    }

    override suspend fun getWikiPage(name: String?, context: IRequestContext): WikiPage? {
        val request = GroupApiRequest(context)
        val id = request.addGetWikiPageRequest(name)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["page"]!!)
    }

    override suspend fun getCourseletState(context: IRequestContext): Pair<Quota, String> {
        val request = GroupApiRequest(context)
        val id = request.addGetCourseletsStateRequest()
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Pair(
            WebWeaverClient.json.decodeFromJsonElement(subResponse["quota"]!!.jsonObject),
            subResponse["runtime_version_hash"]!!.jsonPrimitive.content
        )
    }

    override suspend fun getCourseletConfiguration(context: IRequestContext): JsonElement {
        val request = GroupApiRequest(context)
        val id = request.addGetCourseletsConfigurationRequest()
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["configuration"]!!.jsonObject
    }

    override suspend fun getCourselets(context: IRequestContext): List<Courselet> {
        val request = GroupApiRequest(context)
        val id = request.addGetCourseletsRequest()
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["courselets"]!!.jsonArray.map { WebWeaverClient.json.decodeFromJsonElement(it) }
    }

    override suspend fun getCourseletMappings(context: IRequestContext): List<CourseletMapping> {
        val request = GroupApiRequest(context)
        val id = request.addGetCourseletMappingsRequest()
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["mappings"]!!.jsonArray.map { WebWeaverClient.json.decodeFromJsonElement(it) }
    }

    override suspend fun addCourseletMapping(name: String, context: IRequestContext): CourseletMapping {
        val request = GroupApiRequest(context)
        val id = request.addAddCourseletMappingRequest(name)
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["mapping"]!!)
    }

    override suspend fun exportCourseletRuntime(context: IRequestContext): FileDownloadUrl {
        val request = GroupApiRequest(context)
        val id = request.addExportCourseletRuntimeRequest()
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!.jsonObject)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Group) return false
        if (!super.equals(other)) return false

        if (login != other.login) return false
        if (name != other.name) return false
        if (type != other.type) return false
        if (baseRights != other.baseRights) return false
        if (effectiveRights != other.effectiveRights) return false
        if (_reducedRights != other._reducedRights) return false
        if (_memberRights != other._memberRights) return false
        if (reducedRights != other.reducedRights) return false
        if (memberRights != other.memberRights) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + login.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + type
        result = 31 * result + baseRights.hashCode()
        result = 31 * result + effectiveRights.hashCode()
        result = 31 * result + _reducedRights.hashCode()
        result = 31 * result + _memberRights.hashCode()
        result = 31 * result + reducedRights.hashCode()
        result = 31 * result + memberRights.hashCode()
        return result
    }

}