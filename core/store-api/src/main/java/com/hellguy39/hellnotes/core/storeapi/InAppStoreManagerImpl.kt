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
package com.hellguy39.hellnotes.core.storeapi

import com.hellguy39.hellnotes.core.domain.repository.store.InAppStoreManager
import com.hellguy39.hellnotes.core.storeapi.review.factory.ReviewProviderFactory
import com.hellguy39.hellnotes.core.storeapi.update.factory.UpdateProviderFactory
import javax.inject.Inject
import javax.inject.Singleton

private const val APP_STORE_NAME = BuildConfig.APP_STORE

@Singleton
class InAppStoreManagerImpl
    @Inject
    constructor(
        private val updateProviderFactory: UpdateProviderFactory,
        private val reviewProviderFactory: ReviewProviderFactory,
    ) : InAppStoreManager {
        override val updateProvider by lazy { updateProviderFactory.create(APP_STORE_NAME) }

        override val reviewProvider by lazy { reviewProviderFactory.create(APP_STORE_NAME) }
    }
