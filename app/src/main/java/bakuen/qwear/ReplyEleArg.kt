package bakuen.qwear

import java.io.File

interface ExtraMsgArg {
    fun toText(): String
}

class ReplyEleArg(
    val replayMsgId: Long,
    val senderUidStr: String,
    val sourceMsgText: String,
    val senderNickname: String = "",
    val msg: String = ""
) : ExtraMsgArg {
    override fun toText() = "$SURROUNDING$replayMsgId$SEPARATOR$senderUidStr$SEPARATOR$sourceMsgText$SURROUNDING"
    companion object {
        fun tryParse(msg: String): ReplyEleArg? {
            val splits =  msg.split(SURROUNDING)
            val args = splits.getOrNull(1)?.split(SEPARATOR) ?: return null
            if (args.size != 3) return null
            return ReplyEleArg(
                replayMsgId = args[0].toLongOrNull() ?: return null,
                senderUidStr = args[1],
                sourceMsgText = args[2],
                msg = "${splits.getOrNull(0) ?: ""}${splits.getOrNull(2) ?: ""}"
            )
        }
    }
}

private const val SURROUNDING = "ꡦREPLYꡦ"
private const val SEPARATOR = "ꡬ"