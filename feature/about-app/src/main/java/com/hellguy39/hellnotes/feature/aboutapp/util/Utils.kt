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
package com.hellguy39.hellnotes.feature.aboutapp.util

import android.content.Context
import android.content.Intent
import android.net.Uri

internal fun Context.provideFeedback() {
    val intent = Intent(Intent.ACTION_SEND)

    intent.apply {
        selector = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))

        putExtra(Intent.EXTRA_EMAIL, arrayOf("hellguy39dev@gmail.com"))
        putExtra(Intent.EXTRA_SUBJECT, "HellNotes [Feedback]")
        // putExtra(Intent.EXTRA_TEXT, "Your message")
    }

    startActivity(Intent.createChooser(intent, "Send email using..."))
}

internal fun Context.openGithub() {
    val browserIntent =
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://github.com/hellguy39"),
        )
    startActivity(browserIntent)
}
