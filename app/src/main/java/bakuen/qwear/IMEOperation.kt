package bakuen.qwear

object IMEOperation {
    lateinit var openIME: ()->Unit
    var hideLongMenu: (()->Unit)? = null
    var extra: ExtraMsgArg? = null
    fun openIMEWithExtra(extra: ExtraMsgArg) {
        this.extra = extra
        openIME.invoke()
    }
}