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
package com.hellguy39.hellnotes.feature.locksetup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.domain.repository.settings.DataStoreRepository
import com.hellguy39.hellnotes.core.model.LockScreenType
import com.hellguy39.hellnotes.core.model.repository.local.datastore.SecurityState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LockSetupViewModel
    @Inject
    constructor(
        private val dataStoreRepository: DataStoreRepository,
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        val uiState: StateFlow<LockSetupUiState> =
            combine(
                dataStoreRepository.readSecurityState(),
                savedStateHandle.getStateFlow(Arguments.Type.key, LockScreenType.None.tag),
            ) { securityState, newLockScreenType ->
                LockSetupUiState(
                    securityState = securityState,
                    newLockScreenType = LockScreenType.fromTag(newLockScreenType),
                )
            }
                .stateIn(
                    scope = viewModelScope,
                    initialValue = LockSetupUiState.initialInstance(),
                    started = SharingStarted.WhileSubscribed(5_000),
                )

        fun saveAppCode(code: String) {
            viewModelScope.launch {
                val securityState = uiState.value.securityState
                val newLockScreenType = uiState.value.newLockScreenType

                dataStoreRepository.saveSecurityState(
                    securityState.copy(
                        password = code,
                        lockType = newLockScreenType,
                    ),
                )
            }
        }
    }

data class LockSetupUiState(
    val securityState: SecurityState,
    val newLockScreenType: LockScreenType,
) {
    companion object {
        fun initialInstance() =
            LockSetupUiState(
                securityState = SecurityState.initialInstance(),
                newLockScreenType = LockScreenType.None,
            )
    }
}
