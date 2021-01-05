package de.deftk.lonet.api.implementation

import de.deftk.lonet.api.implementation.feature.calendar.Appointment
import de.deftk.lonet.api.implementation.feature.contacts.Contact
import de.deftk.lonet.api.implementation.feature.filestorage.RemoteFile
import de.deftk.lonet.api.implementation.feature.tasks.Task
import de.deftk.lonet.api.model.IApiContext
import de.deftk.lonet.api.model.IOperatingScope
import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.Permission
import de.deftk.lonet.api.model.feature.Quota
import de.deftk.lonet.api.model.feature.calendar.IAppointment
import de.deftk.lonet.api.model.feature.contacts.Gender
import de.deftk.lonet.api.model.feature.contacts.IContact
import de.deftk.lonet.api.model.feature.filestorage.FileStorageSettings
import de.deftk.lonet.api.model.feature.filestorage.filter.FileFilter
import de.deftk.lonet.api.model.feature.filestorage.session.ISessionFile
import de.deftk.lonet.api.model.feature.tasks.ITask
import de.deftk.lonet.api.request.OperatingScopeApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import de.deftk.lonet.api.utils.PlatformUtil
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import java.util.*

@Serializable
abstract class OperatingScope : IOperatingScope {

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
}