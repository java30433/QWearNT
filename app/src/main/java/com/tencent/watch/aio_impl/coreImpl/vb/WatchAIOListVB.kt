package com.tencent.watch.aio_impl.coreImpl.vb

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import bakuen.plugin.apkhook.annotation.Copy
import bakuen.plugin.apkhook.annotation.Replace
import bakuen.qwear.IMEOperation
import bakuen.qwear.asGroup
import bakuen.qwear.forEach
import com.tencent.aio.api.list.IListUIOperationApi
import com.tencent.mvi.api.help.CreateViewParams
import java.lang.reflect.Field


class WatchAIOListVB {
    var J: InputBarController? = null

    @Replace
    @Copy
    fun h(
        p0: CreateViewParams,
        p1: View,
        p2: IListUIOperationApi
    ): View = WatchAIOListVBCOPY.h(p0, p1, p2).also { aiols ->
        //aiols: WatchAIOListVB$onCreateView
        IMEOperation.openIME = {
            J?.o?.callOnClick()
        }
    }
}