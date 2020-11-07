package de.deftk.lonet.api.model.abstract

import de.deftk.lonet.api.model.Permission
import de.deftk.lonet.api.model.feature.Quota
import de.deftk.lonet.api.model.feature.Task
import de.deftk.lonet.api.model.feature.abstract.*
import de.deftk.lonet.api.model.feature.calendar.Appointment
import de.deftk.lonet.api.model.feature.contact.Contact
import de.deftk.lonet.api.model.feature.contact.Gender
import de.deftk.lonet.api.model.feature.files.FileStorageSettings
import de.deftk.lonet.api.model.feature.files.OnlineFile
import de.deftk.lonet.api.model.feature.files.filters.FileFilter
import de.deftk.lonet.api.model.feature.mailbox.EmailFolder
import de.deftk.lonet.api.model.feature.mailbox.EmailSignature
import de.deftk.lonet.api.request.OperatorApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import java.util.*

abstract class AbstractOperator(private val login: String, private val name: String, val baseRights: List<Permission>, val effectiveRights: List<Permission>, private val type: ManageableType) : IManageable, IMailbox, IFileStorage, ITaskList, IContactHolder, ICalendar {

    abstract fun getContext(): IContext

    override fun getEmailStatus(): Pair<Quota, Int> {
        val request = OperatorApiRequest(this)
        val id = request.addGetEmailStateRequest()[1]
        val response = request.fireRequest(getContext())
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Pair(Quota.fromJson(subResponse.get("quota").asJsonObject), subResponse.get("unread_messages").asInt)
    }

    override fun getEmailQuota(): Quota {
        return getEmailStatus().first
    }

    override fun getUnreadEmailCount(): Int {
        return getEmailStatus().second
    }

    override fun getEmailFolders(): List<EmailFolder> {
        val request = OperatorApiRequest(this)
        val id = request.addGetEmailFoldersRequest()[1]
        val response = request.fireRequest(getContext())
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("folders").asJsonArray.map { EmailFolder.fromJson(it.asJsonObject, this) }
    }

    override fun addEmailFolder(name: String) {
        val request = OperatorApiRequest(this)
        request.addAddEmailFolderRequest(name)
        val response = request.fireRequest(getContext())
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun sendEmail(to: String, subject: String, plainBody: String, text: String?, bcc: String?, cc: String?) {
        val request = OperatorApiRequest(this)
        request.addSendEmailRequest(to, subject, plainBody, text, bcc, cc)
        val response = request.fireRequest(getContext())
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun getEmailSignature(): EmailSignature {
        val request = OperatorApiRequest(this)
        val id = request.addGetEmailSignatureRequest()[1]
        val response = request.fireRequest(getContext())
        return EmailSignature.fromJson(ResponseUtil.getSubResponseResult(response.toJson(), id).get("signature").asJsonObject, this)
    }

    override fun getFileStorageState(): Pair<FileStorageSettings, Quota> {
        val request = OperatorApiRequest(this)
        val id = request.addGetFileStorageStateRequest()[1]
        val response = request.fireRequest(getContext())
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Pair(FileStorageSettings.fromJson(subResponse.get("settings").asJsonObject), Quota.fromJson(subResponse.get("quota").asJsonObject))
    }

    override fun getFiles(filter: FileFilter?): List<OnlineFile> {
        val request = OperatorApiRequest(this)
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
        return subResponse.get("entries")?.asJsonArray?.map { OnlineFile.fromJson(it.asJsonObject, this) }
                ?: emptyList()
    }

    override fun addFolder(name: String, description: String?): OnlineFile {
        val request = OperatorApiRequest(this)
        val id = request.addAddFolderRequest("/", name, description)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return OnlineFile.fromJson(subResponse.get("folder").asJsonObject, this)
    }

    override fun getTrash(limit: Int?): List<OnlineFile> {
        val request = OperatorApiRequest(this)
        val id = request.addGetTrashRequest(limit)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("files").asJsonArray.map { OnlineFile.fromJson(it.asJsonObject, this) }
    }

    override fun getTasks(): List<Task> {
        val request = OperatorApiRequest(this)
        val id = request.addGetTasksRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("entries")?.asJsonArray?.map { Task.fromJson(it.asJsonObject, this) } ?: emptyList()
    }

    override fun addTask(title: String, completed: Boolean?, description: String?, dueDate: Date?, startDate: Date?): Task {
        val request = OperatorApiRequest(this)
        val id = request.addAddTaskRequest(title, completed, description, dueDate, startDate)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Task.fromJson(subResponse.get("entry").asJsonObject, this)
    }

    override fun getContacts(): List<Contact> {
        val request = OperatorApiRequest(this)
        val id = request.addGetContactsRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("entries")?.asJsonArray?.map { Contact.fromJson(it.asJsonObject, this) } ?: emptyList()
    }

    override fun addContact(categories: String?, firstName: String?, lastName: String?, homeStreet: String?, homeStreet2: String?, homePostalCode: String?, homeCity: String?, homeState: String?, homeCountry: String?, homeCoords: String?, homePhone: String?, homeFax: String?, mobilePhone: String?, birthday: String?, email: String?, gender: Gender?, hobby: String?, notes: String?, website: String?, company: String?, companyType: String?, jobTitle: String?): Contact {
        val request = OperatorApiRequest(this)
        val id = request.addAddContactRequest(categories, firstName, lastName, homeStreet, homeStreet2, homePostalCode, homeCity, homeState, homeCountry, homeCoords, homePhone, homeFax, mobilePhone, birthday, email, gender, hobby, notes, website, company, companyType, jobTitle)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Contact.fromJson(subResponse.get("entry").asJsonObject, this)
    }

    override fun getAppointments(): List<Appointment> {
        val request = OperatorApiRequest(this)
        val id = request.addGetAppointmentsRequest()[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.getAsJsonArray("entries").map { Appointment.fromJson(it.asJsonObject, this) }
    }

    override fun addAppointment(title: String, description: String?, endDate: Date?, endDateIso: String?, location: String?, rrule: String?, startDate: Date?, startDateIso: String?): Appointment {
        val request = OperatorApiRequest(this)
        val id = request.addAddAppointmentRequest(title, description, endDate, endDateIso, location, rrule, startDate, startDateIso, login)[1]
        val response = request.fireRequest()
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Appointment.fromJson(subResponse.get("entry").asJsonObject, this)
    }

    override fun getLogin(): String {
        return login
    }

    override fun getName(): String {
        return name
    }

    override fun getType(): ManageableType {
        return type
    }

}