package bakuen.qwear

import com.tencent.qqnt.kernel.nativeinterface.MsgRecord
import com.tencent.qqnt.msg.api.impl.MsgServiceImpl
import com.tencent.qqnt.msg.api.impl.MsgUtilApiImpl

object MsgOperation {
    val msgService = MsgServiceImpl()
}

val MsgRecord.summary: String get() = MsgUtilApiImpl.instance.getElementSummary(this)