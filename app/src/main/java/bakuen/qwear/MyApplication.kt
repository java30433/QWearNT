package bakuen.qwear

import android.content.Context
import android.os.Debug
import com.tencent.qqnt.msg.api.impl.MsgServiceImpl
import com.tencent.qqnt.msg.api.impl.MsgUtilApiImpl
import com.tencent.qqnt.watch.app.WatchApplication
import com.tencent.watch.aio_impl.ui.menu.`MenuItemFactory$ItemEnum`

const val VERSION_CODE = 4
const val VERSION_NAME = "QWear 2.0-beta04"
lateinit var appContext: Context
    private set
class MyApplication : WatchApplication() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
        Thread {
            getExternalFilesDir("")?.child("tencent/msflogs")?.deleteRecursively()
        }.start()
        `MenuItemFactory$ItemEnum`.reply = `MenuItemFactory$ItemEnum`()
        `MenuItemFactory$ItemEnum`.at = `MenuItemFactory$ItemEnum`()
        MsgUtilApiImpl.instance = MsgUtilApiImpl()
    }
}