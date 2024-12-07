package com.hellguy39.hellnotes.core.common.navigation

import com.hellguy39.hellnotes.core.common.arguments.Arguments

interface Screen { val endpoint: String }

fun Screen.withArgs(vararg args: String): String {
    return buildString {
        append(endpoint)
        args.forEach { arg ->
            append("/$arg")
        }
    }
}

fun Screen.withArgKeys(vararg args: String): String {
    return buildString {
        append(endpoint)
        args.forEach { arg ->
            append("/{$arg}")
        }
    }
}

fun Screen.withArgKeys(vararg args: Arguments<*>): String {
    return buildString {
        append(endpoint)
        args.forEach { arg ->
            append("/{${arg.key}}")
        }
    }
}
