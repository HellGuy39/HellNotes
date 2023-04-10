package com.hellguy39.hellnotes.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController

@Composable
fun <T> NavController.GetResultOnce(key: String, onResult: (T) -> Unit) {
    val valueScreenResult =
        currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<T>(key)
        ?.observeAsState()

    valueScreenResult?.value?.let {
        onResult(it)

        currentBackStackEntry
            ?.savedStateHandle
            ?.remove<T>(key)
    }
}

fun <T> NavController.SetResult(key: String, value: T) {
    previousBackStackEntry
        ?.savedStateHandle
        ?.set(key, value)
}

object ResultKey {
    const val LockRequest = "lock_request"
}