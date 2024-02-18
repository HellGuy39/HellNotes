package com.hellguy39.hellnotes.update

import android.content.Context
import com.hellguy39.hellnotes.update.providers.RuStoreUpdateProvider

object UpdateProviderFactory {
    fun create(context: Context): UpdateProvider {
        return RuStoreUpdateProvider(context)
    }
}
