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
package com.hellguy39.hellnotes.feature.settings.screen.locksetup

import androidx.compose.runtime.Composable
import com.hellguy39.hellnotes.core.model.LockScreenType

@Composable
internal fun LockSetupScreen(
    uiState: LockSetupUiState,
    onNavigationBack: () -> Unit,
    onCodeReceived: (String) -> Unit,
) {
    when (uiState.newLockScreenType) {
        LockScreenType.None -> Unit
        LockScreenType.Pin -> {
            LockSetupPinScreen(
                onNavigationBack = onNavigationBack,
                onPinEntered = onCodeReceived,
            )
        }
        LockScreenType.Password -> {
            LockSetupPasswordScreen(
                onNavigationBack = onNavigationBack,
                onPasswordEntered = onCodeReceived,
            )
        }
        else -> Unit
    }
}
