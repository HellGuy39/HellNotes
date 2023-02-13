package com.hellguy39.hellnotes.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.model.util.Language
import com.hellguy39.hellnotes.core.model.util.LockScreenType
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