package com.hellguy39.hellnotes.core.ui.navigations

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.hellguy39.hellnotes.core.model.LockScreenType
import com.hellguy39.hellnotes.core.ui.HellNotesAppState
import com.hellguy39.hellnotes.core.ui.lifecycleIsResumed

sealed class Screen(val route: String) {

    data object Startup : Screen(route = "startup_screen")

    data object OnBoarding : Screen(route = "on_boarding_screen")

    data object Lock : Screen(route = "lock_screen")

    data object LockSetup : Screen(route = "lock_setup_screen")

    data object LanguageSelection : Screen(route = "language_selection_screen")

    data object LockSelection : Screen(route = "lock_selection_screen")

    data object Home : Screen(route = "home_screen")

    data object Settings : Screen(route = "settings_screen")

    data object NoteDetail : Screen(route = "note_detail_screen")

    data object ReminderEdit : Screen(route = "reminder_edit_screen")

    data object Search : Screen(route = "search_screen")

    data object AboutApp : Screen(route = "about_app_screen")

    data object LabelEdit : Screen(route = "label_edit_screen")

    data object LabelSelection : Screen(route = "label_selection_screen")

    data object NoteStyleEdit : Screen(route = "note_style_edit_screen")

    data object NoteSwipeEdit : Screen(route = "note_swipe_edit_screen")

    data object Changelog : Screen(route = "changelog_screen")

    data object Reset : Screen(route = "reset_screen")

    data object PrivacyPolicy : Screen(route = "privacy_policy_screen")

    data object TermsAndConditions : Screen(route = "terms_and_conditions_screen")

    data object Update : Screen(route = "update_screen")

    data object Backup : Screen(route = "backup_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    fun withArgKeys(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/{$arg}")
            }
        }
    }
}

object ArgumentKeys {
    const val NoteId = "note_id"
    const val ReminderId = "reminder_id"
    const val LockType = "lock_type"
    const val Action = "action"
    const val ShortcutAction = "shortcut_action"
}

object ArgumentDefaultValues {
    const val Empty: Long = -2
    const val NewNote: Long = -1
    const val NewReminder: Long = -1
}

private fun defaultNavOptions() = navOptions { launchSingleTop = true }

