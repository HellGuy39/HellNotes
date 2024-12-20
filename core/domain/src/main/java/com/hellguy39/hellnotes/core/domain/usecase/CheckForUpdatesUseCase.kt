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
package com.hellguy39.hellnotes.core.domain.usecase

import com.hellguy39.hellnotes.core.model.repository.remote.Release
import javax.inject.Inject

class CheckForUpdatesUseCase
    @Inject
    constructor() {
        operator fun invoke(latestRelease: Release): Boolean {
            val currentVersion = "" // ProjectInfoProvider.appConfig.versionName
            val latestVersion = latestRelease.tagName ?: ""

            return isUpdateNeeded(currentVersion, latestVersion)
        }

        private fun isUpdateNeeded(
            currentVersion: String,
            latestVersion: String,
        ): Boolean {
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
                this.toMutableList().apply {
                    for (i in 0..difference) {
                        add(0)
                    }
                }
            }
        }
    }
