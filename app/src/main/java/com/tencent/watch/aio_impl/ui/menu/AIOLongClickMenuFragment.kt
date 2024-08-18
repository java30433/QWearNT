package com.tencent.watch.aio_impl.ui.menu

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import bakuen.plugin.apkhook.annotation.Copy
import bakuen.plugin.apkhook.annotation.Replace
import bakuen.qwear.AtMsgMenuItem
import bakuen.qwear.CopyMsgMenuItem
import bakuen.qwear.IMEOperation
import bakuen.qwear.ReplyMsgMenuItem
import com.tencent.qqnt.watch.ui.componet.AbsItem


class AIOLongClickMenuFragment : DialogFragment() {
    val b: ((`MenuItemFactory$ItemEnum`) -> Unit)? = null
    @JvmField
    var d: List<AbsItem> = listOf()

    var needHide = false

    @SuppressLint("MissingSuperCall")
    @Replace
    @Copy
    override fun onCreate(a0: Bundle?) {
        IMEOperation.hideLongMenu = {
            needHide = true
        }
        AIOLongClickMenuFragmentCOPY.onCreate(a0)
        this.d = this.d.toMutableList().apply {
            add(CopyMsgMenuItem(b!!))
            add(ReplyMsgMenuItem(b))
            add(AtMsgMenuItem(b))
        }
    }

    override fun onResume() {
        super.onResume()
        if (needHide) {
            this.activity?.onBackPressed()
            this.dismiss()
        }
    }
}