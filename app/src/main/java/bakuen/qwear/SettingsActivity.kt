package bakuen.qwear

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioGroup.OnCheckedChangeListener
import android.widget.ScrollView
import android.widget.Switch
import android.widget.TextView

class SettingsActivity : Activity() {
    private val root by lazy { LinearLayout(this).apply {
        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        orientation = LinearLayout.VERTICAL
    } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        root.addView(Button(this).apply {
            text = "返回"
            setTextColor(0xFF_FFFFFF.toInt())
            onClick {
                this@SettingsActivity.finish()
            }
        })
        toggle(MySettings.singleLineInput, "单行输入", "如果在使用输入法时遇到问题，清尝试开启")
        input(MySettings.fontSizeOffset, "聊天字体大小调节", "0是默认大小，负数缩小，可小数")
        setContentView(ScrollView(this).apply {
            setBackgroundColor(0xff_000000.toInt())
            setPadding(dp(6), dp(6), dp(6), dp(6))
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            addView(root)
        } )
    }

    private fun input(
        setting: MySettings.Setting<Float>,
        title: String,
        desc: String? = null
    ) = entry(title, desc, EditText(this).apply {
        setText(setting.get().toString())
        setTextColor(0xFF_FFFFFF.toInt())
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable) {
                setting.set(p0.toString().toFloatOrNull() ?: 0f)
            }
        })
    })

    private fun toggle(
        setting: MySettings.Setting<Boolean>,
        title: String,
        desc: String? = null
    ) = entry(title, desc, Switch(this).apply {
        isChecked = setting.get()
        setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                setting.set(p1)
            }
        })
    })

    private fun entry(
        title: String,
        desc: String? = null,
        contentView: View
    ) {
        root.addView(LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            gravity = Gravity.CENTER
            addView(LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(0, WRAP_CONTENT, 1f)
                addView(TextView(context).apply {
                    textSize = 14f
                    text = title
                    setTextColor(Colors.onSurface)
                })
                if (desc != null) {
                    addView(TextView(context).apply {
                        textSize = 10f
                        text = desc
                        setTextColor(Colors.onSurfaceTip)
                    })
                }
            })
            addView(contentView.apply {
                layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, 0f)
            })
        })
    }
}