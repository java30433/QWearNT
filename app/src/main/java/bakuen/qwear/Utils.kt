package bakuen.qwear

import android.annotation.SuppressLint
import android.content.res.TypedArray
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewParent
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.LinearLayout
import java.io.File

fun debugger() {}

fun File.child(dir: String) = File(this, dir)
fun View.asGroup() = this as ViewGroup
fun View.asGroupOrNull() = this as? ViewGroup

fun ViewGroup.forEachAll(block: (View) -> Unit) {
    forEach { child ->
        child.asGroupOrNull()?.forEachAll(block) ?: block(child)
    }
}

fun ViewParent.asGroup() = this as ViewGroup
inline fun ViewGroup.forEach(block: (Int, View) -> Unit) {
    for (index in 0 until childCount) {
        block(index, getChildAt(index))
    }
}
fun View.isRealVisible(): Boolean = Rect().let {
    getLocalVisibleRect(it)
    return (it.top<0 || it.bottom > this.height)
}
inline fun ViewGroup.forEach(block: (View) -> Unit) = forEach { _, view -> block(view) }

inline fun View.onClick(crossinline block: (View) -> Unit) {
    setOnClickListener(object : OnClickListener {
        override fun onClick(p0: View) {
            block(p0)
        }
    })
}
inline fun ViewTreeObserver.onGlobalLayout(crossinline block: () -> Unit) {
    addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            block()
        }
    })
}

fun View.maskLinear(): LinearLayout {
    val wrap = LinearLayout(this.context).apply {
        this@maskLinear.layoutParams?.let { layoutParams = it }
        orientation = LinearLayout.VERTICAL
    }
    this.parent.asGroup().let {
        it.removeView(this)
        it.addView(wrap)
    }
    this.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, 0, 1f)
    wrap.addView(this)
    return wrap
}

@SuppressLint("WrongConstant")
fun View.dp(v: Int) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, v.toFloat(), context.resources.displayMetrics).toInt()
