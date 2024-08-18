package com.tencent.qphone.base.util

import bakuen.plugin.apkhook.annotation.Replace

class QLogItemManager {
    @Replace
    fun writeLogItems(): Boolean = true
}