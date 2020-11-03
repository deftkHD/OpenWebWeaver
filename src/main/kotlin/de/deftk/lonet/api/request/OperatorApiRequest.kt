@file:Suppress("DuplicatedCode")

package de.deftk.lonet.api.request

import com.google.gson.JsonObject
import de.deftk.lonet.api.model.abstract.AbstractOperator
import de.deftk.lonet.api.model.feature.contact.Gender
import de.deftk.lonet.api.model.feature.files.SearchMode
import de.deftk.lonet.api.response.ApiResponse
import java.util.*

open class OperatorApiRequest(val operator: AbstractOperator) : ApiRequest() {

    fun addGetFileStorageStateRequest(login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("files", login),
                addRequest("get_state", null)
        )
    }

    //TODO implement
    fun addGetFileStorageSettingsRequest(login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("files", login),
                addRequest("get_settings", null)
        )
    }

    //TODO implement
    fun addSetFileStorageSettingsRequest(selfUploadNotification: Boolean? = null, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        if (selfUploadNotification != null) requestProperties.addProperty("upload_notification_me", asApiBoolean(selfUploadNotification))
        return listOf(
                addSetFocusRequest("files", login),
                addRequest("set_settings", requestProperties)
        )
    }

    fun addGetFileStorageFilesRequest(folderId: String? = null, getFiles: Boolean? = null, getFolders: Boolean? = null, getRoot: Boolean? = null, limit: Int? = null, offset: Int? = null, recursive: Boolean? = null, searchMode: SearchMode? = null, searchString: String? = null, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        if (folderId != null) requestProperties.addProperty("folder_id", folderId)
        requestProperties.addProperty("get_file_download_url", 0)
        if (getFiles != null) requestProperties.addProperty("get_files", asApiBoolean(getFiles))
        if (getFolders != null) requestProperties.addProperty("get_folders", asApiBoolean(getFolders))
        if (getRoot != null) requestProperties.addProperty("get_root", asApiBoolean(getRoot))
        if (limit != null) requestProperties.addProperty("limit", limit)
        if (offset != null) requestProperties.addProperty("offset", offset)
        if (recursive != null) requestProperties.addProperty("recursive", asApiBoolean(recursive))
        if (searchMode != null) requestProperties.addProperty("search_option", searchMode.id)
        if (searchString != null) requestProperties.addProperty("search_string", searchString)
        return listOf(
                addSetFocusRequest("files", login),
                addRequest("get_entries", requestProperties)
        )
    }

    fun addGetFileDownloadUrl(fileId: String, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        requestProperties.addProperty("id", fileId)
        return listOf(
                addSetFocusRequest("files", login),
                addRequest("get_file_download_url", requestProperties)
        )
    }

    fun addUpdateFileRequest(fileId: String, description: String? = null, name: String? = null, parentId: String? = null, selfDownloadNotification: Boolean? = null, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        requestProperties.addProperty("id", fileId)
        if (description != null) requestProperties.addProperty("description", description)
        if (name != null) requestProperties.addProperty("name", name)
        if (parentId != null) requestProperties.addProperty("folder_id", name)
        if (selfDownloadNotification != null) requestProperties.addProperty("download_notification_me", asApiBoolean(selfDownloadNotification))
        return listOf(
                addSetFocusRequest("files", login),
                addRequest("set_file", requestProperties)
        )
    }

    fun addDeleteFileRequest(fileId: String, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        requestProperties.addProperty("id", fileId)
        return listOf(
                addSetFocusRequest("files", login),
                addRequest("delete_file", requestProperties)
        )
    }

    fun addAddFolderRequest(parentId: String, name: String, description: String? = null, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        requestProperties.addProperty("folder_id", parentId)
        requestProperties.addProperty("name", name)
        if (description != null) requestProperties.addProperty("description", description)
        return listOf(
                addSetFocusRequest("files", login),
                addRequest("add_folder", requestProperties)
        )
    }

    fun addUpdateFolderRequest(folderId: String, description: String? = null, name: String? = null, readable: Boolean? = null, writable: Boolean? = null, selfUploadNotification: Boolean? = null, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        requestProperties.addProperty("id", folderId)
        if (description != null) requestProperties.addProperty("description", description)
        if (name != null) requestProperties.addProperty("name", name)
        if (readable != null) requestProperties.addProperty("readable", asApiBoolean(readable))
        if (writable != null) requestProperties.addProperty("writable", asApiBoolean(writable))
        if (selfUploadNotification != null) requestProperties.addProperty("upload_notification_me", asApiBoolean(selfUploadNotification))
        return listOf(
                addSetFocusRequest("files", login),
                addRequest("set_folder", requestProperties)
        )
    }

    fun addDeleteFolderRequest(folderId: String, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        requestProperties.addProperty("id", folderId)
        return listOf(
                addSetFocusRequest("files", login),
                addRequest("delete_folder", requestProperties)
        )
    }

    fun addGetEmailStateRequest(login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("mailbox", login),
                addRequest("get_state", null)
        )
    }

    fun addSendEmailRequest(to: String, subject: String, plainBody: String, text: String?, bcc: String?, cc: String?, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("to", to)
        requestParams.addProperty("subject", subject)
        requestParams.addProperty("body_plain", plainBody)
        if (text != null)
            requestParams.addProperty("text", text)
        if (bcc != null)
            requestParams.addProperty("bcc", bcc)
        if (cc != null)
            requestParams.addProperty("cc", cc)
        return listOf(
                addSetFocusRequest("mailbox", login),
                addRequest("send_mail", requestParams)
        )
    }

    fun addAddEmailFolderRequest(name: String, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("name", name)
        return listOf(
                addSetFocusRequest("mailbox", login),
                addRequest("add_folder", requestParams)
        )
    }

    fun addSetEmailFolderRequest(folderId: String, name: String?, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("folder_id", folderId)
        if (name != null)
            requestParams.addProperty("name", name)
        return listOf(
                addSetFocusRequest("mailbox", login),
                addRequest("set_folder", requestParams)
        )
    }

    fun addDeleteEmailFolderRequest(folderId: String, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("folder_id", folderId)
        return listOf(
                addSetFocusRequest("mailbox", login),
                addRequest("delete_folder", requestParams)
        )
    }

    fun addGetEmailFoldersRequest(login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("mailbox", login),
                addRequest("get_folders", null)
        )
    }

    fun addGetEmailsRequest(folderId: String, limit: Int?, offset: Int?, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("folder_id", folderId)
        if (limit != null)
            requestParams.addProperty("limit", limit)
        if (offset != null)
            requestParams.addProperty("offset", offset)
        return listOf(
                addSetFocusRequest("mailbox", login),
                addRequest("get_messages", requestParams)
        )
    }

    fun addReadEmailRequest(folderId: String, messageId: Int, peek: Boolean?, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("folder_id", folderId)
        requestParams.addProperty("message_id", messageId)
        if (peek != null)
            requestParams.addProperty("peek", peek)
        return listOf(
                addSetFocusRequest("mailbox", login),
                addRequest("read_message", requestParams)
        )
    }

    fun addEditEmailRequest(folderId: String, messageId: Int, isFlagged: Boolean?, isUnread: Boolean?, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("folder_id", folderId)
        requestParams.addProperty("message_id", messageId)
        if (isFlagged != null)
            requestParams.addProperty("is_flagged", isFlagged)
        if (isUnread != null)
            requestParams.addProperty("is_unread", isUnread)
        return listOf(
                addSetFocusRequest("mailbox", login),
                addRequest("set_message", requestParams)
        )
    }

    fun addMoveEmailRequest(folderId: String, messageId: Int, destinationFolderId: String, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("folder_id", folderId)
        requestParams.addProperty("message_id", messageId)
        requestParams.addProperty("target_folder_id", destinationFolderId)
        return listOf(
                addSetFocusRequest("mailbox", login),
                addRequest("move_message", requestParams)
        )
    }

    fun addDeleteEmailRequest(folderId: String, messageId: Int, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("folder_id", folderId)
        requestParams.addProperty("message_id", messageId)
        return listOf(
                addSetFocusRequest("mailbox", login),
                addRequest("delete_message", requestParams)
        )
    }

    fun addGetEmailSignatureRequest(login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("mailbox", login),
                addRequest("get_signature", null)
        )
    }

    fun addSetEmailSignatureRequest(text: String, positionAnswer: String?, positionForward: String?, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestParams = JsonObject()
        requestParams.addProperty("text", text)
        if (positionAnswer != null)
            requestParams.addProperty("position_answer", positionAnswer)
        if (positionForward != null)
            requestParams.addProperty("position_forward", positionForward)
        return listOf(
                addSetFocusRequest("mailbox", login),
                addRequest("set_signature", requestParams)
        )
    }

    fun addGetTasksRequest(login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("tasks", login),
                addRequest("get_entries", null)
        )
    }

    fun addGetNotificationsRequest(login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("board", login),
                addRequest("get_entries", null)
        )
    }

    fun addGetContactsRequest(login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("addresses", login),
                addRequest("get_entries", null)
        )
    }

    fun addAddContactRequest(categories: String? = null, firstName: String? = null, lastName: String? = null, homeStreet: String? = null, homeStreet2: String? = null, homePostalCode: String? = null, homeCity: String? = null, homeState: String? = null, homeCountry: String? = null, homeCoords: String? = null, homePhone: String? = null, homeFax: String? = null, mobilePhone: String? = null, birthday: String? = null, email: String? = null, gender: Gender? = null, hobby: String? = null, notes: String? = null, website: String? = null, company: String? = null, companyType: String? = null, jobTitle: String? = null, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        if (categories != null) requestProperties.addProperty("categories", categories)
        if (firstName != null) requestProperties.addProperty("firstname", firstName)
        if (lastName != null) requestProperties.addProperty("lastname", lastName)
        if (homeStreet != null) requestProperties.addProperty("homestreet", homeStreet)
        if (homeStreet2 != null) requestProperties.addProperty("homestreet2", homeStreet2)
        if (homePostalCode != null) requestProperties.addProperty("homepostalcode", homePostalCode)
        if (homeCity != null) requestProperties.addProperty("homecity", homeCity)
        if (homeState != null) requestProperties.addProperty("homestate", homeState)
        if (homeCountry != null) requestProperties.addProperty("homecountry", homeCountry)
        if (homeCoords != null) requestProperties.addProperty("homecoords", homeCoords)
        if (homePhone != null) requestProperties.addProperty("homephone", homePhone)
        if (homeFax != null) requestProperties.addProperty("homefax", homeFax)
        if (mobilePhone != null) requestProperties.addProperty("mobilephone", mobilePhone)
        if (birthday != null) requestProperties.addProperty("birthday", birthday)
        if (email != null) requestProperties.addProperty("emailaddress", email)
        if (gender != null) requestProperties.addProperty("gender", gender.id)
        if (hobby != null) requestProperties.addProperty("hobby", hobby)
        if (notes != null) requestProperties.addProperty("notes", notes)
        if (website != null) requestProperties.addProperty("webpage", website)
        if (company != null) requestProperties.addProperty("company", company)
        if (companyType != null) requestProperties.addProperty("companytype", companyType)
        if (jobTitle != null) requestProperties.addProperty("jobtitle", jobTitle)

        return listOf(
                addSetFocusRequest("addresses", login),
                addRequest("add_entry", requestProperties)
        )
    }

    //TODO not sure allowing to change the uid
    //TODO seems like there are more properties?
    fun addSetContactRequest(id: String, categories: String? = null, firstName: String? = null, lastName: String? = null, homeStreet: String? = null, homeStreet2: String? = null, homePostalCode: String? = null, homeCity: String? = null, homeState: String? = null, homeCountry: String? = null, homeCoords: String? = null, homePhone: String? = null, homeFax: String? = null, mobilePhone: String? = null, birthday: String? = null, email: String? = null, gender: Gender? = null, hobby: String? = null, notes: String? = null, website: String? = null, company: String? = null, companyType: String? = null, jobTitle: String? = null, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        requestProperties.addProperty("id", id)
        if (categories != null) requestProperties.addProperty("categories", categories)
        if (firstName != null) requestProperties.addProperty("firstname", firstName)
        if (lastName != null) requestProperties.addProperty("lastname", lastName)
        if (homeStreet != null) requestProperties.addProperty("homestreet", homeStreet)
        if (homeStreet2 != null) requestProperties.addProperty("homestreet2", homeStreet2)
        if (homePostalCode != null) requestProperties.addProperty("homepostalcode", homePostalCode)
        if (homeCity != null) requestProperties.addProperty("homecity", homeCity)
        if (homeState != null) requestProperties.addProperty("homestate", homeState)
        if (homeCountry != null) requestProperties.addProperty("homecountry", homeCountry)
        if (homeCoords != null) requestProperties.addProperty("homecoords", homeCoords)
        if (homePhone != null) requestProperties.addProperty("homephone", homePhone)
        if (homeFax != null) requestProperties.addProperty("homefax", homeFax)
        if (mobilePhone != null) requestProperties.addProperty("mobilephone", mobilePhone)
        if (birthday != null) requestProperties.addProperty("birthday", birthday)
        if (email != null) requestProperties.addProperty("emailaddress", email)
        if (gender != null) requestProperties.addProperty("gender", gender.id)
        if (hobby != null) requestProperties.addProperty("hobby", hobby)
        if (notes != null) requestProperties.addProperty("notes", notes)
        if (website != null) requestProperties.addProperty("webpage", website)
        if (company != null) requestProperties.addProperty("company", company)
        if (companyType != null) requestProperties.addProperty("companytype", companyType)
        if (jobTitle != null) requestProperties.addProperty("jobtitle", jobTitle)

        return listOf(
                addSetFocusRequest("addresses", login),
                addRequest("set_entry", requestProperties)
        )
    }

    fun addDeleteContactRequest(id: String, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        requestProperties.addProperty("id", id)
        return listOf(
                addSetFocusRequest("addresses", login),
                addRequest("delete_entry", requestProperties)
        )
    }

    fun addGetAppointmentsRequest(login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        return listOf(
                addSetFocusRequest("calendar", login),
                addRequest("get_entries", null)
        )
    }

    fun addAddAppointmentRequest(title: String, description: String? = null, endDate: Date? = null, endDateIso: String? = null, location: String? = null, rrule: String? = null, startDate: Date? = null, startDateIso: String? = null, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        requestProperties.addProperty("title", title)
        if (description != null) requestProperties.addProperty("description", description)
        if (endDate != null) requestProperties.addProperty("end_date", endDate.time / 1000)
        if (endDateIso != null) requestProperties.addProperty("end_date_iso", endDateIso)
        if (location != null) requestProperties.addProperty("location", location)
        if (rrule != null) requestProperties.addProperty("rrule", rrule)
        if (startDate != null) requestProperties.addProperty("start_date", startDate.time / 1000)
        if (startDateIso != null) requestProperties.addProperty("start_date_iso", startDateIso)
        return listOf(
                addSetFocusRequest("calendar", login),
                addRequest("add_entry", requestProperties)
        )
    }

    fun addSetAppointmentRequest(id: String, title: String? = null, description: String? = null, endDate: Date? = null, endDateIso: String? = null, location: String? = null, rrule: String? = null, startDate: Date? = null, startDateIso: String? = null, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        requestProperties.addProperty("id", id)
        if (title != null) requestProperties.addProperty("title", title)
        if (description != null) requestProperties.addProperty("description", description)
        if (endDate != null) requestProperties.addProperty("end_date", endDate.time / 1000)
        if (endDateIso != null) requestProperties.addProperty("end_date_iso", endDateIso)
        if (location != null) requestProperties.addProperty("location", location)
        if (rrule != null) requestProperties.addProperty("rrule", rrule)
        if (startDate != null) requestProperties.addProperty("start_date", startDate.time / 1000)
        if (startDateIso != null) requestProperties.addProperty("start_date_iso", startDateIso)
        return listOf(
                addSetFocusRequest("calendar", login),
                addRequest("set_entry", requestProperties)
        )
    }

    fun addDeleteAppointmentRequest(id: String, login: String = operator.getLogin()): List<Int> {
        ensureCapacity(2)
        val requestProperties = JsonObject()
        requestProperties.addProperty("id", id)
        return listOf(
                addSetFocusRequest("calendar", login),
                addRequest("delete_entry", requestProperties)
        )
    }

    fun fireRequest(): ApiResponse {
        return fireRequest(operator.getContext())
    }

}