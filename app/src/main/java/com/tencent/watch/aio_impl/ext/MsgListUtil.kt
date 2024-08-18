package com.tencent.watch.aio_impl.ext

import bakuen.plugin.apkhook.annotation.Copy
import bakuen.plugin.apkhook.annotation.Replace
import com.tencent.qqnt.kernel.nativeinterface.MsgElement
import com.tencent.qqnt.kernel.nativeinterface.MsgRecord
import com.tencent.watch.aio_impl.data.WatchAIOMsgItem

@Replace
@Copy
fun c(msgRecord: MsgRecord): WatchAIOMsgItem {
    val reply = msgRecord.elements.firstOrNull { it.replyElement != null } ?: return MsgListUtilKtCOPY.c_static(msgRecord)
    msgRecord.elements.remove(reply)
    val rawType = msgRecord.msgType
    msgRecord.msgType = 2
    val result = MsgListUtilKtCOPY.c_static(msgRecord)
    msgRecord.elements.add(0, reply)
    msgRecord.msgType = rawType
    return result
}
