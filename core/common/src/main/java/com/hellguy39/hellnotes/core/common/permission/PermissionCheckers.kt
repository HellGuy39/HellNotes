package com.hellguy39.hellnotes.core.common.permission

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.hellguy39.hellnotes.core.common.api.ApiCapabilities

fun Context.canPostNotifications(): Boolean {
    if (!ApiCapabilities.postNotificationsPermissionRequired) return true

    return ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.POST_NOTIFICATIONS,
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.canScheduleExactAlarms(): Boolean {
    if (!ApiCapabilities.scheduleExactAlarmsPermissionRequired) return true

    val alarmManager = ContextCompat.getSystemService(this, AlarmManager::class.java)
    return alarmManager?.canScheduleExactAlarms() == false
}
