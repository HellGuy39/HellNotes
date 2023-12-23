package com.hellguy39.hellnotes.core.common.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.hellguy39.hellnotes.core.common.api.ApiCapabilities

fun Context.canPostNotifications(): Boolean {
    if (!ApiCapabilities.postNotificationsPermissionRequired) return true

    return ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.POST_NOTIFICATIONS,
    ) == PackageManager.PERMISSION_GRANTED
}
