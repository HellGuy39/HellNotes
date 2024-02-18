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
