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
package com.hellguy39.hellnotes.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.settings.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel
    @Inject
    constructor(
        private val dataStoreRepository: DataStoreRepository,
    ) : ViewModel() {
        private val _onBoardingState = MutableStateFlow(OnBoardingState())
        val onBoardingState = _onBoardingState.asStateFlow()

        init {
            checkOnBoarding()
        }

        fun finishOnBoarding() {
            viewModelScope.launch {
                _onBoardingState.update { state -> state.copy(isVisible = false) }
                dataStoreRepository.saveOnBoardingState(completed = true)
            }
        }

        private fun checkOnBoarding() {
            viewModelScope.launch {
                dataStoreRepository.readOnBoardingState().collectLatest { completed ->
                    _onBoardingState.update { state -> state.copy(isVisible = !completed) }
                }
            }
        }
    }

data class OnBoardingState(
    val isVisible: Boolean = false,
)
