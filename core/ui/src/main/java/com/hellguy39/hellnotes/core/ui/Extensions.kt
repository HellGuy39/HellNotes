package com.hellguy39.hellnotes.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.model.Language
import com.hellguy39.hellnotes.core.model.LockScreenType
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipe
import com.hellguy39.hellnotes.core.model.repository.local.datastore.Repeat
import com.hellguy39.hellnotes.core.ui.resources.AppStrings

@Composable
fun LockScreenType.asDisplayableString(): String {
    return when (this) {
        LockScreenType.None -> stringResource(id = AppStrings.MenuItem.None)
        LockScreenType.Pin -> stringResource(id = AppStrings.MenuItem.Pin)
        LockScreenType.Pattern -> stringResource(id = AppStrings.MenuItem.Pattern)
        LockScreenType.Password -> stringResource(id = AppStrings.MenuItem.Password)
        LockScreenType.Slide -> stringResource(id = AppStrings.MenuItem.Slide)
    }
}

@Composable
fun Language.asDisplayableString(): String {
    return when (this) {
        Language.Russian -> stringResource(id = AppStrings.Lan.Russian)
        Language.English -> stringResource(id = AppStrings.Lan.English)
        Language.German -> stringResource(id = AppStrings.Lan.German)
        Language.French -> stringResource(id = AppStrings.Lan.French)
        Language.SystemDefault -> stringResource(id = AppStrings.Lan.SystemDefault)
        else -> "Unknown"
    }
}

@Composable
fun Repeat.asDisplayableString(): String {
    return when (this) {
        Repeat.DoesNotRepeat -> stringResource(id = AppStrings.MenuItem.DoesNoteRepeat)
        Repeat.Daily -> stringResource(id = AppStrings.MenuItem.Daily)
        Repeat.Weekly -> stringResource(id = AppStrings.MenuItem.Weekly)
        Repeat.Monthly -> stringResource(id = AppStrings.MenuItem.Monthly)
    }
}

@Composable
fun NoteStyle.asDisplayableString(): String {
    return when (this) {
        NoteStyle.Outlined -> stringResource(id = AppStrings.MenuItem.Outlined)
        NoteStyle.Elevated -> stringResource(id = AppStrings.MenuItem.Elevated)
    }
}

@Composable
fun NoteSwipe.asDisplayableString(): String {
    return when (this) {
        NoteSwipe.None -> stringResource(id = AppStrings.MenuItem.None)
        NoteSwipe.Archive -> stringResource(id = AppStrings.MenuItem.Archive)
        NoteSwipe.Delete -> stringResource(id = AppStrings.MenuItem.Delete)
    }
}
