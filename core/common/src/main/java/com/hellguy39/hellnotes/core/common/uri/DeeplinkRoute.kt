package com.hellguy39.hellnotes.core.common.uri

import android.net.Uri
import com.hellguy39.hellnotes.core.common.BuildConfig
import com.hellguy39.hellnotes.core.common.arguments.Arguments

class DeeplinkRoute private constructor(
    private var uriString: String,
) {
    fun addPath(path: String): DeeplinkRoute {
        return apply {
            uriString = "$uriString/$path"
        }
    }

    fun <T> passArgument(argument: Arguments<T>, value: T): DeeplinkRoute {
        return apply {
            uriString = "$uriString/${argument.key}=$value"
        }
    }

    fun <T> addArgument(argument: Arguments<T>): DeeplinkRoute {
        return apply {
            uriString = "$uriString/${argument.key}={${argument.key}}"
        }
    }

    fun asUri(): Uri {
        return Uri.parse(uriString)
    }

    fun asString(): String {
        return uriString
    }

    companion object {
        fun fromApp(): DeeplinkRoute {
            return DeeplinkRoute("app://${BuildConfig.APPLICATION_ID}")
        }
    }
}
