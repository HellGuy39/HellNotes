package com.hellguy39.hellnotes.core.ui.navigations

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.model.LockScreenType
import com.hellguy39.hellnotes.core.ui.HellNotesAppState
import com.hellguy39.hellnotes.core.ui.lifecycleIsResumed

private fun defaultNavOptions() = navOptions { launchSingleTop = true }

fun HellNotesAppState.navigateToOnBoarding(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions(),
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.OnBoarding.route,
            navOptions = navOptions,
        )
    }
}

fun HellNotesAppState.navigateToNoteDetail(
    from: NavBackStackEntry,
    noteId: Long? = Arguments.NoteId.emptyValue,
    navOptions: NavOptions = defaultNavOptions(),
) {
    val noteIdArg = noteId ?: Arguments.NoteId.emptyValue

    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.NoteDetail.withArgs(noteIdArg.toString()),
            navOptions = navOptions,
        )
    }
}

fun HellNotesAppState.navigateToReminderEdit(
    from: NavBackStackEntry,
    noteId: Long? = Arguments.NoteId.emptyValue,
    reminderId: Long? = Arguments.ReminderId.emptyValue,
    navOptions: NavOptions = defaultNavOptions(),
) {
    val noteIdArg = noteId ?: Arguments.NoteId.emptyValue
    val reminderIdArg = reminderId ?: Arguments.ReminderId.emptyValue

    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.ReminderEdit.withArgs(noteIdArg.toString(), reminderIdArg.toString()),
            navOptions = navOptions,
        )
    }
}

fun HellNotesAppState.navigateToLabelSelection(
    from: NavBackStackEntry,
    noteId: Long? = Arguments.NoteId.emptyValue,
    navOptions: NavOptions = defaultNavOptions(),
) {
    val noteIdArg = noteId ?: Arguments.NoteId.emptyValue

    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.LabelSelection.withArgs(noteIdArg.toString()),
            navOptions = navOptions,
        )
    }
}

fun HellNotesAppState.navigateToLockSetup(
    from: NavBackStackEntry,
    lockType: LockScreenType,
    navOptions: NavOptions = defaultNavOptions(),
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.LockSetup.withArgs(lockType.tag),
            navOptions = navOptions,
        )
    }
}

fun HellNotesAppState.navigateToSettings(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions(),
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.Settings.route,
            navOptions = navOptions,
        )
    }
}

fun HellNotesAppState.navigateToAboutApp(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions(),
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.AboutApp.route,
            navOptions = navOptions,
        )
    }
}

fun HellNotesAppState.navigateToHome(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions(),
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.Home.route,
            navOptions = navOptions,
        )
    }
}

fun HellNotesAppState.navigateToSearch(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions(),
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.Search.route,
            navOptions = navOptions,
        )
    }
}

fun HellNotesAppState.navigateToLabelEdit(
    from: NavBackStackEntry,
    action: String,
    navOptions: NavOptions = defaultNavOptions(),
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.LabelEdit.withArgs(action),
            navOptions = navOptions,
        )
    }
}

fun HellNotesAppState.navigateToLock(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions(),
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.Lock.route,
            navOptions = navOptions,
        )
    }
}

fun HellNotesAppState.navigateToLockSelection(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions(),
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.LockSelection.route,
            navOptions = navOptions,
        )
    }
}

fun HellNotesAppState.navigateToLanguageSelection(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions(),
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.LanguageSelection.route,
            navOptions = navOptions,
        )
    }
}

fun HellNotesAppState.navigateToNoteStyleEdit(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions(),
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.NoteStyleEdit.route,
            navOptions = navOptions,
        )
    }
}

fun HellNotesAppState.navigateToNoteSwipeEdit(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions(),
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.NoteSwipeEdit.route,
            navOptions = navOptions,
        )
    }
}

fun HellNotesAppState.navigateToReset(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions(),
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.Reset.route,
            navOptions = navOptions,
        )
    }
}

fun HellNotesAppState.navigateToChangelog(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions(),
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.Changelog.route,
            navOptions = navOptions,
        )
    }
}

fun HellNotesAppState.navigateToPrivacyPolicy(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions(),
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.PrivacyPolicy.route,
            navOptions = navOptions,
        )
    }
}

fun HellNotesAppState.navigateToTermsAndConditions(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions(),
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.TermsAndConditions.route,
            navOptions = navOptions,
        )
    }
}

fun HellNotesAppState.navigateToUpdate(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions(),
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.Update.route,
            navOptions = navOptions,
        )
    }
}

fun HellNotesAppState.navigateToBackup(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions(),
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.Backup.route,
            navOptions = navOptions,
        )
    }
}
