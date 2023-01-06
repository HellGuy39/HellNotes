package com.hellguy39.hellnotes.data.repository

import android.content.Context
import androidx.core.content.edit
import com.hellguy39.hellnotes.domain.repository.AppSettingsRepository
import com.hellguy39.hellnotes.model.util.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppSettingsRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
): AppSettingsRepository {

    private val prefs = context.getSharedPreferences(PREFS_UI_STATE, Context.MODE_PRIVATE)

    override fun getListStyle(): ListStyle {
        val listStyle = prefs.getString(
            ARG_LIST_STYLE,
            ListStyle.COLUMN
        ).toString()
        return ListStyle.from(listStyle)
    }

    override fun saveListStyle(listStyle: ListStyle) {
        prefs.edit {
            putString(ARG_LIST_STYLE, listStyle.parse())
            commit()
        }
    }

    override fun getListSort(): Sorting {
        val sorting = prefs.getString(
            ARG_SORTING,
            Sorting.DATE_OF_CREATION
        ).toString()
        return Sorting.from(sorting)
    }

    override fun saveListSort(sorting: Sorting) {
        prefs.edit {
            putString(ARG_SORTING, sorting.parse())
            commit()
        }
    }

    companion object {
        private const val PREFS_UI_STATE = "prefs_ui_state"

        private const val ARG_LIST_STYLE = "arg_list_style"
        private const val ARG_SORTING = "arg_sorting"
    }
}