package de.deftk.lonet.api.model.abstract

import de.deftk.lonet.api.model.Permission
import de.deftk.lonet.api.model.feature.Quota
import de.deftk.lonet.api.model.feature.Task
import de.deftk.lonet.api.model.feature.abstract.IContactHolder
import de.deftk.lonet.api.model.feature.abstract.IFileStorage
import de.deftk.lonet.api.model.feature.abstract.IMailbox
import de.deftk.lonet.api.model.feature.abstract.ITaskList
import de.deftk.lonet.api.model.feature.contact.Contact
import de.deftk.lonet.api.model.feature.contact.Gender
import de.deftk.lonet.api.model.feature.files.FileStorageSettings
import de.deftk.lonet.api.model.feature.files.OnlineFile
import de.deftk.lonet.api.model.feature.files.filters.FileFilter
import de.deftk.lonet.api.model.feature.mailbox.EmailFolder
import de.deftk.lonet.api.request.OperatorApiRequest
import de.deftk.lonet.api.response.ResponseUtil

abstract class AbstractOperator(private val login: String, private val name: String, val permissions: List<Permission>, private val type: ManageableType) : IManageable, IMailbox, IFileStorage, ITaskList, IContactHolder {

    abstract fun getContext(): IContext

    override fun getEmailStatus(overwriteCache: Boolean): Pair<Quota, Int> {
        val request = OperatorApiRequest(this)
        val id = request.addGetEmailStateRequest()[1]
        val response = request.fireRequest(getContext(), overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Pair(Quota.fromJson(subResponse.get("quota").asJsonObject), subResponse.get("unread_messages").asInt)
    }

    override fun getEmailQuota(overwriteCache: Boolean): Quota {
        return getEmailStatus(overwriteCache).first
    }

    override fun getUnreadEmailCount(overwriteCache: Boolean): Int {
        return getEmailStatus(overwriteCache).second
    }

    override fun getEmailFolders(overwriteCache: Boolean): List<EmailFolder> {
        val request = OperatorApiRequest(this)
        val id = request.addGetEmailFoldersRequest()[1]
        val response = request.fireRequest(getContext(), overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("folders").asJsonArray.map { EmailFolder.fromJson(it.asJsonObject, this) }
    }

    override fun sendEmail(to: String, subject: String, plainBody: String, text: String?, bcc: String?, cc: String?) {
        val request = OperatorApiRequest(this)
        request.addSendEmailRequest(to, subject, plainBody, text, bcc, cc)
        val response = request.fireRequest(getContext(), true)
        ResponseUtil.checkSuccess(response.toJson())
    }

    override fun getFileStorageState(overwriteCache: Boolean): Pair<FileStorageSettings, Quota> {
        val request = OperatorApiRequest(this)
        val id = request.addGetFileStorageStateRequest()[1]
        val response = request.fireRequest(getContext(), overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return Pair(FileStorageSettings.fromJson(subResponse.get("settings").asJsonObject), Quota.fromJson(subResponse.get("quota").asJsonObject))
    }

    override fun getFileStorageFiles(filter: FileFilter?, overwriteCache: Boolean): List<OnlineFile> {
        val request = OperatorApiRequest(this)
        val id = request.addGetFileStorageFilesRequest(
                "/",
                recursive = false,
                getFiles = true,
                getFolders = true,
                searchString = filter?.searchTerm,
                searchMode = filter?.searchMode
        )[1]
        val response = request.fireRequest(overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("entries")?.asJsonArray?.map { OnlineFile.fromJson(it.asJsonObject, this) }
                ?: emptyList()
    }

    override fun createFolder(name: String, description: String?): OnlineFile {
        val request = OperatorApiRequest(this)
        val id = request.addAddFolderRequest("/", name, description)[1]
        val response = request.fireRequest(true)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return OnlineFile.fromJson(subResponse.get("folder").asJsonObject, this)
    }

    override fun getTasks(overwriteCache: Boolean): List<Task> {
        val request = OperatorApiRequest(this)
        val id = request.addGetTasksRequest()[1]
        val response = request.fireRequest(overwriteCache)
        val subResponse = ResponseUtil.getSubResponseResult(response.toJson(), id)
        return subResponse.get("entries")?.asJsonArray?.map { Task.fromJson(it.asJsonObject, this) } ?: emptyList()
    }

    override fun getContacts(overwriteCache: Boolean): List<Contact> {
        val request = OperatorApiRequest(this)
        val id = request.addGetContactsRequest()[1]
        val response = request.fireRequest(overwriteCache)
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