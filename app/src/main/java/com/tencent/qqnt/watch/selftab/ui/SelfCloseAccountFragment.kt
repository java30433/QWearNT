package com.tencent.qqnt.watch.selftab.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import bakuen.plugin.apkhook.annotation.Copy
import bakuen.plugin.apkhook.annotation.Replace
import bakuen.qwear.VERSION_CODE
import bakuen.qwear.VERSION_NAME


class SelfCloseAccountFragment {
    @SuppressLint("ResourceType")
    @Replace
    @Copy
    fun S(
        a1: LayoutInflater?,
        a2: ViewGroup?,
        a3: Bundle?
    ): View = SelfCloseAccountFragmentCOPY.S(a1, a2, a3).apply {
        findViewById<TextView>(0x7e090527)?.text =
            buildString {
                append("关于 $VERSION_NAME($VERSION_CODE)\n")
                append("BY java30433\n")
                append("and 爅峫 提供深色主题修改\n")
                append("交流及反馈Q群：691530437\n")
                append("\n")
                append("QWearNT为QQNT手表版的修改版，仅供学习交流使用，免费提供，请勿用于任何商业用途。开发者对使用本软件造成的任何后果不负任何责任。开发者与深圳市腾讯计算机系统有限公司及QQ软件无任何关系。")
            }
    }
}