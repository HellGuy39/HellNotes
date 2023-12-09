package com.hellguy39.hellnotes.component.broadcast

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentDefaultValues
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownloadCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        val downloadManager = context?.getSystemService(DownloadManager::class.java)

        if (intent?.action == "android.intent.action.DOWNLOAD_COMPLETE") {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, ArgumentDefaultValues.EMPTY)
            val filepath = downloadManager?.getUriForDownloadedFile(id)
//            if (filepath != null)
//                context.openApkFile(filepath)
        }
    }

//    private fun Context.openApkFile(filepath: Uri) {
//        val intent = Intent(Intent.ACTION_VIEW)
//        intent.setDataAndType(
//            filepath,
//            "application/vnd.android.package-archive"
//        )
//        //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivity(intent)
//    }
}
