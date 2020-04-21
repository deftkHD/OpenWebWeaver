package de.deftk.lonet.api.model.feature

import com.google.gson.JsonObject
import de.deftk.lonet.api.LoNet
import de.deftk.lonet.api.model.Member
import de.deftk.lonet.api.request.AuthRequest
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Serializable
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection

class Task(jsonObject: JsonObject, val group: Member): Serializable {

    // no need to worry, session id is already expired ^^
    // curl 'https://www.lo-net2.de/wws/130837.php?sid=49257099026314249558508520868961073741828331S52b413cc' -H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:74.0) Gecko/20100101 Firefox/74.0' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8' -H 'Accept-Language: de,en-US;q=0.7,en;q=0.3' --compressed -H 'Referer: https://www.lo-net2.de/wws/130837.php?tid=235266&sid=49257099026314249558508520858201073741828331Sd37c352f&enableautogrow=1' -H 'Content-Type: multipart/form-data; boundary=---------------------------18234854469591812993786830883' -H 'Origin: https://www.lo-net2.de' -H 'DNT: 1' -H 'Connection: keep-alive' -H 'Cookie: wwspc=1; wwsc=999701104119441160' -H 'Upgrade-Insecure-Requests: 1' -H 'Pragma: no-cache' -H 'Cache-Control: no-cache' --data-binary $'-----------------------------18234854469591812993786830883\r\nContent-Disposition: form-data; name="tid"\r\n\r\n235266\r\n-----------------------------18234854469591812993786830883\r\nContent-Disposition: form-data; name="complete"\r\n\r\n0\r\n-----------------------------18234854469591812993786830883\r\nContent-Disposition: form-data; name="set"\r\n\r\nAls unerledigt markieren\r\n-----------------------------18234854469591812993786830883--\r\n'

    val id: String = jsonObject.get("id").asString
    val title: String? = jsonObject.get("title")?.asString
    val description: String? = jsonObject.get("description")?.asString
    val startDate: Date? = if (jsonObject.get("start_date").asInt != 0) Date(jsonObject.get("start_date").asLong * 1000) else null
    val endDate: Date? = if (jsonObject.get("due_date").asInt != 0) Date(jsonObject.get("due_date").asLong * 1000) else null
    var completed: Boolean = jsonObject.get("completed").asInt == 1
        private set
    val creationDate: Date
    val creationMember: Member
    val modificationDate: Date
    val modificationMember: Member

    init {
        val createdObject = jsonObject.get("created").asJsonObject
        creationDate = Date(createdObject.get("date").asLong * 1000)
        creationMember = Member(createdObject.get("user").asJsonObject, null)
        val modifiedObject = jsonObject.get("modified").asJsonObject
        modificationDate = Date(modifiedObject.get("date").asLong * 1000)
        modificationMember = Member(modifiedObject.get("user").asJsonObject, null)
    }

    fun setCompleted(state: Boolean, sessionId: String) {
        TODO("doesn't work yet")
        if (state != completed) {
            //TODO doesn't work
            val request = AuthRequest("https://lo-net2.de/wws/130837.php?sid=$sessionId")
            val url = URL(request.serverUrl)
            val connection = url.openConnection() as HttpsURLConnection
            connection.connectTimeout = 15000
            connection.requestMethod = "POST"
            //connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            connection.addRequestProperty("Content-Type", "multipart/form-data")
            connection.doOutput = true
            connection.doInput = true

            val content = LoNet.serializeContent(
                mapOf(
                    Pair("tid", id),
                    Pair("complete", (if (state) 1 else 0).toString()),
                    Pair("set", "Als ${if (state) "" else "un"}gelesen makieren")
                )
            )
            connection.outputStream.write(content)
            connection.outputStream.flush()
            connection.outputStream.close()

            val reader = BufferedReader(InputStreamReader(connection.inputStream, "UTF-8"))
            val sb = StringBuilder()
            while (true) {
                val ln = reader.readLine() ?: break
                sb.append("$ln\n")
            }
            val response = sb.toString()
            error("invalid response")
        }
    }

    override fun toString(): String {
        return title ?: id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}