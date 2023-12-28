package com.hellguy39.hellnotes.core.ui.navigations

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.hellguy39.hellnotes.core.common.arguments.Arguments

inline fun <reified T> Arguments<T>.asNavigationArgument(): NamedNavArgument {
    return navArgument(name = key) {
        type = navType()
        defaultValue = emptyValue
    }
}

inline fun <reified T> Arguments<T>.navType(): NavType<*> {
    return when (T::class) {
        String::class -> {
            NavType.StringType
        }
        Int::class -> {
            NavType.IntType
        }
        Long::class -> {
            NavType.LongType
        }
        Float::class -> {
            NavType.FloatType
        }
        else -> throw RuntimeException("Type could not be recognized")
    }
}
