package com.hellguy39.hellnotes.core.domain.use_case

import com.hellguy39.hellnotes.core.domain.ProjectInfoProvider
import com.hellguy39.hellnotes.core.model.Release
import javax.inject.Inject

class CheckForUpdatesUseCase @Inject constructor() {

    operator fun invoke(latestRelease: Release): Boolean {

        val currentVersion = ProjectInfoProvider.appConfig.versionName
        val latestVersion = latestRelease.tag_name ?: ""

        return isUpdateNeeded(currentVersion, latestVersion)
    }

    private fun isUpdateNeeded(currentVersion: String, latestVersion: String): Boolean {
        var formattedCurrentVersion = currentVersion.format()
        var formattedLatestVersion = latestVersion.format()

        formattedCurrentVersion = formattedCurrentVersion.fillWithComparable(formattedLatestVersion)
        formattedLatestVersion = formattedLatestVersion.fillWithComparable(formattedCurrentVersion)

        for (i in formattedCurrentVersion.indices) {
            if (formattedLatestVersion[i] > formattedCurrentVersion[i]) {
                return true
            }
        }

        return false
    }

    private fun String.format(): List<Int> {
        return this.removePrefix("v")
            .removePrefix("version")
            .replace("-", "")
            .replace("rc", ".3.")
            .replace("release_candidate", ".3.")
            .replace("beta", ".2.")
            .replace("alpha", ".1.")
            .split(".").map { it.toInt() }
    }

    private fun List<Int>.fillWithComparable(comparableList: List<Int>): List<Int> {
        return if (this.size >= comparableList.size) {
            this
        } else {
            val difference = comparableList.size - this.size
            this.toMutableList().apply { for (i in 0..difference) { add(0) } }
        }
    }

}