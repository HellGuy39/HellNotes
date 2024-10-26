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
package com.hellguy39.hellnotes.update.providers

import android.content.Context
import com.hellguy39.hellnotes.update.UpdateProvider
import ru.rustore.sdk.appupdate.manager.factory.RuStoreAppUpdateManagerFactory
import ru.rustore.sdk.appupdate.model.AppUpdateInfo
import ru.rustore.sdk.appupdate.model.AppUpdateOptions
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RuStoreUpdateProvider(
    context: Context,
) : UpdateProvider() {
    private val updateManager = RuStoreAppUpdateManagerFactory.create(context)

    private var appUpdateInfo: AppUpdateInfo? = null

    override val providerName: String
        get() = "RuStore"

    override suspend fun isUpdateAvailable(): Boolean {
        return suspendCoroutine { continuation ->
            updateManager
                .getAppUpdateInfo()
                .addOnSuccessListener { info ->
                    appUpdateInfo = info
                    if (info.updateAvailability == 1) {
                        continuation.resume(true)
                    } else {
                        continuation.resume(false)
                    }
                }
                .addOnFailureListener { throwable ->
                    continuation.resume(false)
                }
        }
    }

    override fun launchUpdate() {
        val info = appUpdateInfo ?: return
        val options = AppUpdateOptions.Builder().build()
        updateManager
            .startUpdateFlow(info, options)
            .addOnSuccessListener { resultCode ->
            }
            .addOnFailureListener { throwable ->
            }
    }
}
