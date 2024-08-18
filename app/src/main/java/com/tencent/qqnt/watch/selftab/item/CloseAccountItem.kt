package com.tencent.qqnt.watch.selftab.item

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import bakuen.plugin.apkhook.annotation.Copy
import bakuen.plugin.apkhook.annotation.Replace
import bakuen.qwear.VERSION_CODE
import bakuen.qwear.VERSION_NAME
import kotlin.jvm.internal.Intrinsics


class CloseAccountItem(fragment: Fragment) : SelfOperationItem(fragment) {
    @Replace
    override fun a(): Int {
        return 0x7e08055c
    }

    @Replace
    override fun b(): Int {
        return 0x7e1200ed
    }
}