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

@Composable
fun FocusRequester.requestFocusWhenBeAvailable() {
    var isFocusRequested by rememberSaveable { mutableStateOf(false) }

    if (isFocusRequested) return

    val windowInfo = LocalWindowInfo.current

    LaunchedEffect(key1 = windowInfo) {
        if (!isFocusRequested) {
            snapshotFlow {
                windowInfo.isWindowFocused
            }.collect { isWindowFocused ->
                if (isWindowFocused) {
                    requestFocus()
                    isFocusRequested = true
                }
            }
        }
    }
}
