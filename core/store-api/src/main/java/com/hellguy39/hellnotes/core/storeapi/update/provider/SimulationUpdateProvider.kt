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
package com.hellguy39.hellnotes.core.storeapi.update.provider

import android.app.Activity
import com.hellguy39.hellnotes.core.domain.repository.store.UpdateProvider
import com.hellguy39.hellnotes.core.domain.repository.store.UpdateState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SimulationUpdateProvider : UpdateProvider {
    private val _updateState = MutableStateFlow<UpdateState>(UpdateState.Unavailable)
    override val updateState = _updateState

    override val appStoreName: String
        get() = "simulation"

    override fun attach() {}

    override fun detach() {}

    override suspend fun checkUpdateAvailability() {
        _updateState.update { UpdateState.Available }
    }

    override suspend fun downloadUpdate(activity: Activity) {
        _updateState.update { UpdateState.Downloading }
        delay(5000)
        _updateState.update { UpdateState.ReadyToInstall }
    }

    override suspend fun completeUpdate() {
        _updateState.update { UpdateState.Unavailable }
    }
}
