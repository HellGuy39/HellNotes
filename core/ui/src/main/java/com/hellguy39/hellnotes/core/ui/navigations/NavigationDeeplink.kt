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
