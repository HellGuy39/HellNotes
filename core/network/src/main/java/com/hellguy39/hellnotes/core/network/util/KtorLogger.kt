package com.hellguy39.hellnotes.core.network.util

import android.util.Log
import io.ktor.client.plugins.logging.Logger

class KtorLogger : Logger {

    override fun log(message: String) {
        Log.i("Ktor client", message)
    }
}