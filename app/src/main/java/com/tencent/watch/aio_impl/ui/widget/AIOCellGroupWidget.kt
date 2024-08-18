package com.tencent.watch.aio_impl.ui.widget

import android.content.Context
import android.view.View
import android.widget.FrameLayout


class AIOCellGroupWidget(context: Context) : FrameLayout(context) {

    fun getContentWidget(): View {
        throw RuntimeException()
    }

    /**
     * 0: 别人的信息
     */
    fun getLocationType(): Int = 0
/*
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        this.asGroup().forEach { child ->
            if (child is AIOItemTextView) {
                //聊天者名字
                if (child.currentTextColor == 0x66_000000) {
                    child.setTextColor(0xFF_999999.toInt())
                }
                //聊天内容
                if (child.textSize == 15f) {
                    child.textSize = 10f//no work
                    child.setTextColor(0xFF_EEEEEE.toInt())
                }
            }
        }
        AIOCellGroupWidgetCOPY.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }*/
}