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
package com.hellguy39.hellnotes.core.ui.navigations

import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.common.uri.DeeplinkRoute

inline fun <reified T> Arguments<T>.asNavigationDeeplink(): NavDeepLink {
    return navDeepLink {
        uriPattern =
            DeeplinkRoute.fromApp()
                .addArgument(this@asNavigationDeeplink)
                .asString()
    }
}

inline fun <reified T> List<Arguments<T>>.asNavigationDeeplink(): NavDeepLink {
    return navDeepLink {
        uriPattern =
            DeeplinkRoute.fromApp()
                .apply {
                    forEach { argument ->
                        addArgument(argument)
                    }
                }
                .asString()
    }
}
