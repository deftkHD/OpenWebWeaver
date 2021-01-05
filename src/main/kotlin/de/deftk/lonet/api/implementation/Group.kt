package de.deftk.lonet.api.implementation

import de.deftk.lonet.api.exception.ApiException
import de.deftk.lonet.api.implementation.feature.board.BoardNotification
import de.deftk.lonet.api.implementation.feature.courselets.Courselet
import de.deftk.lonet.api.implementation.feature.courselets.CourseletMapping
import de.deftk.lonet.api.implementation.feature.forum.ForumPost
import de.deftk.lonet.api.implementation.feature.wiki.WikiPage
import de.deftk.lonet.api.model.IGroup
import de.deftk.lonet.api.model.IRequestContext
import de.deftk.lonet.api.model.Permission
import de.deftk.lonet.api.model.RemoteScope
import de.deftk.lonet.api.model.feature.FileDownloadUrl
import de.deftk.lonet.api.model.feature.Quota
import de.deftk.lonet.api.model.feature.board.BoardNotificationColor
import de.deftk.lonet.api.model.feature.board.IBoardNotification
import de.deftk.lonet.api.model.feature.forum.ForumPostIcon
import de.deftk.lonet.api.model.feature.forum.ForumSettings
import de.deftk.lonet.api.model.feature.forum.IForumPost
import de.deftk.lonet.api.request.GroupApiRequest
import de.deftk.lonet.api.response.ResponseUtil
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*
import java.util.*

@Serializable
class Group(
    override val login: String,
    @SerialName("name_hr")
    override val name: String,
    override val type: Int,
    override val baseRights: List<Permission> = emptyList(),
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
                parent.getComments().add(post)
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