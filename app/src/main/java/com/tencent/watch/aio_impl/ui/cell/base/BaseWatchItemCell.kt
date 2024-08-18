package com.tencent.watch.aio_impl.ui.cell.base

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import bakuen.plugin.apkhook.annotation.Copy
import bakuen.plugin.apkhook.annotation.Replace
import bakuen.qwear.Colors
import bakuen.qwear.ElementType
import bakuen.qwear.Ids
import bakuen.qwear.MsgOperation
import bakuen.qwear.MySettings
import bakuen.qwear.appContext
import bakuen.qwear.asGroup
import bakuen.qwear.asGroupOrNull
import bakuen.qwear.debugger
import bakuen.qwear.dp
import bakuen.qwear.forEach
import bakuen.qwear.forEachAll
import bakuen.qwear.maskLinear
import bakuen.qwear.summary
import com.tencent.mobileqq.utils.TimeFormatterUtils
import com.tencent.qqnt.kernel.nativeinterface.IMsgOperateCallback
import com.tencent.qqnt.kernel.nativeinterface.MsgElement
import com.tencent.qqnt.kernel.nativeinterface.MsgRecord
import com.tencent.qqnt.msg.api.impl.MsgServiceImpl
import com.tencent.watch.aio_impl.data.WatchAIOMsgItem
import com.tencent.watch.aio_impl.ui.widget.AIOCellGroupWidget
import java.lang.ref.WeakReference
import java.util.ArrayList

open class BaseWatchItemCell {

    fun f(): WatchAIOMsgItem {
        throw RuntimeException()
    }

    private var replyView: WeakReference<LinearLayout>? = null

    @SuppressLint("ResourceType", "SetTextI18n")
    @Replace
    @Copy
    fun i(
        view: View?,
        msgItem: WatchAIOMsgItem,
        i: Int,
        list: List<Any>?,
        v1: Lifecycle?,
        v2: LifecycleOwner?
    ) {
        BaseWatchItemCellCOPY.i(view, msgItem, i, list, v1, v2)
        if (view == null) return
        val content = (view as? AIOCellGroupWidget)?.getContentWidget() ?: return
        msgItem.d.elements.firstOrNull { it.replyElement != null }?.replyElement.let { replyElement ->
            if (replyView == null) replyView = WeakReference(null)
            val rv = replyView!!
            if (replyElement == null) {
                rv.get()?.visibility = View.GONE
                return@let
            }
            if (rv.get() == null) {
                val wrap = content.maskLinear()
                val context = wrap.context
                wrap.addView(LinearLayout(context).apply {
                    layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, 0f).apply {
                        leftMargin = dp(2)
                        rightMargin = dp(2)
                    }
                    orientation = LinearLayout.VERTICAL
                    setBackgroundColor(Colors.replyBackground)
                    setPadding(dp(2), 0, dp(2), 0)
                    addView(TextView(context).apply {
                        id = Ids.MEMBER1
                        textSize = 12f + MySettings.fontSizeOffset.get()
                        setTextColor(Colors.replyText)
                    })
                }.also { replyView = WeakReference(it) }, 0)
            }
            rv.get()?.visibility = View.VISIBLE
            val timeText =
                TimeFormatterUtils.a(appContext, 3, replyElement.replyMsgTime * 1000, true, true)
            val nameTv = view.findViewById<TextView>(Ids.MEMBER1)
            nameTv.text = "${
                replyElement.mySenderName ?: replyElement.senderUid.toString().also {
                    MsgOperation.msgService.getSingleMsg(
                        this.f().l(),
                        replyElement.replayMsgSeq,
                        object : IMsgOperateCallback {
                            override fun onResult(
                                i: Int,
                                str: String?,
                                msgRecords: ArrayList<MsgRecord>?
                            ) {
                                msgRecords?.getOrNull(0)?.let {
                                    replyElement.mySenderName =
                                        it.sendMemberName.ifEmpty { it.sendNickName }
                                    nameTv.post {
                                        nameTv.text = "${replyElement.mySenderName} $timeText\n${it.summary}"
                                    }
                                }
                            }
                        })
                }
            } $timeText\n${replyElement.sourceMsgTextElems.joinToString("") { it.textElemContent ?: " " }}"
            nameTv.textSize = 13f + MySettings.fontSizeOffset.get()
            nameTv.invalidate()
        }
        content.parent.asGroup().forEachAll { child ->
            if (child is TextView && child.currentTextColor == 0xFF_FFFFFF.toInt()) {
                child.textSize = 15f + MySettings.fontSizeOffset.get()
            }
        }
        view.invalidate()
    }
}
