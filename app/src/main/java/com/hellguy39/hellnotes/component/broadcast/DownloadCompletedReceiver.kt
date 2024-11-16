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
package com.hellguy39.hellnotes.component.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownloadCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
//        val downloadManager = context?.getSystemService(DownloadManager::class.java)
//
//        if (intent?.action == "android.intent.action.DOWNLOAD_COMPLETE") {
//            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, ArgumentDefaultValues.EMPTY)
//            val filepath = downloadManager?.getUriForDownloadedFile(id)
// //            if (filepath != null)
// //                context.openApkFile(filepath)
//        }
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
