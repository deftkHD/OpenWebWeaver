package de.deftk.openww.api.request

import de.deftk.openww.api.WebWeaverClient
import de.deftk.openww.api.model.IRequestContext
import de.deftk.openww.api.model.feature.board.BoardNotificationColor
import de.deftk.openww.api.model.feature.courselets.TemplatePackage
import de.deftk.openww.api.model.feature.forum.ForumPostIcon
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

class GroupApiRequest(context: IRequestContext): OperatingScopeApiRequest(context) {

    fun addGetMembersRequest(miniatures: Boolean? = null, onlineOnly: Boolean? = null, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            if (miniatures != null)
                put("get_miniatures", asApiBoolean(miniatures))
            if (onlineOnly != null)
                put("only_online", asApiBoolean(onlineOnly))
        }
        ensureFocus(Focusable.MEMBERS, login)
        return addRequest("get_users", requestParams)
    }

    fun addSendQuickMessageRequest(sessionFile: String?, text: String?, login: String = context.login): Int {
        val params = buildJsonObject {
            if (sessionFile != null)
                put("import_session_file", sessionFile)
            if (text != null)
                put("text", text)
        }
        ensureFocus(Focusable.MEMBERS, login)
        return addRequest("send_quick_message", params)
    }

    fun addGetForumStateRequest(login: String = context.login): Int {
        ensureFocus(Focusable.FORUM, login)
        return addRequest("get_state", null)
    }

    fun addGetForumPostRequest(id: String? = null, login: String = context.login): Int {
        val requestProperties = buildJsonObject {
            put("id", id)
        }
        ensureFocus(Focusable.FORUM, login)
        return addRequest("get_entry", requestProperties)
    }

    fun addGetForumPostsRequest(parentId: String? = null, login: String = context.login): Int {
        val requestProperties = buildJsonObject {
            if (parentId != null)
                put("parent_id", parentId)
        }
        ensureFocus(Focusable.FORUM, login)
        return addRequest("get_entries", requestProperties)
    }

    fun addAddForumPostRequest(title: String, text: String, icon: ForumPostIcon, parentId: String = "0", importSessionFile: String? = null, importSessionFiles: Array<String>? = null, replyNotificationMe: Boolean? = null, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("title", title)
            put("text", text)
            put("icon", WebWeaverClient.json.encodeToJsonElement(icon))
            put("parent_id", parentId)
            if (importSessionFile != null) put("import_session_file", importSessionFile)
            if (importSessionFiles != null) {
                val sessionFiles = buildJsonArray {
                    importSessionFiles.forEach { add(it) }
                }
                put("import_session_files", sessionFiles)
            }
            if (replyNotificationMe != null) put("reply_notification_me", replyNotificationMe)
        }
        ensureFocus(Focusable.FORUM, login)
        return addRequest("add_entry", requestParams)
    }

    fun addDeleteForumPostRequest(id: String, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("id", id)
        }
        ensureFocus(Focusable.FORUM, login)
        return addRequest("delete_entry", requestParams)
    }

    fun addForumExportSessionFileRequest(fileId: String, id: String, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("file_id", fileId)
            put("id", id)
        }
        ensureFocus(Focusable.FORUM, login)
        return addRequest("export_session_file", requestParams)
    }

    fun addGetWikiPageRequest(name: String? = null, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            if (name != null)
                put("name", name)
        }
        ensureFocus(Focusable.WIKI, login)
        return addRequest("get_page", requestParams)
    }

    fun addGetCourseletsStateRequest(login: String = context.login): Int {
        ensureFocus(Focusable.COURSELETS, login)
        return addRequest("get_state", null)
    }

    fun addGetCourseletsConfigurationRequest(login: String = context.login): Int {
        ensureFocus(Focusable.COURSELETS, login)
        return addRequest("get_configuration", null)
    }

    fun addGetCourseletProgressRequest(ids: List<Int>? = null, since: Long? = null, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            if (ids != null) {
                val array = buildJsonArray {
                    ids.forEach { add(it) }
                }
                put("ids", array)
            }
            if (since != null)
                put("since", since / 1000)
        }
        ensureFocus(Focusable.COURSELETS, login)
        return addRequest("get_progress", requestParams)
    }

    fun addAddCourseletResultRequest(id: Int, pageId: String, score: Int? = null, time: Long? = null, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("id", id)
            put("page_id", pageId)
            if (score != null)
                put("score", score)
            if (time != null)
                put("time", time / 1000)
        }
        ensureFocus(Focusable.COURSELETS, login)
        return addRequest("add_result", requestParams)
    }

    fun addSetCourseletSuspendDataRequest(id: Int, suspendData: String, ifLatest: Int? = null, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("id", id)
            put("suspend_data", suspendData)
            put("if_latest", ifLatest) //TODO not sure whether nullable or not
        }
        ensureFocus(Focusable.COURSELETS, login)
        return addRequest("set_suspend_data", requestParams)
    }

    fun addGetCourseletResultsRequest(id: Int, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("id", id)
        }
        ensureFocus(Focusable.COURSELETS, login)
        return addRequest("get_results", requestParams)
    }

    fun addDeleteCourseletResultsRequest(id: Int, pageId: String? = null, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("id", id)
            if (pageId != null)
                put("page_id", pageId)
        }
        ensureFocus(Focusable.COURSELETS, login)
        return addRequest("delete_results", requestParams)
    }

    fun addAddCourseletBookmarkRequest(id: Int, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("id", id)
        }
        ensureFocus(Focusable.COURSELETS, login)
        return addRequest("add_bookmark", requestParams)
    }

    fun addDeleteCourseletBookmarkRequest(id: Int, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("id", id)
        }
        ensureFocus(Focusable.COURSELETS, login)
        return addRequest("delete_bookmark", requestParams)
    }

    fun addExportCourseletRuntimeRequest(login: String = context.login): Int {
        ensureFocus(Focusable.COURSELETS, login)
        return addRequest("export_runtime", null)
    }

    fun addGetTemplatesRequest(login: String = context.login): Int {
        ensureFocus(Focusable.COURSELETS, login)
        return addRequest("get_templates", null)
    }

    fun addExportTemplateRequest(id: String, pkg: TemplatePackage? = null, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("id", id)
            if (pkg != null)
                put("package", WebWeaverClient.json.encodeToString(pkg))
        }
        ensureFocus(Focusable.COURSELETS, login)
        return addRequest("export_template", requestParams)
    }

    fun addGetCourseletMappingsRequest(login: String = context.login): Int {
        ensureFocus(Focusable.COURSELETS, login)
        return addRequest("get_mappings", null)
    }

    fun addAddCourseletMappingRequest(name: String, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("name", name)
        }
        ensureFocus(Focusable.COURSELETS, login)
        return addRequest("add_mapping", requestParams)
    }

    fun addSetCourseletMappingRequest(id: Int, name: String? = null, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("id", id)
            put("name", name)
        }
        ensureFocus(Focusable.COURSELETS, login)
        return addRequest("set_mapping", requestParams)
    }

    fun addDeleteCourseletMappingRequest(id: Int, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("id", id)
        }
        ensureFocus(Focusable.COURSELETS, login)
        return addRequest("delete_mapping", requestParams)
    }

    fun addGetCourseletsRequest(login: String = context.login): Int {
        ensureFocus(Focusable.COURSELETS, login)
        return addRequest("get_courselets", null)
    }

    fun addImportCourseletRequest(id: String, isVisible: Boolean? = null, mapping: Int? = null, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("id", id)
            if (isVisible != null)
                put("is_visible", asApiBoolean(isVisible))
            if (mapping != null)
                put("mapping", mapping)
        }
        ensureFocus(Focusable.COURSELETS, login)
        return addRequest("import_courselet", requestParams)
    }

    fun addExportCourseletRequest(id: String, pkg: TemplatePackage? = null, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("id", id)
            if (pkg != null)
                put("pkg", WebWeaverClient.json.encodeToString(pkg))
        }
        ensureFocus(Focusable.COURSELETS, login)
        return addRequest("export_courselet", requestParams)
    }

    fun addDeleteCourseletRequest(id: String, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("id", id)
        }
        ensureFocus(Focusable.COURSELETS, login)
        return addRequest("delete_courselet", requestParams)
    }

    fun addAddTeacherBoardNotificationRequest(title: String, text: String, color: BoardNotificationColor? = null, killDate: Long? = null, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("title", title)
            put("text", text)
            if (color != null)
                put("color", color.serialId)
            if (killDate != null)
                put("kill_date", killDate / 1000)
        }
        ensureFocus(Focusable.BOARD_TEACHER, login)
        return addRequest("add_entry", requestParams)
    }

    fun addSetTeacherBoardNotificationRequest(id: String, title: String, text: String, color: BoardNotificationColor? = null, killDate: Long? = null, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("id", id)
            put("title", title)
            put("text", text)
            put("color", color?.serialId)
            put("kill_date", if (killDate != null) JsonPrimitive(killDate / 1000) else JsonNull)
        }
        ensureFocus(Focusable.BOARD_TEACHER, login)
        return addRequest("set_entry", requestParams)
    }

    fun addDeleteTeacherBoardNotificationRequest(id: String, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("id", id)
        }
        ensureFocus(Focusable.BOARD_TEACHER, login)
        return addRequest("delete_entry", requestParams)
    }

    fun addAddPupilBoardNotificationRequest(title: String, text: String, color: BoardNotificationColor? = null, killDate: Long? = null, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("title", title)
            put("text", text)
            if (color != null)
                put("color", color.serialId)
            if (killDate != null)
                put("kill_date", killDate / 1000)
        }
        ensureFocus(Focusable.BOARD_PUPIL, login)
        return addRequest("add_entry", requestParams)
    }

    fun addSetPupilBoardNotificationRequest(id: String, title: String, text: String, color: BoardNotificationColor? = null, killDate: Long? = null, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("id", id)
            put("title", title)
            put("text", text)
            put("color", color?.serialId)
            put("kill_date", if (killDate != null) JsonPrimitive(killDate / 1000) else JsonNull)
        }
        ensureFocus(Focusable.BOARD_PUPIL, login)
        return addRequest("set_entry", requestParams)
    }

    fun addDeletePupilBoardNotificationRequest(id: String, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("id", id)
        }
        ensureFocus(Focusable.BOARD_PUPIL, login)
        return addRequest("delete_entry", requestParams)
    }

    fun addAddBoardNotificationRequest(title: String, text: String, color: BoardNotificationColor? = null, killDate: Long? = null, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("title", title)
            put("text", text)
            if (color != null)
                put("color", color.serialId)
            if (killDate != null)
                put("kill_date", killDate / 1000)
        }
        ensureFocus(Focusable.BOARD, login)
        return addRequest("add_entry", requestParams)
    }

    // warning: killDate can't be removed once set
    fun addSetBoardNotificationRequest(id: String, title: String, text: String, color: BoardNotificationColor, killDate: Long? = null, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("id", id)
            put("title", title)
            put("text", text)
            put("color", WebWeaverClient.json.encodeToJsonElement(color))
            if (killDate != null)
            put("kill_date", killDate / 1000)
        }
        ensureFocus(Focusable.BOARD, login)
        return addRequest("set_entry", requestParams)
    }

    fun addDeleteBoardNotificationRequest(id: String, login: String = context.login): Int {
        val requestParams = buildJsonObject {
            put("id", id)
        }
        ensureFocus(Focusable.BOARD, login)
        return addRequest("delete_entry", requestParams)
    }

}