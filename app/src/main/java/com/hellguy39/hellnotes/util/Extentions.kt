package com.hellguy39.hellnotes.util

import android.app.Activity
import android.widget.Toast

fun Activity.showToast(
    message: String,
    length: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(
        applicationContext,
        message,
        Toast.LENGTH_SHORT
    ).show()
}