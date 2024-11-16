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
package com.hellguy39.hellnotes.core.storeapi.di

import com.hellguy39.hellnotes.core.domain.repository.store.InAppStoreManager
import com.hellguy39.hellnotes.core.storeapi.InAppStoreManagerImpl
import com.hellguy39.hellnotes.core.storeapi.review.factory.ReviewProviderFactory
import com.hellguy39.hellnotes.core.storeapi.review.factory.ReviewProviderFactoryImpl
import com.hellguy39.hellnotes.core.storeapi.storage.StoreManagerStorage
import com.hellguy39.hellnotes.core.storeapi.storage.StoreManagerStorageImpl
import com.hellguy39.hellnotes.core.storeapi.update.factory.UpdateProviderFactory
import com.hellguy39.hellnotes.core.storeapi.update.factory.UpdateProviderFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface StoreAPIModule {
    @Binds
    fun bindInAppStoreManager(i: InAppStoreManagerImpl): InAppStoreManager

    @Binds
    fun bindUpdateProviderFactory(i: UpdateProviderFactoryImpl): UpdateProviderFactory

    @Binds
    fun bindReviewProviderFactory(i: ReviewProviderFactoryImpl): ReviewProviderFactory

    @Binds
    fun bindStoreManagerStorage(i: StoreManagerStorageImpl): StoreManagerStorage
}
