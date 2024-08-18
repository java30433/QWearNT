package com.tencent.qqnt.msg.api.impl

import android.util.Log
import android.widget.Toast
import bakuen.plugin.apkhook.annotation.Copy
import bakuen.plugin.apkhook.annotation.Replace
import bakuen.qwear.AtEleArg
import bakuen.qwear.ReplyEleArg
import bakuen.qwear.appContext
import com.tencent.qqnt.kernel.nativeinterface.Contact
import com.tencent.qqnt.kernel.nativeinterface.GProMVPAtType
import com.tencent.qqnt.kernel.nativeinterface.IMsgOperateCallback
import com.tencent.qqnt.kernel.nativeinterface.IOperateCallback
import com.tencent.qqnt.kernel.nativeinterface.MsgElement


class MsgServiceImpl {
    @Replace
    @Copy
    fun sendMsg(
        a0: Contact,
        a1: Long,
        elements: ArrayList<MsgElement?>,
        a2: IOperateCallback
    ) {
        elements.getOrNull(0)?.textElement?.let { text ->
            AtEleArg.tryParse(text.content)?.let { arg ->
                text.content = " ${arg.msg}"
                elements.add(
                    0,
                    MsgUtilApiImpl.instance.createAtTextElement(
                        "@${arg.atNickname}",
                        arg.atUid,
                        GProMVPAtType.AT_TYPE_GUILD_MEMBER
                    )
                )
            }
            ReplyEleArg.tryParse(text.content)?.let { arg ->
                text.content = arg.msg
                elements.add(
                    0,
                    MsgUtilApiImpl.instance.createReplyElement(arg.replayMsgId).apply {
                        this.replyElement?.senderUid = arg.senderUidStr.toLongOrNull() ?: 0L
                    }
                )
            }
        }
        MsgServiceImplCOPY.sendMsg(a0, a1, elements, a2)
    }

    fun getSingleMsg(
        contact: Contact?,
        j: Long,
        iMsgOperateCallback: IMsgOperateCallback?
    ) {
    }
}