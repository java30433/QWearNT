package bakuen.qwear

import android.view.View
import android.widget.Switch
import com.tencent.qqnt.watch.ui.componet.AbsItem
import com.tencent.watch.aio_impl.ui.menu.`MenuItemFactory$ItemEnum`

class ReplyMsgMenuItem(val func: Function1<`MenuItemFactory$ItemEnum`, Unit>) : AbsItem("em_text_reply") {
    override fun getIconResId(): Int = 0x7e080590

    override fun getText(): Int = 0x7e12074f

    override fun needSwitch(): Boolean = false

    override fun updateSwitchStatus(r1: Switch) {}

    fun onClick(view: View?) {
        func.invoke(`MenuItemFactory$ItemEnum`.reply)
    }
}