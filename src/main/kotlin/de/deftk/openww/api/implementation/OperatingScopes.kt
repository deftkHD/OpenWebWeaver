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
import java.util.*

@Serializable
sealed class OperatingScope : IOperatingScope {

    abstract override val login: String
    abstract override val name: String
    abstract override val type: Int
    abstract override val baseRights: List<Permission>
    abstract override val effectiveRights: List<Permission>

    override fun getRequestContext(apiContext: IApiContext): IRequestContext =
        RequestContext(login, apiContext.sessionId, apiContext.requestUrl, apiContext.requestHandler)

    override suspend fun getAppointments(context: IRequestContext): List<Appointment> {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetAppointmentsRequest()[1]
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
        val id = request.addAddAppointmentRequest(title, description, endDate?.time, endDateIso, location, rrule, startDate?.time, startDateIso, login)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["entry"]!!)
    }

    override suspend fun getContacts(context: IRequestContext): List<Contact> {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetContactsRequest()[1]
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
        val id = request.addAddContactRequest(birthday, businessCity, businessCoords, businessCountry, businessFax, businessPhone, businessPostalCode, businessState, businessStreet, businessStreet2, businessStreet3, categories, company, companyType, email2Address, email3Address, emailAddress, firstName, fullName, gender, hobby, homeCity, homeCoords, homeCountry, homeFax, homePhone, homePostalCode, homeState, homeStreet, homeStreet2, homeStreet3, jobTitle, jobTitle2, lastName, middleName, mobilePhone, nickName, notes, subjects, suffix, title, uid, webPage, login)[1]
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
        )[1]
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
        )[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]?.jsonArray?.map { WebWeaverClient.json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override suspend fun addFile(name: String, data: ByteArray, description: String?, context: IRequestContext): RemoteFile {
        val request = OperatingScopeApiRequest(context)
        val id = request.addAddFileRequest(PlatformUtil.base64EncodeToString(data), "/", name, description)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override suspend fun addSparseFile(name: String, size: Int, description: String?, context: IRequestContext): RemoteFile {
        val request = OperatingScopeApiRequest(context)
        val id = request.addAddSparseFileRequest("/", name, size, description, login)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override suspend fun importSessionFile(sessionFile: ISessionFile, createCopy: Boolean?, description: String?, context: IRequestContext): RemoteFile {
        val request = OperatingScopeApiRequest(context)
        val id = request.addImportSessionFileRequest(sessionFile.id, createCopy, description, folderId = "/")[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override suspend fun addFolder(name: String, description: String?, context: IRequestContext): RemoteFile {
        val request = OperatingScopeApiRequest(context)
        val id = request.addAddFolderRequest("/", name, description)[1]
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
        val id = request.addGetFileStorageStateRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Pair(
            WebWeaverClient.json.decodeFromJsonElement(subResponse["settings"]!!.jsonObject),
            WebWeaverClient.json.decodeFromJsonElement(subResponse["quota"]!!.jsonObject)
        )
    }

    override suspend fun getTrash(limit: Int?, context: IRequestContext): List<RemoteFile> {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetTrashRequest(limit)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["files"]!!.jsonArray.map { WebWeaverClient.json.decodeFromJsonElement(it) }
    }

    override suspend fun getTasks(context: IRequestContext): List<Task> {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetTasksRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]?.jsonArray?.map { WebWeaverClient.json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override suspend fun addTask(task: ITask, context: IRequestContext): ITask {
        return addTask(task.title, task.completed, task.description, task.dueDate?.time, task.startDate?.time, context)
    }

    override suspend fun addTask(title: String, completed: Boolean?, description: String?, dueDate: Long?, startDate: Long?, context: IRequestContext): Task {
        val request = OperatingScopeApiRequest(context)
        val id = request.addAddTaskRequest(title, completed, description, dueDate, startDate)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["entry"]!!)
    }

    override suspend fun readQuickMessages(exportSessionFile: Boolean?, context: IRequestContext): List<QuickMessage> {
        val request = GroupApiRequest(context)
        val id = request.addReadQuickMessagesRequest(exportSessionFile)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["messages"]!!.jsonArray.map { WebWeaverClient.json.decodeFromJsonElement(it) }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OperatingScope

        if (login != other.login) return false

        return true
    }

    override fun hashCode(): Int {
        return login.hashCode()
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
        val id = request.addGetProfileRequest(exportImage)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["profile"]!!)
    }

    override suspend fun exportProfileImage(context: IRequestContext): SessionFile {
        val request = UserApiRequest(context)
        val id = request.addExportProfileImageRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["image"]!!)
    }

    override suspend fun deleteProfileImage(context: IRequestContext) {
        val request = UserApiRequest(context)
        request.addDeleteProfileImageRequest()[1]
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override suspend fun importProfileImage(sessionFile: ISessionFile, context: IRequestContext) {
        val request = UserApiRequest(context)
        request.addImportProfileImageRequest(sessionFile.id)[1]
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override suspend fun getSystemNotifications(context: IRequestContext): List<SystemNotification> {
        val request = UserApiRequest(context)
        val id = request.addGetSystemNotificationsRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["messages"]?.jsonArray?.map { WebWeaverClient.json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override suspend fun addSessionFile(name: String, data: ByteArray, context: IRequestContext): SessionFile {
        val request = UserApiRequest(context)
        val id = request.addAddSessionFileRequest(name, data)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override fun getGroups(): List<Group> = groups

    override fun passwordMustChange(): Boolean = userData.passwordMustChange

    override suspend fun getAutoLoginUrl(disableLogout: Boolean?, disableReceptionOfQuickMessages: Boolean?, ensalveSession: Boolean?, locale: Locale?, pingMaster: Boolean?, sessionTimeout: Int?, targetData: JsonElement?, targetIframes: Boolean?, targetUrlPath: String?, context: IRequestContext): String {
        val request = UserApiRequest(context)
        val id = request.addGetAutoLoginUrlRequest(disableLogout, disableReceptionOfQuickMessages, ensalveSession, locale, pingMaster, sessionTimeout, targetData, targetIframes, targetUrlPath)[1]
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
        request.addRegisterServiceRequest(type, token, application, generateSecret, isOnline, managedObjects, unmanagedPriority)[1]
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override suspend fun unregisterService(type: ServiceType, token: String, context: IRequestContext) {
        val request = UserApiRequest(context)
        request.addUnregisterServiceRequest(type, token)[1]
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override suspend fun getEmailStatus(context: IRequestContext): Pair<Quota, Int> {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetEmailStateRequest()[1]
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
        val id = request.addGetEmailFoldersRequest()[1]
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

    override suspend fun getEmailSignature(context: IRequestContext): EmailSignature {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetEmailSignatureRequest()[1]
        val response = request.fireRequest()
        return WebWeaverClient.json.decodeFromJsonElement(ResponseUtil.getSubResponseResult(response.toJson(), id)["signature"]!!)
    }

    override suspend fun getAllTasks(context: IApiContext): List<Pair<ITask, IOperatingScope>> {
        check(context is ApiContext)
        val request = UserApiRequest(getRequestContext(context))
        val taskIds = request.addGetAllTasksRequest(this)
        val response = request.fireRequest().toJson().jsonArray
        val tasks = mutableListOf<Pair<ITask, IOperatingScope>>()
        val responses = response.filter { taskIds.contains(it.jsonObject["id"]!!.jsonPrimitive.int) }.map { it.jsonObject }
        responses.withIndex().forEach { (index, subResponse) ->
            if (index % 2 == 1) {
                val focus = responses[index - 1]["result"]!!.jsonObject
                assert(focus["method"]?.jsonPrimitive?.content == "set_focus")
                val memberLogin = focus["user"]!!.jsonObject["login"]!!.jsonPrimitive.content
                val member = context.findOperatingScope(memberLogin)!!
                subResponse["result"]!!.jsonObject["entries"]!!.jsonArray.forEach { taskResponse ->
                    tasks.add(Pair(Json.decodeFromJsonElement<Task>(taskResponse.jsonObject), member))
                }
            }
        }
        return tasks
    }

    override suspend fun getAllBoardNotifications(context: IApiContext): List<Pair<IBoardNotification, IGroup>> {
        check(context is ApiContext)
        val request = UserApiRequest(getRequestContext(context))
        val taskIds = request.addGetAllBoardNotificationsRequest(this)
        val response = request.fireRequest().toJson().jsonArray
        val notifications = mutableListOf<Pair<IBoardNotification, IGroup>>()
        val responses = response.filter { taskIds.contains(it.jsonObject["id"]!!.jsonPrimitive.int) }.map { it.jsonObject }
        responses.withIndex().forEach { (index, subResponse) ->
            if (index % 2 == 1) {
                val focus = responses[index - 1]["result"]!!.jsonObject
                assert(focus["method"]?.jsonPrimitive?.content == "set_focus")
                val memberLogin = focus["user"]!!.jsonObject["login"]!!.jsonPrimitive.content
                val member = context.findOperatingScope(memberLogin)!! as Group
                subResponse["result"]!!.jsonObject["entries"]!!.jsonArray.forEach { notificationResponse ->
                    notifications.add(Pair(Json.decodeFromJsonElement<BoardNotification>(notificationResponse.jsonObject), member))
                }
            }
        }
        return notifications
    }

    override suspend fun getAllPupilBoardNotifications(context: IApiContext): List<Pair<IBoardNotification, IGroup>> {
        check(context is ApiContext)
        val request = UserApiRequest(getRequestContext(context))
        val taskIds = request.addGetAllPupilBoardNotificationsRequest(this)
        val response = request.fireRequest().toJson().jsonArray
        val notifications = mutableListOf<Pair<IBoardNotification, IGroup>>()
        val responses = response.filter { taskIds.contains(it.jsonObject["id"]!!.jsonPrimitive.int) }.map { it.jsonObject }
        responses.withIndex().forEach { (index, subResponse) ->
            if (index % 2 == 1) {
                val focus = responses[index - 1]["result"]!!.jsonObject
                assert(focus["method"]?.jsonPrimitive?.content == "set_focus")
                val memberLogin = focus["user"]!!.jsonObject["login"]!!.jsonPrimitive.content
                val member = context.findOperatingScope(memberLogin)!! as Group
                subResponse["result"]!!.jsonObject["entries"]!!.jsonArray.forEach { taskResponse ->
                    notifications.add(Pair(Json.decodeFromJsonElement<BoardNotification>(taskResponse.jsonObject), member))
                }
            }
        }
        return notifications
    }

    override suspend fun getAllTeacherBoardNotifications(context: IApiContext): List<Pair<IBoardNotification, IGroup>> {
        check(context is ApiContext)
        val request = UserApiRequest(getRequestContext(context))
        val taskIds = request.addGetAllTeacherBoardNotificationsRequest(this)
        val response = request.fireRequest().toJson().jsonArray
        val notifications = mutableListOf<Pair<IBoardNotification, IGroup>>()
        val responses = response.filter { taskIds.contains(it.jsonObject["id"]!!.jsonPrimitive.int) }.map { it.jsonObject }
        responses.withIndex().forEach { (index, subResponse) ->
            if (index % 2 == 1) {
                val focus = responses[index - 1]["result"]!!.jsonObject
                assert(focus["method"]?.jsonPrimitive?.content == "set_focus")
                val memberLogin = focus["user"]!!.jsonObject["login"]!!.jsonPrimitive.content
                val member = context.findOperatingScope(memberLogin)!! as Group
                subResponse["result"]!!.jsonObject["entries"]!!.jsonArray.forEach { taskResponse ->
                    notifications.add(Pair(Json.decodeFromJsonElement<BoardNotification>(taskResponse.jsonObject), member))
                }
            }
        }
        return notifications
    }

    override suspend fun getUsers(getMiniatures: Boolean?, onlineOnly: Boolean?, context: IRequestContext): List<RemoteScope> {
        val request = UserApiRequest(context)
        val id = request.addGetMessengerUsersRequest(getMiniatures, onlineOnly)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["users"]?.jsonArray?.map { WebWeaverClient.json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override suspend fun sendQuickMessage(login: String, importSessionFile: ISessionFile?, text: String?, context: IRequestContext): QuickMessage {
        val request = UserApiRequest(context)
        val id = request.addSendQuickMessageRequest(login, importSessionFile, text)[1]
        val response = request.fireRequest()
        return WebWeaverClient.json.decodeFromJsonElement(ResponseUtil.getSubResponseResult(response.toJson(), id)["message"]!!)
    }

    override suspend fun addChat(login: String, context: IRequestContext): RemoteScope {
        val request = UserApiRequest(context)
        val id = request.addAddChatRequest(login)[1]
        val response = request.fireRequest()
        return WebWeaverClient.json.decodeFromJsonElement(ResponseUtil.getSubResponseResult(response.toJson(), id)["user"]!!)
    }

    override suspend fun removeChat(login: String, context: IRequestContext): RemoteScope {
        val request = UserApiRequest(context)
        val id = request.addRemoveChatRequest(login)[1]
        val response = request.fireRequest()
        return WebWeaverClient.json.decodeFromJsonElement(ResponseUtil.getSubResponseResult(response.toJson(), id)["user"]!!)
    }

    override suspend fun getHistory(exportSessionFile: Boolean?, startId: Int?, context: IRequestContext): List<QuickMessage> {
        val request = UserApiRequest(context)
        val id = request.addGetHistoryRequest(exportSessionFile, startId)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["messages"]?.jsonArray?.map { WebWeaverClient.json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override suspend fun getNotes(context: IRequestContext): List<Note> {
        val request = UserApiRequest(context)
        val id = request.addGetNotesRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]?.jsonArray?.map { WebWeaverClient.json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override suspend fun addNote(note: INote, context: IRequestContext): Note {
        return addNote(note.text, note.title, note.color, context)
    }

    override suspend fun addNote(text: String, title: String, color: NoteColor?, context: IRequestContext): Note {
        val request = UserApiRequest(context)
        val id = request.addAddNoteRequest(text, title, color)[1]
        val response = request.fireRequest()
        return WebWeaverClient.json.decodeFromJsonElement(ResponseUtil.getSubResponseResult(response.toJson(), id)["entry"]!!)
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
        val id = request.addGetMembersRequest(miniatures, onlineOnly)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["users"]?.jsonArray?.map { WebWeaverClient.json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override suspend fun sendGlobalQuickMessage(sessionFile: ISessionFile?, text: String?, context: IRequestContext): IQuickMessage {
        val request = GroupApiRequest(context)
        val id = request.addSendQuickMessageRequest(sessionFile?.id, text)[1]
        val response = request.fireRequest()
        return WebWeaverClient.json.decodeFromJsonElement(ResponseUtil.getSubResponseResult(response.toJson(), id)["message"]!!)
    }

    override suspend fun getBoardNotifications(context: IRequestContext): List<BoardNotification> {
        val request = GroupApiRequest(context)
        val id = request.addGetBoardNotificationsRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]?.jsonArray?.map { WebWeaverClient.json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override suspend fun addBoardNotification(notification: IBoardNotification, context: IRequestContext): BoardNotification {
        return addBoardNotification(notification.title, notification.text, notification.color, null, context)
    }

    override suspend fun addBoardNotification(title: String, text: String, color: BoardNotificationColor?, killDate: Date?, context: IRequestContext): BoardNotification {
        val request = GroupApiRequest(context)
        val id = request.addAddBoardNotificationRequest(title, text, color, killDate?.time)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["entry"]!!)
    }

    override suspend fun getTeacherBoardNotifications(context: IRequestContext): List<BoardNotification> {
        val request = GroupApiRequest(context)
        val id = request.addGetTeacherBoardNotificationsRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]?.jsonArray?.map { WebWeaverClient.json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override suspend fun addTeacherBoardNotification(notification: IBoardNotification, context: IRequestContext): BoardNotification {
        return addTeacherBoardNotification(notification.title, notification.text, notification.color, null, context)
    }

    override suspend fun addTeacherBoardNotification(title: String, text: String, color: BoardNotificationColor?, killDate: Date?, context: IRequestContext): BoardNotification {
        val request = GroupApiRequest(context)
        val id = request.addAddTeacherBoardNotificationRequest(title, text, color, killDate?.time)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["entry"]!!)
    }

    override suspend fun getPupilBoardNotifications(context: IRequestContext): List<BoardNotification> {
        val request = GroupApiRequest(context)
        val id = request.addGetPupilBoardNotificationsRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]?.jsonArray?.map { WebWeaverClient.json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override suspend fun addPupilBoardNotification(notification: IBoardNotification, context: IRequestContext): BoardNotification {
        return addPupilBoardNotification(notification.title, notification.text, notification.color, null, context)
    }

    override suspend fun addPupilBoardNotification(title: String, text: String, color: BoardNotificationColor?, killDate: Date?, context: IRequestContext): BoardNotification {
        val request = GroupApiRequest(context)
        val id = request.addAddPupilBoardNotificationRequest(title, text, color, killDate?.time)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["entry"]!!)
    }

    override suspend fun getForumState(context: IRequestContext): Pair<Quota, ForumSettings> {
        val request = GroupApiRequest(context)
        val id = request.addGetForumStateRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Pair(Json.decodeFromJsonElement(subResponse["quota"]!!.jsonObject), WebWeaverClient.json.decodeFromJsonElement(subResponse["settings"]!!.jsonObject))
    }

    override suspend fun getForumPosts(parentId: String?, context: IRequestContext): List<IForumPost> {
        val request = GroupApiRequest(context)
        val id = request.addGetForumPostsRequest(parentId = parentId)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]!!.jsonArray.map { WebWeaverClient.json.decodeFromJsonElement<ForumPost>(it) }
    }

    override suspend fun getForumPostsTree(parentId: String?, context: IRequestContext): List<ForumPost> {
        val request = GroupApiRequest(context)
        val id = request.addGetForumPostsRequest(parentId = parentId)[1]
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
        val id = request.addAddForumPostRequest(title, text, icon, parent?.id ?: "0", importSessionFile, importSessionFiles, replyNotificationMe)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["entry"]!!)
    }

    override suspend fun getWikiPage(name: String?, context: IRequestContext): WikiPage? {
        val request = GroupApiRequest(context)
        val id = request.addGetWikiPageRequest(name)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["page"]!!)
    }

    override suspend fun getCourseletState(context: IRequestContext): Pair<Quota, String> {
        val request = GroupApiRequest(context)
        val id = request.addGetCourseletsStateRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Pair(
            WebWeaverClient.json.decodeFromJsonElement(subResponse["quota"]!!.jsonObject),
            subResponse["runtime_version_hash"]!!.jsonPrimitive.content
        )
    }

    override suspend fun getCourseletConfiguration(context: IRequestContext): JsonElement {
        val request = GroupApiRequest(context)
        val id = request.addGetCourseletsConfigurationRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["configuration"]!!.jsonObject
    }

    override suspend fun getCourselets(context: IRequestContext): List<Courselet> {
        val request = GroupApiRequest(context)
        val id = request.addGetCourseletsRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["courselets"]!!.jsonArray.map { WebWeaverClient.json.decodeFromJsonElement(it) }
    }

    override suspend fun getCourseletMappings(context: IRequestContext): List<CourseletMapping> {
        val request = GroupApiRequest(context)
        val id = request.addGetCourseletMappingsRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["mappings"]!!.jsonArray.map { WebWeaverClient.json.decodeFromJsonElement(it) }
    }

    override suspend fun addCourseletMapping(name: String, context: IRequestContext): CourseletMapping {
        val request = GroupApiRequest(context)
        val id = request.addAddCourseletMappingRequest(name)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["mapping"]!!)
    }

    override suspend fun exportCourseletRuntime(context: IRequestContext): FileDownloadUrl {
        val request = GroupApiRequest(context)
        val id = request.addExportCourseletRuntimeRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return WebWeaverClient.json.decodeFromJsonElement(subResponse["file"]!!.jsonObject)
    }
}