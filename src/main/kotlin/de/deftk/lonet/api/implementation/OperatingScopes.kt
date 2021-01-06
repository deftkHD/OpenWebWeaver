package de.deftk.lonet.api.implementation

import de.deftk.lonet.api.LoNetClient
import de.deftk.lonet.api.exception.ApiException
import de.deftk.lonet.api.implementation.feature.board.BoardNotification
import de.deftk.lonet.api.implementation.feature.calendar.Appointment
import de.deftk.lonet.api.implementation.feature.contacts.Contact
import de.deftk.lonet.api.implementation.feature.courselets.Courselet
import de.deftk.lonet.api.implementation.feature.courselets.CourseletMapping
import de.deftk.lonet.api.implementation.feature.filestorage.RemoteFile
import de.deftk.lonet.api.implementation.feature.filestorage.session.SessionFile
import de.deftk.lonet.api.implementation.feature.forum.ForumPost
import de.deftk.lonet.api.implementation.feature.mailbox.EmailFolder
import de.deftk.lonet.api.implementation.feature.mailbox.EmailSignature
import de.deftk.lonet.api.implementation.feature.systemnotification.SystemNotification
import de.deftk.lonet.api.implementation.feature.tasks.Task
import de.deftk.lonet.api.implementation.feature.wiki.WikiPage
import de.deftk.lonet.api.model.*
import de.deftk.lonet.api.model.Locale
import de.deftk.lonet.api.model.feature.FileDownloadUrl
import de.deftk.lonet.api.model.feature.Quota
import de.deftk.lonet.api.model.feature.ServiceType
import de.deftk.lonet.api.model.feature.board.BoardNotificationColor
import de.deftk.lonet.api.model.feature.board.IBoardNotification
import de.deftk.lonet.api.model.feature.calendar.IAppointment
import de.deftk.lonet.api.model.feature.contacts.Gender
import de.deftk.lonet.api.model.feature.contacts.IContact
import de.deftk.lonet.api.model.feature.filestorage.FileStorageSettings
import de.deftk.lonet.api.model.feature.filestorage.filter.FileFilter
import de.deftk.lonet.api.model.feature.filestorage.session.ISessionFile
import de.deftk.lonet.api.model.feature.forum.ForumPostIcon
import de.deftk.lonet.api.model.feature.forum.ForumSettings
import de.deftk.lonet.api.model.feature.forum.IForumPost
import de.deftk.lonet.api.model.feature.mailbox.ReferenceMode
import de.deftk.lonet.api.model.feature.tasks.ITask
import de.deftk.lonet.api.request.GroupApiRequest
import de.deftk.lonet.api.request.OperatingScopeApiRequest
import de.deftk.lonet.api.request.UserApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import de.deftk.lonet.api.serialization.BooleanFromIntSerializer
import de.deftk.lonet.api.utils.PlatformUtil
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
        RequestContext(login, apiContext.getSessionId(), apiContext.getRequestURL(), apiContext.getRequestHandler())

    override fun getAppointments(context: IRequestContext): List<Appointment> {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetAppointmentsRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]!!.jsonArray.map { Json.decodeFromJsonElement(it) }
    }

    override fun addAppointment(appointment: IAppointment, context: IRequestContext): Appointment {
        return addAppointment(
            appointment.getTitle(),
            appointment.getDescription(),
            appointment.getEndDate(),
            appointment.getEndDateIso(),
            appointment.getLocation(),
            appointment.getRrule(),
            appointment.getStartDate(),
            appointment.getStartDateIso(),
            appointment.getUid(),
            context
        )
    }

    override fun addAppointment(title: String, description: String?, endDate: Date?, endDateIso: String?, location: String?, rrule: String?, startDate: Date?, startDateIso: String?, uid: String?, context: IRequestContext): Appointment {
        val request = OperatingScopeApiRequest(context)
        val id = request.addAddAppointmentRequest(title, description, endDate?.time, endDateIso, location, rrule, startDate?.time, startDateIso, login)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["entry"]!!)
    }

    override fun getContacts(context: IRequestContext): List<Contact> {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetContactsRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]?.jsonArray?.map { Json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override fun addContact(contact: IContact, context: IRequestContext): Contact {
        return addContact(contact.getBirthday(), contact.getBusinessCity(), contact.getBusinessCoords(), contact.getBusinessCountry(), contact.getBusinessFax(), contact.getBusinessPhone(), contact.getBusinessPostalCode(), contact.getBusinessState(), contact.getBusinessStreet(), contact.getBusinessStreet2(), contact.getBusinessStreet3(), contact.getCategories(), contact.getCompany(), contact.getCompanyType(), contact.getEmailAddress2(), contact.getEmailAddress3(), contact.getEmailAddress(), contact.getFirstName(), contact.getFullName(), contact.getGender(), contact.getHobby(), contact.getHomeCity(), contact.getHomeCoords(), contact.getHomeCountry(), contact.getHomeFax(), contact.getHomePhone(), contact.getHomePostalCode(), contact.getHomeState(), contact.getHomeStreet(), contact.getHomeStreet2(), contact.getHomeStreet3(), contact.getJobTitle(), contact.getJobTitle2(), contact.getLastName(), contact.getMiddleName(), contact.getMobilePhone(), contact.getNickName(), contact.getNotes(), contact.getSubjects(), contact.getSuffix(), contact.getTitle(), contact.getUid(), contact.getWebPage(), context)
    }

    override fun addContact(
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
        return Json.decodeFromJsonElement(subResponse["entry"]!!)
    }

    override fun getFiles(limit: Int?, offset: Int?, filter: FileFilter?, context: IRequestContext): List<RemoteFile> {
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
        return subResponse["entries"]?.jsonArray?.map { Json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override fun addFile(name: String, data: ByteArray, description: String?, context: IRequestContext): RemoteFile {
        val request = OperatingScopeApiRequest(context)
        val id = request.addAddFileRequest(PlatformUtil.base64EncodeToString(data), "/", name, description)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override fun addSparseFile(name: String, size: Int, description: String?, context: IRequestContext): RemoteFile {
        val request = OperatingScopeApiRequest(context)
        val id = request.addAddSparseFileRequest("/", name, size, description, login)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override fun importSessionFile(sessionFile: ISessionFile, createCopy: Boolean?, description: String?, context: IRequestContext): RemoteFile {
        val request = OperatingScopeApiRequest(context)
        val id = request.addImportSessionFileRequest(sessionFile.getId(), createCopy, description, folderId = "/")[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["file"]!!)
    }

    override fun addFolder(name: String, description: String?, context: IRequestContext): RemoteFile {
        val request = OperatingScopeApiRequest(context)
        val id = request.addAddFolderRequest("/", name, description)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["folder"]!!)
    }

    override fun setReadable(readable: Boolean, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        request.addSetFolderRequest("/", readable = readable)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun setWritable(writable: Boolean, context: IRequestContext) {
        val request = OperatingScopeApiRequest(context)
        request.addSetFolderRequest("/", writable = writable)
        val response = request.fireRequest()
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun getFileStorageState(context: IRequestContext): Pair<FileStorageSettings, Quota> {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetFileStorageStateRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Pair(
            Json.decodeFromJsonElement(subResponse["settings"]!!.jsonObject),
            Json.decodeFromJsonElement(subResponse["quota"]!!.jsonObject)
        )
    }

    override fun getTrash(limit: Int?, context: IRequestContext): List<RemoteFile> {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetTrashRequest(limit)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["files"]!!.jsonArray.map { Json.decodeFromJsonElement(it) }
    }

    override fun getTasks(context: IRequestContext): List<Task> {
        val request = OperatingScopeApiRequest(context)
        val id = request.addGetTasksRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]?.jsonArray?.map { Json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override fun addTask(task: ITask, context: IRequestContext): ITask {
        return addTask(task.getTitle(), task.isCompleted(), task.getDescription(), task.getEndDate()?.time, task.getStartDate()?.time, context)
    }

    override fun addTask(title: String, completed: Boolean?, description: String?, dueDate: Long?, startDate: Long?, context: IRequestContext): Task {
        val request = OperatingScopeApiRequest(context)
        val id = request.addAddTaskRequest(title, completed, description, dueDate, startDate)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["entry"]!!)
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

    override fun getAllTasks(context: IApiContext): List<Pair<ITask, IOperatingScope>> {
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

    override fun getAllBoardNotifications(context: IApiContext): List<Pair<IBoardNotification, IGroup>> {
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
                subResponse["result"]!!.jsonObject["entries"]!!.jsonArray.forEach { taskResponse ->
                    notifications.add(Pair(Json.decodeFromJsonElement<BoardNotification>(taskResponse.jsonObject), member))
                }
            }
        }
        return notifications
    }

    override fun getAllPupilBoardNotifications(context: IApiContext): List<Pair<IBoardNotification, IGroup>> {
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

    override fun getAllTeacherBoardNotifications(context: IApiContext): List<Pair<IBoardNotification, IGroup>> {
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
    private val reducedRights: List<Permission> = emptyList(),
    @SerialName("member_rights")
    private val memberRights: List<Permission> = emptyList()
) : IGroup, OperatingScope() {

    override fun getReducedRights(): List<Permission> = reducedRights

    override fun getMemberRights(): List<Permission> = memberRights

    override fun getMembers(context: IRequestContext): List<RemoteScope> {
        val request = GroupApiRequest(context)
        val id = request.addGetMembersRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["users"]?.jsonArray?.map { Json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override fun getBoardNotifications(context: IRequestContext): List<BoardNotification> {
        val request = GroupApiRequest(context)
        val id = request.addGetBoardNotificationsRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]?.jsonArray?.map { Json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override fun addBoardNotification(notification: IBoardNotification, context: IRequestContext): BoardNotification {
        return addBoardNotification(notification.getTitle(), notification.getText(), notification.getColor(), notification.getKillDate(), context)
    }

    override fun addBoardNotification(title: String, text: String, color: BoardNotificationColor?, killDate: Date?, context: IRequestContext): BoardNotification {
        val request = GroupApiRequest(context)
        val id = request.addAddBoardNotificationRequest(title, text, color, killDate?.time)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["entry"]!!)
    }

    override fun getTeacherBoardNotifications(context: IRequestContext): List<BoardNotification> {
        val request = GroupApiRequest(context)
        val id = request.addGetTeacherBoardNotificationsRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]?.jsonArray?.map { Json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override fun addTeacherBoardNotification(notification: IBoardNotification, context: IRequestContext): BoardNotification {
        return addTeacherBoardNotification(notification.getTitle(), notification.getText(), notification.getColor(), notification.getKillDate(), context)
    }

    override fun addTeacherBoardNotification(title: String, text: String, color: BoardNotificationColor?, killDate: Date?, context: IRequestContext): BoardNotification {
        val request = GroupApiRequest(context)
        val id = request.addAddTeacherBoardNotificationRequest(title, text, color, killDate?.time)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["entry"]!!)
    }

    override fun getPupilBoardNotifications(context: IRequestContext): List<BoardNotification> {
        val request = GroupApiRequest(context)
        val id = request.addGetPupilBoardNotificationsRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["entries"]?.jsonArray?.map { Json.decodeFromJsonElement(it) } ?: emptyList()
    }

    override fun addPupilBoardNotification(notification: IBoardNotification, context: IRequestContext): BoardNotification {
        return addPupilBoardNotification(notification.getTitle(), notification.getText(), notification.getColor(), notification.getKillDate(), context)
    }

    override fun addPupilBoardNotification(title: String, text: String, color: BoardNotificationColor?, killDate: Date?, context: IRequestContext): BoardNotification {
        val request = GroupApiRequest(context)
        val id = request.addAddPupilBoardNotificationRequest(title, text, color, killDate?.time)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["entry"]!!)
    }

    override fun getForumState(context: IRequestContext): Pair<Quota, ForumSettings> {
        val request = GroupApiRequest(context)
        val id = request.addGetForumStateRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Pair(Json.decodeFromJsonElement(subResponse["quota"]!!.jsonObject), Json.decodeFromJsonElement(subResponse["settings"]!!.jsonObject))
    }

    override fun getForumPosts(parentId: String?, context: IRequestContext): List<ForumPost> {
        val request = GroupApiRequest(context)
        val id = request.addGetForumPostsRequest(parentId = parentId)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        val allPosts = subResponse["entries"]!!.jsonArray.map { Json.decodeFromJsonElement<ForumPost>(it) }
        // build tree structure
        val rootPosts = mutableListOf<ForumPost>()
        val tmpPosts = mutableMapOf<String, ForumPost>()
        allPosts.forEach { post -> tmpPosts[post.getId()] = post }
        allPosts.forEach { post ->
            if (post.getParentId() != "0") {
                val parent = tmpPosts[post.getParentId()] ?: throw ApiException("Comment has invalid parent!")
                parent.commentLoaded(post)
            } else {
                rootPosts.add(post)
            }
        }
        return rootPosts
    }

    override fun addForumPost(title: String, text: String, icon: ForumPostIcon, parent: IForumPost?, importSessionFile: String?, importSessionFiles: Array<String>?, replyNotificationMe: Boolean?, context: IRequestContext): ForumPost {
        val request = GroupApiRequest(context)
        val id = request.addAddForumPostRequest(title, text, icon, parent?.getId() ?: "0", importSessionFile, importSessionFiles, replyNotificationMe)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["entry"]!!)
    }

    override fun getWikiPage(name: String?, context: IRequestContext): WikiPage? {
        val request = GroupApiRequest(context)
        val id = request.addGetWikiPageRequest(name)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["page"]!!)
    }

    override fun getCourseletState(context: IRequestContext): Pair<Quota, String> {
        val request = GroupApiRequest(context)
        val id = request.addGetCourseletsStateRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Pair(
            Json.decodeFromJsonElement(subResponse["quota"]!!.jsonObject),
            subResponse["runtime_version_hash"]!!.jsonPrimitive.content
        )
    }

    override fun getCourseletConfiguration(context: IRequestContext): JsonElement {
        val request = GroupApiRequest(context)
        val id = request.addGetCourseletsConfigurationRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["configuration"]!!.jsonObject
    }

    override fun getCourselets(context: IRequestContext): List<Courselet> {
        val request = GroupApiRequest(context)
        val id = request.addGetCourseletsRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["courselets"]!!.jsonArray.map { Json.decodeFromJsonElement(it) }
    }

    override fun getCourseletMappings(context: IRequestContext): List<CourseletMapping> {
        val request = GroupApiRequest(context)
        val id = request.addGetCourseletMappingsRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse["mappings"]!!.jsonArray.map { Json.decodeFromJsonElement(it) }
    }

    override fun addCourseletMapping(name: String, context: IRequestContext): CourseletMapping {
        val request = GroupApiRequest(context)
        val id = request.addAddCourseletMappingRequest(name)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["mapping"]!!)
    }

    override fun exportCourseletRuntime(context: IRequestContext): FileDownloadUrl {
        val request = GroupApiRequest(context)
        val id = request.addExportCourseletRuntimeRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Json.decodeFromJsonElement(subResponse["file"]!!.jsonObject)
    }
}