package bakuen.qwear

class AtEleArg(
    val atUid: String,
    val atNickname: String = "",
    val msg: String = ""
) : ExtraMsgArg {
    override fun toText() = "$SURROUNDING$atUid$SEPARATOR$atNickname$SURROUNDING"
    companion object {
        fun tryParse(msg: String): AtEleArg? {
            val splits =  msg.split(SURROUNDING)
            val args = splits.getOrNull(1)?.split(SEPARATOR) ?: return null
            if (args.size != 2) return null
            return AtEleArg(
                atUid = args[0],
                atNickname = args[1],
                msg = "${splits.getOrNull(0) ?: ""}${splits.getOrNull(2) ?: ""}"
            )
        }
    }
}

private const val SURROUNDING = "ꡦATꡦ"
private const val SEPARATOR = "ꡬ"