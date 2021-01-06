package de.deftk.lonet.api.model

import de.deftk.lonet.api.model.feature.ServiceType
import de.deftk.lonet.api.model.feature.board.IBoardNotification
import de.deftk.lonet.api.model.feature.filestorage.session.ISessionFile
import de.deftk.lonet.api.model.feature.mailbox.IMailbox
import de.deftk.lonet.api.model.feature.systemnotification.ISystemNotification
import de.deftk.lonet.api.model.feature.tasks.ITask
import kotlinx.serialization.json.JsonElement

interface IUser: IOperatingScope, IMailbox {

    fun getBaseUser(): RemoteScope
    fun getFullName(): String
    fun getGTAC(): GTAC

    fun getSystemNotifications(context: IRequestContext): List<ISystemNotification>
    fun addSessionFile(name: String, data: ByteArray, context: IRequestContext): ISessionFile

    fun getGroups(): List<IGroup>
    fun passwordMustChange(): Boolean

    fun getAutoLoginUrl(disableLogout: Boolean? = null, disableReceptionOfQuickMessages: Boolean? = null, ensalveSession: Boolean? = null, locale: Locale? = null, pingMaster: Boolean? = null, sessionTimeout: Int? = null, targetData: JsonElement? = null, targetIframes: Boolean? = null, targetUrlPath: String? = null, context: IRequestContext): String
    fun logout(context: IRequestContext)
    fun logoutDestroyToken(token: String, context: IRequestContext)
    fun checkSession(context: IRequestContext): Boolean
    fun registerService(type: ServiceType, token: String, application: String? = null, generateSecret: String? = null, isOnline: Boolean? = null, managedObjects: String? = null, unmanagedPriority: Int? = null,context: IRequestContext)
    fun unregisterService(type: ServiceType, token: String, context: IRequestContext)

    fun getAllTasks(context: IApiContext): List<Pair<ITask, IOperatingScope>>
    fun getAllBoardNotifications(context: IApiContext): List<Pair<IBoardNotification, IOperatingScope>>
    fun getAllPupilBoardNotifications(context: IApiContext): List<Pair<IBoardNotification, IOperatingScope>>
    fun getAllTeacherBoardNotifications(context: IApiContext): List<Pair<IBoardNotification, IOperatingScope>>

}