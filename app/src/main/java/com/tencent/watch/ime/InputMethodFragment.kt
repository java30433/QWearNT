package com.tencent.watch.ime

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import bakuen.plugin.apkhook.annotation.Copy
import bakuen.plugin.apkhook.annotation.Replace
import bakuen.qwear.AtEleArg
import bakuen.qwear.IMEOperation
import bakuen.qwear.Ids
import bakuen.qwear.MySettings
import bakuen.qwear.ReplyEleArg
import bakuen.qwear.asGroup
import bakuen.qwear.forEach
import bakuen.qwear.onClick


abstract class InputMethodFragment {

    @JvmField
    val f: KeyboardPresenter? = null

    abstract fun D(msg: String)

    @SuppressLint("ResourceType", "SetTextI18n")
    @Replace
    @Copy
    fun S(p1: LayoutInflater?, p2: ViewGroup?, p3: Bundle?): View {
        return InputMethodFragmentCOPY.S(p1, p2, p3).asGroup().apply {
            forEach { child ->
                child.visibility = View.GONE
            }
            var extra = IMEOperation.extra
            extra?.let {
                IMEOperation.extra = null
                when (it) {
                    is ReplyEleArg -> {
                        val replyEle = findViewById<LinearLayout>(Ids.MEMBER4)
                        replyEle.visibility = View.VISIBLE
                        findViewById<TextView>(Ids.MEMBER5).text =
                            "回复 ${it.senderNickname} 的消息\n${it.sourceMsgText}"
                        findViewById<ImageView>(Ids.MEMBER6).onClick {
                            extra = null
                            replyEle.visibility = View.GONE
                        }
                    }
                    is AtEleArg -> {
                        val atTextView = findViewById<TextView>(Ids.MEMBER7)
                        atTextView.text = "@${it.atNickname}"
                        atTextView.visibility = View.VISIBLE
                    }
                }
            }
            findViewById<View>(0x7e090529).visibility = View.VISIBLE
            //val et = findViewById<EditText>(0x7e090318)
            val et = findViewById<EditText>(0x7e090474)
            //TODO
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                et.showSoftInputOnFocus = true
            }
            et.requestFocus()
            if (MySettings.singleLineInput.get()) et.isSingleLine = true
            et.imeOptions = EditorInfo.IME_ACTION_DONE
            et.setOnEditorActionListener(object : OnEditorActionListener {
                override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                        .hideSoftInputFromWindow(
                            et.windowToken,
                            InputMethodManager.HIDE_NOT_ALWAYS
                        )
                    return true
                }
            })
            val sendBtn = findViewById<View>(0x7e090528)
            val newlineBtn = findViewById<View>(0x7e090527)
            //val emotionBtn = findViewById<View>(0x7e09033f)
            et.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable) {
                    f?.s = StringBuilder(p0.toString())
                }
            })
            sendBtn.onClick {
                if (et.text.isNotEmpty()) this@InputMethodFragment.D("${extra?.toText() ?: ""}${et.text}")
                IMEOperation.hideLongMenu?.invoke()
            }
            newlineBtn.onClick {
                et.text.insert(et.selectionEnd, "\n")
            }
        }
    }
}