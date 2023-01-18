package com.hellguy39.hellnotes.core.ui

import com.hellguy39.hellnotes.core.model.AppConfig

object ProjectInfoProvider {

    var appConfig = AppConfig("Untitled", 0, "", "", false)
        private set

    fun setAppConfig(conf: AppConfig) {
        appConfig = conf
    }

}