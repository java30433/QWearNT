package com.tencent.mobileqq.tts.impl

import bakuen.plugin.apkhook.annotation.Replace

class TtsControllerImpl {
    @Replace
    fun speak(str: String?, str2: String?, i: Int, num: Int?, str3: String?) {}
}