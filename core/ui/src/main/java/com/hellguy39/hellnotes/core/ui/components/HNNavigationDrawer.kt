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
package com.hellguy39.hellnotes.core.ui.components

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hellguy39.hellnotes.core.ui.rememberWindowInfo

@Composable
fun HNNavigationDrawer(
    drawerState: DrawerState,
    drawerSheet: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val windowInfo = rememberWindowInfo()

    HNModalNavigationDrawer(
        drawerState = drawerState,
        drawerSheet = drawerSheet,
        content = content,
    )

//    when(windowInfo.screenWidthInfo) {
//        is WindowInfo.WindowType.Compact -> {
//            HNModalNavigationDrawer(
//                drawerState = drawerState,
//                drawerSheet = drawerSheet,
//                content = content
//            )
//        }
//        else -> {
//            HNPermanentNavigationDrawer(
//                drawerSheet = drawerSheet,
//                content = content
//            )
//        }
//    }
}

@Composable
private fun HNPermanentNavigationDrawer(
    drawerSheet: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    PermanentNavigationDrawer(
        modifier = Modifier,
        drawerContent = drawerSheet,
        content = content,
    )
}

@Composable
private fun HNModalNavigationDrawer(
    drawerState: DrawerState,
    drawerSheet: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = drawerSheet,
        content = content,
    )
}
