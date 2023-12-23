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
