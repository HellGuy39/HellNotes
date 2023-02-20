package com.hellguy39.hellnotes.core.ui.navigations

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.hellguy39.hellnotes.core.model.util.LockScreenType

sealed class Screen(val route: String) {
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
}

object ArgumentKeys {
    const val NoteId = "noteId"
    const val ReminderId = "reminderId"
    const val LockType = "lockType"
}

object ArgumentDefaultValues {
    const val NewNote: Long = -1
    const val NewReminder: Long = -1
}

fun NavController.navigateToNoteDetail(
    noteId: Long? = ArgumentDefaultValues.NewNote,
    navOptions: NavOptions? = null
) {
    val noteIdArg = noteId ?: ArgumentDefaultValues.NewNote

    navigate(
        route = "${Screen.NoteDetail.route}/$noteIdArg",
        navOptions = navOptions
    )
}

fun NavController.navigateToReminderEdit(
    noteId: Long? = ArgumentDefaultValues.NewNote,
    reminderId: Long? = ArgumentDefaultValues.NewReminder,
    navOptions: NavOptions? = null
) {
    val noteIdArg = noteId ?: ArgumentDefaultValues.NewNote
    val reminderIdArg = reminderId ?: ArgumentDefaultValues.NewReminder

    navigate(
        route = "${Screen.ReminderEdit.route}/$noteIdArg/$reminderIdArg",
        navOptions = navOptions
    )
}

fun NavController.navigateToLockSetup(
    lockType: LockScreenType,
    navOptions: NavOptions? = null
) {
    navigate(
        route = "${Screen.LockSetup.route}/${lockType.parse()}",
        navOptions = navOptions
    )
}

fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    navigate(
        route = Screen.Settings.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToAboutApp(navOptions: NavOptions? = null) {
    navigate(
        route = Screen.AboutApp.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(
        route = Screen.Home.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    navigate(
        route = Screen.Search.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToLabels(navOptions: NavOptions? = null) {
    navigate(
        route = Screen.LabelEdit.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToLock(navOptions: NavOptions? = null) {
    navigate(
        route = Screen.Lock.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToLockSelection(navOptions: NavOptions? = null) {
    navigate(
        route = Screen.LockSelection.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToLanguageSelection(navOptions: NavOptions? = null) {
    navigate(
        route = Screen.LanguageSelection.route,
        navOptions = navOptions
    )
}