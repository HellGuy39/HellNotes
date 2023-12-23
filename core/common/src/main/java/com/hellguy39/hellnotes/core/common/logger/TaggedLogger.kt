package com.hellguy39.hellnotes.core.common.logger

import android.util.Log
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun taggedLogger(tag: String): TaggedLoggerDelegate = TaggedLoggerDelegate(tag)

class TaggedLoggerDelegate(tag: String) : ReadOnlyProperty<Any?, Logger> {
    private val logger by lazy { TaggedLogger(tag) }

    override fun getValue(thisRef: Any?, property: KProperty<*>): Logger = logger
}

class TaggedLogger(
    private val tag: String,
) : Logger {
    override fun i(msg: String) {
        Log.i(tag, msg)
    }

    override fun e(msg: String) {
        Log.e(tag, msg)
    }

    override fun v(msg: String) {
        Log.v(tag, msg)
    }

    override fun d(msg: String) {
        Log.d(tag, msg)
    }

    override fun w(msg: String) {
        Log.w(tag, msg)
    }

    override fun i(block: () -> String) {
        Log.i(tag, block())
    }

    override fun e(block: () -> String) {
        Log.e(tag, block())
    }

    override fun v(block: () -> String) {
        Log.v(tag, block())
    }

    override fun d(block: () -> String) {
        Log.d(tag, block())
    }

    override fun w(block: () -> String) {
        Log.w(tag, block())
    }
}
