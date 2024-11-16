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
package com.hellguy39.hellnotes.core.storeapi.review.factory

import android.content.Context
import com.hellguy39.hellnotes.core.common.di.IoDispatcher
import com.hellguy39.hellnotes.core.storeapi.review.provider.NoneReviewProvider
import com.hellguy39.hellnotes.core.storeapi.review.provider.PlayStoreReviewProvider
import com.hellguy39.hellnotes.core.storeapi.review.provider.RuStoreReviewProvider
import com.hellguy39.hellnotes.core.storeapi.review.provider.SimulationReviewProvider
import com.hellguy39.hellnotes.core.storeapi.storage.StoreManagerStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewProviderFactoryImpl
    @Inject
    constructor(
        @ApplicationContext
        private val context: Context,
        private val storeManagerStorage: StoreManagerStorage,
        @IoDispatcher
        private val ioDispatcher: CoroutineDispatcher,
    ) : ReviewProviderFactory {
//        private val providersList by lazy {
//            listOf(
//                RuStoreReviewProvider(context, storeManagerStorage, ioDispatcher),
//                PlayStoreReviewProvider(context, storeManagerStorage, ioDispatcher),
//                NoneReviewProvider(context),
//            )
//        }

        override fun create(appStoreName: String) = NoneReviewProvider(context)
//            providersList.find { provider ->
//                provider.appStoreName == appStoreName
//            } ?: SimulationReviewProvider()
    }
