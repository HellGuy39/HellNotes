package com.hellguy39.hellnotes.android_features

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.hellguy39.hellnotes.core.domain.system_features.Downloader
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidDownloader
@Inject
constructor(
    @ApplicationContext private val context: Context
) : Downloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadFile(url: String): Long {
        val request = DownloadManager.Request(url.toUri())
            .setMimeType("application/vnd.android.package-archive")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE and DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("Update for HellNotes...")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "hellnotes_update.apk")
        return downloadManager.enqueue(request)
    }

}