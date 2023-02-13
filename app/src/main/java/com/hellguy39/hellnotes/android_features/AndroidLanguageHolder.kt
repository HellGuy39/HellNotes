package com.hellguy39.hellnotes.android_features

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.hellguy39.hellnotes.core.domain.system_features.LanguageHolder
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidLanguageHolder @Inject constructor(
    @ApplicationContext private val context: Context
): LanguageHolder {
    override fun setLanguageCode(code: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(code)
        } else {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(code)
            )
        }
    }

    override fun getLanguageCode(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val appLocales = context.getSystemService(LocaleManager::class.java).applicationLocales
            appLocales.toLanguageTags()
        } else {
            val appLocales = AppCompatDelegate.getApplicationLocales()
            appLocales.toLanguageTags()
        }
    }
}