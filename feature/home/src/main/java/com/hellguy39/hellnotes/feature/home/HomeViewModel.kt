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
package com.hellguy39.hellnotes.feature.home

import android.app.Activity
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.notes.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.store.InAppStoreManager
import com.hellguy39.hellnotes.core.domain.repository.store.ReviewState
import com.hellguy39.hellnotes.core.domain.repository.store.UpdateState
import com.hellguy39.hellnotes.core.model.repository.local.database.Label
import com.hellguy39.hellnotes.core.ui.extensions.toStateList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        labelRepository: LabelRepository,
        private val inAppStoreManager: InAppStoreManager,
    ) : ViewModel() {
        val uiState: StateFlow<HomeUiState> =
            combine(
                labelRepository.getAllLabelsStream(),
                inAppStoreManager.updateProvider.updateState,
                inAppStoreManager.reviewProvider.reviewState,
            ) { labels, updateState, reviewState ->
                HomeUiState(
                    labels = labels.toStateList(),
                    updateState = updateState,
                    isReviewAvailable = reviewState is ReviewState.Available,
                    isIdle = false,
                )
            }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = HomeUiState(),
                )

        fun onUpdateButtonClick(activity: Activity) {
            viewModelScope.launch {
                when (uiState.value.updateState) {
                    UpdateState.Available -> {
                        inAppStoreManager.updateProvider.downloadUpdate(activity)
                    }
                    UpdateState.ReadyToInstall -> {
                        inAppStoreManager.updateProvider.completeUpdate()
                    }
                    else -> Unit
                }
            }
        }

        fun onReviewButtonClick(activity: Activity) {
            viewModelScope.launch {
                inAppStoreManager.reviewProvider.requestReview(activity)
            }
        }
    }

data class HomeUiState(
    val labels: SnapshotStateList<Label> = mutableStateListOf(),
    val updateState: UpdateState = UpdateState.Unavailable,
    val isReviewAvailable: Boolean = false,
    val isIdle: Boolean = true,
)
