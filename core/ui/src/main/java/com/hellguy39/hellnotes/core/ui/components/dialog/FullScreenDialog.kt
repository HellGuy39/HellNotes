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
package com.hellguy39.hellnotes.core.ui.components.dialog

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.view.WindowCompat

@Composable
fun getDialogWindow(): Window? = (LocalView.current.parent as? DialogWindowProvider)?.window

@Composable
fun getActivityWindow(): Window? = LocalView.current.context.getActivityWindow()

private tailrec fun Context.getActivityWindow(): Window? =
    when (this) {
        is Activity -> window
        is ContextWrapper -> baseContext.getActivityWindow()
        else -> null
    }

@Composable
fun FullScreenDialog(
    isShowingDialog: Boolean,
    dialogProperties: DialogProperties = DialogProperties(),
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    if (!isShowingDialog) return

    Dialog(
        onDismissRequest = onDismissRequest,
        properties =
            DialogProperties(
                dismissOnBackPress = dialogProperties.dismissOnBackPress,
                dismissOnClickOutside = dialogProperties.dismissOnClickOutside,
                securePolicy = dialogProperties.securePolicy,
                usePlatformDefaultWidth = true,
                decorFitsSystemWindows = false,
            ),
        content = {
            ApplyAttributes()
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                content()
            }
        },
    )
}

@Composable
fun ApplyAttributes() {
    val activityWindow = getActivityWindow()
    val dialogWindow = getDialogWindow()
    val parentView = LocalView.current.parent as View
    SideEffect {
        if (activityWindow != null && dialogWindow != null) {
            val attributes = WindowManager.LayoutParams()
            attributes.copyFrom(activityWindow.attributes)
            attributes.type = dialogWindow.attributes.type
            dialogWindow.attributes = attributes
            parentView.layoutParams = FrameLayout.LayoutParams(activityWindow.decorView.width, activityWindow.decorView.height)

            val insetsController = WindowCompat.getInsetsController(activityWindow, parentView)
            val dialogInsetsController = WindowCompat.getInsetsController(dialogWindow, parentView)
            dialogInsetsController.isAppearanceLightStatusBars = insetsController.isAppearanceLightStatusBars
        }
    }
}
