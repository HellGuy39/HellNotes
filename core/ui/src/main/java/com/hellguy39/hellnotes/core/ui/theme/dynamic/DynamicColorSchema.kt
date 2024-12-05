package com.hellguy39.hellnotes.core.ui.theme.dynamic

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme

@RequiresApi(Build.VERSION_CODES.S)
fun dynamicColorSchema(context: Context, darkTheme: Boolean = false) : ColorScheme {
    return if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
}
