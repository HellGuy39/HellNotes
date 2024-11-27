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
import android.content.Context
import com.hellguy39.hellnotes.core.domain.repository.store.UpdateProvider
import com.hellguy39.hellnotes.core.domain.repository.store.UpdateState
import com.hellguy39.hellnotes.core.storeapi.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import ru.rustore.sdk.appupdate.listener.InstallStateUpdateListener
import ru.rustore.sdk.appupdate.manager.factory.RuStoreAppUpdateManagerFactory
import ru.rustore.sdk.appupdate.model.AppUpdateInfo
import ru.rustore.sdk.appupdate.model.AppUpdateOptions
import ru.rustore.sdk.appupdate.model.AppUpdateType
import ru.rustore.sdk.appupdate.model.InstallState
import ru.rustore.sdk.appupdate.model.InstallStatus
import ru.rustore.sdk.appupdate.model.UpdateAvailability
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RuStoreUpdateProvider(
    context: Context,
    private val ioDispatcher: CoroutineDispatcher,
) : UpdateProvider {
    private val installStatusConsumer by lazy { InstallStatusConsumer() }
    private val installListener by lazy { InstallListener() }
    private val updateManager by lazy { RuStoreAppUpdateManagerFactory.create(context) }
    private val appUpdateOptions by lazy {
        AppUpdateOptions.Builder().appUpdateType(AppUpdateType.FLEXIBLE).build()
    }

    override val appStoreName = context.getString(R.string.core_store_api_ru_store)

    private val _updateState = MutableStateFlow<UpdateState>(UpdateState.Unavailable)
    override val updateState = _updateState.asStateFlow()

    override fun attach() {
        updateManager.registerListener(installListener)
    }

    override fun detach() {
        updateManager.unregisterListener(installListener)
    }

    override suspend fun checkUpdateAvailability() {
        return withContext(ioDispatcher) {
            val appUpdateInfo = appUpdateInfo() ?: return@withContext
            when (appUpdateInfo.updateAvailability) {
                UpdateAvailability.UPDATE_AVAILABLE -> {
                    _updateState.update { UpdateState.Available }
                    installStatusConsumer.consume(appUpdateInfo.installStatus)
                }
                UpdateAvailability.UPDATE_NOT_AVAILABLE -> {
                    _updateState.update { UpdateState.Unavailable }
                }
                UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS -> {
                    installStatusConsumer.consume(appUpdateInfo.installStatus)
                }
                UpdateAvailability.UNKNOWN -> {
                    installStatusConsumer.consume(appUpdateInfo.installStatus)
                }
            }
        }
    }

    override suspend fun downloadUpdate(activity: Activity) {
        withContext(ioDispatcher) {
            val appUpdateInfo = appUpdateInfo() ?: return@withContext
            updateManager.startUpdateFlow(appUpdateInfo, appUpdateOptions)
        }
    }

    override suspend fun completeUpdate() {
        withContext(ioDispatcher) {
            val appUpdateInfo = appUpdateInfo() ?: return@withContext
            if (appUpdateInfo.installStatus == InstallStatus.DOWNLOADED) {
                updateManager.completeUpdate(appUpdateOptions)
            }
        }
    }

    private suspend fun appUpdateInfo(): AppUpdateInfo? {
        return suspendCoroutine { continuation ->
            updateManager.getAppUpdateInfo()
                .addOnSuccessListener { appUpdateInfo ->
                    continuation.resume(appUpdateInfo)
                }
                .addOnFailureListener {
                    continuation.resume(null)
                }
        }
    }

    private inner class InstallListener : InstallStateUpdateListener {
        override fun onStateUpdated(state: InstallState) {
            installStatusConsumer.consume(state.installStatus)
        }
    }

    private inner class InstallStatusConsumer {
        fun consume(installStatus: Int) {
            when (installStatus) {
                InstallStatus.INSTALLING -> {
                }
                InstallStatus.FAILED -> {
                }
                InstallStatus.PENDING -> {
                }
                InstallStatus.DOWNLOADED -> {
                    _updateState.update { UpdateState.ReadyToInstall }
                }
                InstallStatus.DOWNLOADING -> {
                    _updateState.update { UpdateState.Downloading }
                }
                InstallStatus.UNKNOWN -> {
                }
            }
        }
    }
}
