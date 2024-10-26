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
package com.hellguy39.hellnotes.update

import android.content.Context
import com.hellguy39.hellnotes.core.domain.manager.UpdateManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UpdateManagerImpl
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : UpdateManager {
        private val updateProvider = UpdateProviderFactory.create(context)

        override val providerName: String
            get() = updateProvider.providerName

        override suspend fun checkForUpdates() {
            if (updateProvider.isUpdateAvailable()) {
                updateProvider.launchUpdate()
            }
        }
    }
