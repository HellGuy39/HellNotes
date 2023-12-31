package com.hellguy39.hellnotes.core.ui.navigations

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.model.LockScreenType
import com.hellguy39.hellnotes.core.ui.state.AppState
import com.hellguy39.hellnotes.core.ui.state.lifecycleIsResumed

private fun defaultNavOptions() = navOptions { launchSingleTop = true }

fun AppState.navigateToOnBoarding(
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

fun AppState.navigateToNoteDetail(
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

fun AppState.navigateToReminderEdit(
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

fun AppState.navigateToLabelSelection(
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

fun AppState.navigateToLockSetup(
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

fun AppState.navigateToSettings(
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

fun AppState.navigateToAboutApp(
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

fun AppState.navigateToHome(
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

fun AppState.navigateToSearch(
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

fun AppState.navigateToLabelEdit(
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

fun AppState.navigateToLock(
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

fun AppState.navigateToLockSelection(
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

fun AppState.navigateToLanguageSelection(
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

fun AppState.navigateToNoteStyleEdit(
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

fun AppState.navigateToNoteSwipeEdit(
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

fun AppState.navigateToReset(
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

fun AppState.navigateToChangelog(
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

fun AppState.navigateToPrivacyPolicy(
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

fun AppState.navigateToTermsAndConditions(
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

fun AppState.navigateToUpdate(
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

fun AppState.navigateToBackup(
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
