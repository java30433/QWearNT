package bakuen.qwear

import android.view.View
import android.widget.Switch
import com.tencent.qqnt.watch.ui.componet.AbsItem
import com.tencent.watch.aio_impl.ui.menu.`MenuItemFactory$ItemEnum`


class CopyMsgMenuItem(val func: Function1<`MenuItemFactory$ItemEnum`, Unit>) : AbsItem("em_text_copy") {
    override fun getIconResId(): Int = 0x7e0805a6

    override fun getText(): Int = 0x7e1209d6

    override fun needSwitch(): Boolean = false

    override fun updateSwitchStatus(r1: Switch) {}

    fun onClick(view: View?) {
        func.invoke(`MenuItemFactory$ItemEnum`.f)
    }
}