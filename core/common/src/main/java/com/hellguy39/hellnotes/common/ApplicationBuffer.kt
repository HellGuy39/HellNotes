package com.hellguy39.hellnotes.common

object ApplicationBuffer {

    private var versionName: String = "Untitled"

    fun setVersionName(version: String) {
        versionName = version
    }

    fun getVersionName() = versionName

}