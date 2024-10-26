/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
