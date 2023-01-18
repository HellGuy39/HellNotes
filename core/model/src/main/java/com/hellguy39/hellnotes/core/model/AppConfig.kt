package com.hellguy39.hellnotes.core.model

data class AppConfig(
    val versionName: String,
    val versionCode: Int,
    val buildType: String,
    var applicationId: String,
    var isDebug: Boolean
)
