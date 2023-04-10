package com.hellguy39.hellnotes.core.ui.navigations

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.hellguy39.hellnotes.core.model.util.LockRequest
import com.hellguy39.hellnotes.core.model.util.LockScreenType

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
    object NoteStyleEdit : Screen(route = "note_style_edit")
    object NoteSwipeEdit : Screen(route = "note_swipe_edit")

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
    const val ChecklistId = "checklist_id"
    const val ReminderId = "reminder_id"
    const val LockType = "lock_type"
    const val LockRequest = "lock_request"
    const val Action = "action"
}

object ArgumentDefaultValues {
    const val Empty: Long = -2
    const val NewNote: Long = -1
    const val NewReminder: Long = -1
    const val NewChecklist: Long = -1
}

fun NavController.navigateToOnBoarding(
    navOptions: NavOptions? = null
) {
    navigate(
        route = Screen.OnBoarding.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToNoteDetail(
    noteId: Long? = ArgumentDefaultValues.NewNote,
    navOptions: NavOptions? = null
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
    navOptions: NavOptions? = null
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
    navOptions: NavOptions? = null
) {
    val noteIdArg = noteId ?: ArgumentDefaultValues.NewNote

    navigate(
        route = Screen.LabelSelection.withArgs(noteIdArg.toString()),
        navOptions = navOptions
    )
}

fun NavController.navigateToLockSetup(
    lockType: LockScreenType,
    navOptions: NavOptions? = null
) {
    navigate(
        route = Screen.LockSetup.withArgs(lockType.string()),
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

fun NavController.navigateToLabelEdit(
    navOptions: NavOptions? = null,
    action: String
) {
    navigate(
        route = Screen.LabelEdit.withArgs(action),
        navOptions = navOptions
    )
}

fun NavController.navigateToLock(
    navOptions: NavOptions? = null
) {
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

fun NavController.navigateToNoteStyleEdit(navOptions: NavOptions? = null) {
    navigate(
        route = Screen.NoteStyleEdit.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToNoteSwipeEdit(navOptions: NavOptions? = null) {
    navigate(
        route = Screen.NoteSwipeEdit.route,
        navOptions = navOptions
    )
}
