package com.hellguy39.hellnotes.core.ui.navigations

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.hellguy39.hellnotes.core.model.LockScreenType

sealed class Screen(val route: String) {

    object Startup : Screen(route = "startup_screen")

    object OnBoarding : Screen(route = "on_boarding_screen")

    object Lock : Screen(route = "lock_screen")

    object LockSetup : Screen(route = "lock_setup_screen")

    object LanguageSelection : Screen(route = "language_selection_screen")

    object LockSelection : Screen(route = "lock_selection_screen")

    object Home : Screen(route = "home_screen")

    object Settings : Screen(route = "settings_screen")

    object NoteDetail : Screen(route = "note_detail_screen")

    object ReminderEdit : Screen(route = "reminder_edit_screen")

    object Search : Screen(route = "search_screen")

    object AboutApp : Screen(route = "about_app_screen")

    object LabelEdit : Screen(route = "label_edit_screen")

    object LabelSelection : Screen(route = "label_selection_screen")

    object NoteStyleEdit : Screen(route = "note_style_edit_screen")

    object NoteSwipeEdit : Screen(route = "note_swipe_edit_screen")

    object Changelog : Screen(route = "changelog_screen")

    object Reset : Screen(route = "reset_screen")

    object PrivacyPolicy : Screen(route = "privacy_policy_screen")

    object TermsAndConditions : Screen(route = "terms_and_conditions_screen")

    object Update : Screen(route = "update_screen")

    object Backup : Screen(route = "backup_screen")

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