fun NavController.navigateToOnBoarding(
    navOptions: NavOptions = defaultNavOptions()
) {
    navigate(
        route = Screen.OnBoarding.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToNoteDetail(
    noteId: Long? = ArgumentDefaultValues.NewNote,
    navOptions: NavOptions = defaultNavOptions()
) {
    val noteIdArg = noteId ?: ArgumentDefaultValues.NewNote

    navigate(
        route = Screen.NoteDetail.withArgs(noteIdArg.toString()),
        navOptions = navOptions
    )
}

fun NavController.navigateToReminderEdit(
    noteId: Long? = ArgumentDefaultValues.NewNote,
    reminderId: Long? = ArgumentDefaultValues.NewReminder,
    navOptions: NavOptions = defaultNavOptions()
) {
    val noteIdArg = noteId ?: ArgumentDefaultValues.NewNote
    val reminderIdArg = reminderId ?: ArgumentDefaultValues.NewReminder

    navigate(
        route = Screen.ReminderEdit.withArgs(noteIdArg.toString(), reminderIdArg.toString()),
        navOptions = navOptions
    )
}

fun NavController.navigateToLabelSelection(
    noteId: Long? = ArgumentDefaultValues.NewNote,
    navOptions: NavOptions = defaultNavOptions()
) {
    val noteIdArg = noteId ?: ArgumentDefaultValues.NewNote

    navigate(
        route = Screen.LabelSelection.withArgs(noteIdArg.toString()),
        navOptions = navOptions
    )
}

fun NavController.navigateToLockSetup(
    lockType: LockScreenType,
    navOptions: NavOptions = defaultNavOptions()
) {
    navigate(
        route = Screen.LockSetup.withArgs(lockType.string()),
        navOptions = navOptions
    )
}

fun NavController.navigateToSettings(
    navOptions: NavOptions = defaultNavOptions()
) {
    navigate(
        route = Screen.Settings.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToAboutApp(
    navOptions: NavOptions = defaultNavOptions()
) {
    navigate(
        route = Screen.AboutApp.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToHome(
    navOptions: NavOptions = defaultNavOptions()
) {
    navigate(
        route = Screen.Home.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToSearch(
    navOptions: NavOptions = defaultNavOptions()
) {
    navigate(
        route = Screen.Search.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToLabelEdit(
    navOptions: NavOptions = defaultNavOptions(),
    action: String
) {
    navigate(
        route = Screen.LabelEdit.withArgs(action),
        navOptions = navOptions
    )
}

fun NavController.navigateToLock(
    navOptions: NavOptions = defaultNavOptions()
) {
    navigate(
        route = Screen.Lock.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToLockSelection(
    navOptions: NavOptions = defaultNavOptions()
) {
    navigate(
        route = Screen.LockSelection.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToLanguageSelection(
    navOptions: NavOptions = defaultNavOptions()
) {
    navigate(
        route = Screen.LanguageSelection.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToNoteStyleEdit(
    navOptions: NavOptions = defaultNavOptions()
) {
    navigate(
        route = Screen.NoteStyleEdit.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToNoteSwipeEdit(
    navOptions: NavOptions = defaultNavOptions()
) {
    navigate(
        route = Screen.NoteSwipeEdit.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToReset(
    navOptions: NavOptions = defaultNavOptions()
) {
    navigate(
        route = Screen.Reset.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToChangelog(
    navOptions: NavOptions = defaultNavOptions()
) {
    navigate(
        route = Screen.Changelog.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToPrivacyPolicy(
    navOptions: NavOptions = defaultNavOptions()
) {
    navigate(
        route = Screen.PrivacyPolicy.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToTermsAndConditions(
    navOptions: NavOptions = defaultNavOptions()
) {
    navigate(
        route = Screen.TermsAndConditions.route,
        navOptions = navOptions
    )
}


fun NavController.navigateToUpdate(
    navOptions: NavOptions = defaultNavOptions()
) {
    navigate(
        route = Screen.Update.route,
        navOptions = navOptions
    )
}


fun NavController.navigateToBackup(
    navOptions: NavOptions = defaultNavOptions()
) {
    navigate(
        route = Screen.Backup.route,
        navOptions = navOptions
    )
}

fun HellNotesAppState.navigateToOnBoarding(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions()
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.OnBoarding.route,
            navOptions = navOptions
        )
    }
}

fun HellNotesAppState.navigateToNoteDetail(
    from: NavBackStackEntry,
    noteId: Long? = ArgumentDefaultValues.NewNote,
    navOptions: NavOptions = defaultNavOptions()
) {
    val noteIdArg = noteId ?: ArgumentDefaultValues.NewNote

    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.NoteDetail.withArgs(noteIdArg.toString()),
            navOptions = navOptions
        )
    }
}

fun HellNotesAppState.navigateToReminderEdit(
    from: NavBackStackEntry,
    noteId: Long? = ArgumentDefaultValues.NewNote,
    reminderId: Long? = ArgumentDefaultValues.NewReminder,
    navOptions: NavOptions = defaultNavOptions()
) {
    val noteIdArg = noteId ?: ArgumentDefaultValues.NewNote
    val reminderIdArg = reminderId ?: ArgumentDefaultValues.NewReminder

    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.ReminderEdit.withArgs(noteIdArg.toString(), reminderIdArg.toString()),
            navOptions = navOptions
        )
    }
}

fun HellNotesAppState.navigateToLabelSelection(
    from: NavBackStackEntry,
    noteId: Long? = ArgumentDefaultValues.NewNote,
    navOptions: NavOptions = defaultNavOptions()
) {
    val noteIdArg = noteId ?: ArgumentDefaultValues.NewNote

    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.LabelSelection.withArgs(noteIdArg.toString()),
            navOptions = navOptions
        )
    }
}

fun HellNotesAppState.navigateToLockSetup(
    from: NavBackStackEntry,
    lockType: LockScreenType,
    navOptions: NavOptions = defaultNavOptions()
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.LockSetup.withArgs(lockType.string()),
            navOptions = navOptions
        )
    }
}

fun HellNotesAppState.navigateToSettings(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions()
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.Settings.route,
            navOptions = navOptions
        )
    }
}

fun HellNotesAppState.navigateToAboutApp(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions()
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.AboutApp.route,
            navOptions = navOptions
        )
    }
}

fun HellNotesAppState.navigateToHome(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions()
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.Home.route,
            navOptions = navOptions
        )
    }
}

fun HellNotesAppState.navigateToSearch(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions()
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.Search.route,
            navOptions = navOptions
        )
    }
}

fun HellNotesAppState.navigateToLabelEdit(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions(),
    action: String
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.LabelEdit.withArgs(action),
            navOptions = navOptions
        )
    }
}

fun HellNotesAppState.navigateToLock(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions()
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.Lock.route,
            navOptions = navOptions
        )
    }
}

fun HellNotesAppState.navigateToLockSelection(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions()
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.LockSelection.route,
            navOptions = navOptions
        )
    }
}

fun HellNotesAppState.navigateToLanguageSelection(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions()
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.LanguageSelection.route,
            navOptions = navOptions
        )
    }
}

fun HellNotesAppState.navigateToNoteStyleEdit(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions()
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.NoteStyleEdit.route,
            navOptions = navOptions
        )
    }
}

fun HellNotesAppState.navigateToNoteSwipeEdit(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions()
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.NoteSwipeEdit.route,
            navOptions = navOptions
        )
    }
}

fun HellNotesAppState.navigateToReset(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions()
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.Reset.route,
            navOptions = navOptions
        )
    }
}

fun HellNotesAppState.navigateToChangelog(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions()
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.Changelog.route,
            navOptions = navOptions
        )
    }
}

fun HellNotesAppState.navigateToPrivacyPolicy(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions()
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.PrivacyPolicy.route,
            navOptions = navOptions
        )
    }
}

fun HellNotesAppState.navigateToTermsAndConditions(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions()
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.TermsAndConditions.route,
            navOptions = navOptions
        )
    }
}


fun HellNotesAppState.navigateToUpdate(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions()
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.Update.route,
            navOptions = navOptions
        )
    }
}


fun HellNotesAppState.navigateToBackup(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions()
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Screen.Backup.route,
            navOptions = navOptions
        )
    }
}
