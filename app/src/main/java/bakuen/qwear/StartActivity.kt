package bakuen.qwear

import android.os.Bundle
import android.os.Debug
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.tencent.qqnt.watch.app.JumpActivity
import com.tencent.qqnt.watch.selftab.item.ItemConfig
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class StartActivity : JumpActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ItemConfig.c.filter { (_, clazz) ->
            !listOf(
                "BindParentItem",
                "FeedbackItem",
                "PrivacyLicenseItem",
                "UpdateItem",
                "PersonalInfoProtectItem",
                "PersonalInfoSharedItem"
            ).contains(clazz.simpleName)
        }.let {
            ItemConfig.c.clear()
            ItemConfig.c.putAll(it)
        }
        Thread {
            try {
                val connection = URL("https://pastebin.com/raw/y0KfLGQ9")
                    .openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val reader =
                        BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuilder()
                    var line: String?
                    while ((reader.readLine().also { line = it }) != null) {
                        response.append(line)
                    }
                    reader.close()
                    val info = response.toString().split("@")
                    val newVersion = info[0].toInt()
                    if (newVersion > VERSION_CODE) {
                        Handler(Looper.getMainLooper()).post {
                            Toast.makeText(
                                this,
                                info[1].replace("\\n", "\n"),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
                connection.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}