package com.hellguy39.hellnotes.core.ui.focus

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalWindowInfo
import kotlinx.coroutines.job

fun FocusRequester.tryRequestFocus() {
    try {
        requestFocus()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@Composable
fun FocusRequester.requestAfterRecomposition() {
    LaunchedEffect(Unit) {
        this.coroutineContext.job.invokeOnCompletion {
            tryRequestFocus()
        }
    }
}

@Composable
fun FocusRequester.requestOnceAfterRecompositionIf(condition: () -> Boolean) {
    var isFocusRequested by rememberSaveable { mutableStateOf(false) }

    if (isFocusRequested) return

    val windowInfo = LocalWindowInfo.current

    LaunchedEffect(key1 = condition(), key2 = windowInfo) {
        if (condition() && !isFocusRequested) {
            snapshotFlow {
                windowInfo.isWindowFocused
            }.collect { isWindowFocused ->
                if (isWindowFocused) {
                    isFocusRequested = true
                    this.coroutineContext.job.invokeOnCompletion {
                        tryRequestFocus()
                    }
                }
            }
        }
    }
}
