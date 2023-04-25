package com.hellguy39.hellnotes.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.model.Language
import com.hellguy39.hellnotes.core.model.LockScreenType
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipe
import com.hellguy39.hellnotes.core.model.repository.local.datastore.Repeat
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@Composable
fun LockScreenType.getDisplayName(): String {
    return when(this) {
        LockScreenType.None -> stringResource(id = HellNotesStrings.MenuItem.None)
        LockScreenType.Pin -> stringResource(id = HellNotesStrings.MenuItem.Pin)
        LockScreenType.Pattern -> stringResource(id = HellNotesStrings.MenuItem.Pattern)
        LockScreenType.Password -> stringResource(id = HellNotesStrings.MenuItem.Password)
        LockScreenType.Slide -> stringResource(id = HellNotesStrings.MenuItem.Slide)
    }
}

@Composable
fun Language.getDisplayName(): String {
    return when(this) {
        Language.Russian -> stringResource(id = HellNotesStrings.Lan.Russian)
        Language.English -> stringResource(id = HellNotesStrings.Lan.English)
        Language.German -> stringResource(id = HellNotesStrings.Lan.German)
        Language.French -> stringResource(id = HellNotesStrings.Lan.French)
        Language.SystemDefault -> stringResource(id = HellNotesStrings.Lan.SystemDefault)
        else -> "Unknown"
    }
}

@Composable
fun Repeat.getDisplayName(): String {
    return when(this) {
        Repeat.DoesNotRepeat -> stringResource(id = HellNotesStrings.MenuItem.DoesNoteRepeat)
        Repeat.Daily -> stringResource(id = HellNotesStrings.MenuItem.Daily)
        Repeat.Weekly -> stringResource(id = HellNotesStrings.MenuItem.Weekly)
        Repeat.Monthly -> stringResource(id = HellNotesStrings.MenuItem.Monthly)
    }
}

@Composable
fun NoteStyle.getDisplayName(): String {
    return when(this) {
        NoteStyle.Outlined -> stringResource(id = HellNotesStrings.MenuItem.Outlined)
        NoteStyle.Elevated -> stringResource(id = HellNotesStrings.MenuItem.Elevated)
    }
}

@Composable
fun NoteSwipe.getDisplayName(): String {
    return when(this) {
        NoteSwipe.None -> stringResource(id = HellNotesStrings.MenuItem.None)
        NoteSwipe.Archive -> stringResource(id = HellNotesStrings.MenuItem.Archive)
        NoteSwipe.Delete -> stringResource(id = HellNotesStrings.MenuItem.Delete)
    }
}