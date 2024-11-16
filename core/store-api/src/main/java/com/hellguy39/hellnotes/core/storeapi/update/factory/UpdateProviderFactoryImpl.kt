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
package com.hellguy39.hellnotes.core.storeapi.update.factory

import android.content.Context
import com.hellguy39.hellnotes.core.common.di.IoDispatcher
import com.hellguy39.hellnotes.core.domain.repository.store.UpdateProvider
import com.hellguy39.hellnotes.core.storeapi.update.provider.NoneUpdateProvider
import com.hellguy39.hellnotes.core.storeapi.update.provider.PlayStoreUpdateProvider
import com.hellguy39.hellnotes.core.storeapi.update.provider.RuStoreUpdateProvider
import com.hellguy39.hellnotes.core.storeapi.update.provider.SimulationUpdateProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateProviderFactoryImpl
    @Inject
    constructor(
        @ApplicationContext
        private val context: Context,
        @IoDispatcher
        private val ioDispatcher: CoroutineDispatcher,
    ) : UpdateProviderFactory {
        private val providersList by lazy {
            listOf(
                RuStoreUpdateProvider(context, ioDispatcher),
                PlayStoreUpdateProvider(context, ioDispatcher),
                NoneUpdateProvider(context),
            )
        }

        override fun create(appStoreName: String): UpdateProvider {
            return providersList.find { provider ->
                provider.appStoreName == appStoreName
            } ?: SimulationUpdateProvider()
        }
    }
