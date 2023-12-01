package com.hellguy39.hellnotes.android_features

import android.annotation.SuppressLint
import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.hellguy39.hellnotes.core.domain.system_features.LanguageHolder
import com.hellguy39.hellnotes.core.model.Language
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@SuppressLint("NewApi")
class AndroidLanguageHolder
@Inject
constructor(
    @ApplicationContext private val context: Context
): LanguageHolder {

    private val localeManager by lazy {
        context.getSystemService(LocaleManager::class.java)
    }

    private val _languageFlow = MutableStateFlow(getLanguage())
    override val languageFlow: Flow<Language> = _languageFlow.asStateFlow()

    override suspend fun setLanguage(language: Language) {
        _languageFlow.emit(language)
        setLanguageByTag(language.tag)
    }

    override fun getLanguage(): Language {
        return Language.fromTag(getLanguageTag())
    }

    private fun setLanguageByTag(tag: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            localeManager.applicationLocales = LocaleList.forLanguageTags(tag)
        } else {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(tag)
            )
        }
    }

    private fun getLanguageTag(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val appLocales = localeManager.applicationLocales
            appLocales.toLanguageTags()
        } else {
            val appLocales = AppCompatDelegate.getApplicationLocales()
            appLocales.toLanguageTags()
        }
    }
}