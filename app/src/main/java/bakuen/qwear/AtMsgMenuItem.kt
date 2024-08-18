package bakuen.qwear

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.tencent.qqnt.watch.ui.componet.AbsItem
import com.tencent.watch.aio_impl.ui.menu.`MenuItemFactory$ItemEnum`
import org.w3c.dom.Text

class AtMsgMenuItem(val func: Function1<`MenuItemFactory$ItemEnum`, Unit>) : AbsItem("em_text_at") {
    override fun getIconResId(): Int = 0x7e0807a8

    override fun getText(): Int = 0x7e12074f

    override fun needSwitch(): Boolean = false

    override fun updateSwitchStatus(r1: Switch) {}

    fun onClick(view: View?) {
        func.invoke(`MenuItemFactory$ItemEnum`.at)
    }

    @SuppressLint("ResourceType", "SetTextI18n")
    override fun createView(context: Context, viewGroup: ViewGroup): View {
        val base = super.createView(context, viewGroup)
        val icon = base.findViewById<ImageView>(2114520136)
        val label = base.findViewById<TextView>(2114519752)
        label.text = "@TA"
        return base
    }
}