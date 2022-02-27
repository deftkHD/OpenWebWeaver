package de.deftk.openww.api.model

import de.deftk.openww.api.model.feature.ServiceType
import de.deftk.openww.api.model.feature.board.IBoardNotification
import de.deftk.openww.api.model.feature.filestorage.session.ISessionFile
import de.deftk.openww.api.model.feature.mailbox.IMailbox
import de.deftk.openww.api.model.feature.messenger.IMessenger
import de.deftk.openww.api.model.feature.notes.INotebook
import de.deftk.openww.api.model.feature.profile.IUserProfile
import de.deftk.openww.api.model.feature.systemnotification.ISystemNotification
import de.deftk.openww.api.model.feature.tasks.ITask
import kotlinx.serialization.json.JsonElement

interface IUser: IOperatingScope, IMailbox, IMessenger, INotebook {

    val baseUser: RemoteScope
    val fullName: String
    val gtac: GTAC

    suspend fun getProfile(exportImage: Boolean? = false, context: IRequestContext): IUserProfile
    suspend fun exportProfileImage(context: IRequestContext): ISessionFile
    suspend fun deleteProfileImage(context: IRequestContext)
    suspend fun importProfileImage(sessionFile: ISessionFile, context: IRequestContext)

    suspend fun getSystemNotifications(context: IRequestContext): List<ISystemNotification>
    suspend fun addSessionFile(name: String, data: ByteArray, context: IRequestContext): ISessionFile

    fun getGroups(): List<IGroup>
    fun passwordMustChange(): Boolean

    suspend fun getAutoLoginUrl(disableLogout: Boolean? = null, disableReceptionOfQuickMessages: Boolean? = null, ensalveSession: Boolean? = null, locale: Locale? = null, pingMaster: Boolean? = null, sessionTimeout: Int? = null, targetData: JsonElement? = null, targetIframes: Boolean? = null, targetUrlPath: String? = null, context: IRequestContext): String
    suspend fun logout(context: IRequestContext)
    suspend fun logoutDestroyToken(token: String, context: IRequestContext)
    suspend fun checkSession(context: IRequestContext): Boolean
    suspend fun registerService(type: ServiceType, token: String, application: String? = null, generateSecret: String? = null, isOnline: Boolean? = null, managedObjects: String? = null, unmanagedPriority: Int? = null,context: IRequestContext)
    suspend fun unregisterService(type: ServiceType, token: String, context: IRequestContext)

    suspend fun getAllTasks(context: IApiContext): List<Pair<ITask, IOperatingScope>>
    suspend fun getAllBoardNotifications(context: IApiContext): List<Pair<IBoardNotification, IGroup>>
    suspend fun getAllPupilBoardNotifications(context: IApiContext): List<Pair<IBoardNotification, IGroup>>
    suspend fun getAllTeacherBoardNotifications(context: IApiContext): List<Pair<IBoardNotification, IGroup>>

}