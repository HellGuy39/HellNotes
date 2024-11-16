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
package com.hellguy39.hellnotes.core.storeapi.review.provider

import android.app.Activity
import android.content.Context
import com.hellguy39.hellnotes.core.common.di.IoDispatcher
import com.hellguy39.hellnotes.core.domain.repository.store.ReviewProvider
import com.hellguy39.hellnotes.core.storeapi.R
import com.hellguy39.hellnotes.core.storeapi.storage.StoreManagerStorage
import com.hellguy39.hellnotes.core.storeapi.storage.completeReview
import com.hellguy39.hellnotes.core.storeapi.storage.reviewState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.rustore.sdk.review.RuStoreReviewManagerFactory
import ru.rustore.sdk.review.model.ReviewInfo
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RuStoreReviewProvider(
    context: Context,
    private val storeManagerStorage: StoreManagerStorage,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
) : ReviewProvider {
    private val manager by lazy { RuStoreReviewManagerFactory.create(context) }

    override val reviewState = storeManagerStorage.reviewState()

    override suspend fun requestReview(activity: Activity) {
        withContext(ioDispatcher) {
            val reviewInfo = requestReview() ?: return@withContext
            val isSuccess = launchReview(reviewInfo)
            if (isSuccess) {
                storeManagerStorage.completeReview()
            }
        }
    }

    override val appStoreName: String = context.getString(R.string.core_store_api_ru_store)

    private suspend fun requestReview(): ReviewInfo? {
        return suspendCoroutine { continuation ->
            manager.requestReviewFlow()
                .addOnSuccessListener { reviewInfo ->
                    continuation.resume(reviewInfo)
                }
                .addOnFailureListener { exception ->
                    exception.printStackTrace()
                    continuation.resume(null)
                }
        }
    }

    private suspend fun launchReview(reviewInfo: ReviewInfo): Boolean {
        return suspendCoroutine { continuation ->
            manager.launchReviewFlow(reviewInfo)
                .addOnSuccessListener { _ ->
                    continuation.resume(true)
                }
                .addOnFailureListener { exception ->
                    exception.printStackTrace()
                    continuation.resume(false)
                }
        }
    }
}
