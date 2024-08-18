package com.tencent.qqnt.watch.selftab.item

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import bakuen.plugin.apkhook.annotation.Replace
import bakuen.qwear.SettingsActivity

class PersonalInfoItem(fragment: Fragment) : SelfOperationItem(fragment) {
    @Replace
    override fun a(): Int {
        return 0x7e08046f
    }

    @Replace
    override fun b(): Int {
        return 0x7e120a5b
    }

    @Replace
    override fun onClick(view: View?) {
        view?.context?.let {
            it.startActivity(Intent(it, SettingsActivity::class.java))
        }
    }
}
