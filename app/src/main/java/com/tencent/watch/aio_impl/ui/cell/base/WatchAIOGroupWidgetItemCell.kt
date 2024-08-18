package com.tencent.watch.aio_impl.ui.cell.base

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.core.view.postDelayed
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import bakuen.plugin.apkhook.annotation.Copy
import bakuen.plugin.apkhook.annotation.Replace
import bakuen.qwear.AtEleArg
import bakuen.qwear.IMEOperation
import bakuen.qwear.MsgOperation
import bakuen.qwear.ReplyEleArg
import bakuen.qwear.debugger
import bakuen.qwear.summary
import com.tencent.qqnt.msg.api.impl.MsgUtilApiImpl
import com.tencent.watch.aio_impl.ui.menu.AIOLongClickMenuFragment
import com.tencent.watch.aio_impl.ui.menu.`MenuItemFactory$ItemEnum`


open class WatchAIOGroupWidgetItemCell : BaseWatchItemCell() {
    @Replace
    @Copy
    fun l(itemEnum: `MenuItemFactory$ItemEnum`, fragment: Fragment): Boolean {
        val msg = this.f().d
        if (itemEnum == `MenuItemFactory$ItemEnum`.f) {
            debugger()
            val clipboardManager =
                fragment.context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText(
                "QQMsg",
                msg.summary
            )
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(fragment.context, "已复制到剪切板", Toast.LENGTH_SHORT).show()
            return true
        } else if (itemEnum == `MenuItemFactory$ItemEnum`.reply) {
            IMEOperation.openIMEWithExtra(
                ReplyEleArg(
                    replayMsgId = msg.msgId,
                    senderUidStr = msg.senderUid,
                    sourceMsgText = msg.summary,
                    senderNickname = msg.sendMemberName.ifEmpty { msg.sendNickName }
                )
            )
            return true
        }  else if (itemEnum == `MenuItemFactory$ItemEnum`.at) {
            IMEOperation.openIMEWithExtra(
                AtEleArg(
                    atUid = msg.senderUid,
                    atNickname = msg.sendMemberName.ifEmpty { msg.sendNickName }
                )
            )
            return true
        }
        return WatchAIOGroupWidgetItemCellCOPY.l(itemEnum, fragment)
    }
}