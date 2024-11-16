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
package com.hellguy39.hellnotes.tools

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.hellguy39.hellnotes.core.domain.repository.system.Downloader
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DownloaderImpl
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : Downloader {
        private val downloadManager by lazy {
            context.getSystemService(DownloadManager::class.java)
        }

        override fun downloadFile(url: String): Long {
            val request =
                DownloadManager.Request(url.toUri())
                    .setMimeType("application/vnd.android.package-archive")
                    .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE and DownloadManager.Request.NETWORK_WIFI)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setTitle("Update for HellNotes...")
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "hellnotes_update.apk")
            return downloadManager.enqueue(request)
        }
    }
