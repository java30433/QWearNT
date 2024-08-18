package com.tencent.watch.qzone_impl.frame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bakuen.plugin.apkhook.annotation.Copy
import bakuen.plugin.apkhook.annotation.Replace


class QzonePublishEntryFragment {
    @Replace
    @Copy
    fun S(
        a0: LayoutInflater,
        a1: ViewGroup?,
        a2: Bundle?
    ): View {
        val result = QzonePublishEntryFragmentCOPY.S(a0, a1, a2)
        return result
    }
}