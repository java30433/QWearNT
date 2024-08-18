package bakuen.qwear

import android.content.Context
import android.content.SharedPreferences

object MySettings {
    val sp: SharedPreferences = appContext.getSharedPreferences("qwearnt", Context.MODE_PRIVATE)

    val singleLineInput = Setting("single_line", false)
    val fontSizeOffset = Setting("font_size_offset", 0f)

    class Setting<T>(val key: String, val def: T) {
        @Suppress("UNCHECKED_CAST")
        fun get(): T = when (def) {
            is Boolean -> sp.getBoolean(key, def) as T
            is Int -> sp.getInt(key, def) as T
            is Float -> sp.getFloat(key, def) as T
            else -> throw IllegalArgumentException()
        }
        fun set(v: T) {
            val edit = sp.edit()
            when (v) {
                is Boolean -> edit.putBoolean(key, v)
                is Int -> edit.putInt(key, v)
                is Float -> edit.putFloat(key, v)
            }
            edit.apply()
        }
    }
}