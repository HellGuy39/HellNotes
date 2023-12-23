package com.hellguy39.hellnotes.core.common.api

import android.os.Build

object ApiCapabilities {
    /**
     * Returns true for API 33+
     */
    val postNotificationsPermissionRequired
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
}
