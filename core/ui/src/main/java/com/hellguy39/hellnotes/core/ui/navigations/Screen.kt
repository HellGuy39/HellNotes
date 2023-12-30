package com.hellguy39.hellnotes.core.ui.navigations

import com.hellguy39.hellnotes.core.common.arguments.Arguments

sealed class Screen(val route: String) {
    data object Startup : Screen(route = "startup")

    data object OnBoarding : Screen(route = "on_boarding")

    data object Lock : Screen(route = "lock")

    data object LockSetup : Screen(route = "lock_setup")

    data object LanguageSelection : Screen(route = "language_selection")

    data object LockSelection : Screen(route = "lock_selection")

    data object Home : Screen(route = "home")

    data object Notes : Screen(route = "notes")

    data object Reminders : Screen(route = "reminders")

    data object Label : Screen(route = "label")

    data object Archive : Screen(route = "archive")

    data object Trash : Screen(route = "trash")

    data object Settings : Screen(route = "settings")

    data object NoteDetail : Screen(route = "note_detail")

    data object ReminderEdit : Screen(route = "reminder_edit")

    data object Search : Screen(route = "search")

    data object AboutApp : Screen(route = "about_app")

    data object LabelEdit : Screen(route = "label_edit")

    data object LabelSelection : Screen(route = "label_selection")

    data object NoteStyleEdit : Screen(route = "note_style_edit")

    data object NoteSwipeEdit : Screen(route = "note_swipe_edit")

    data object Changelog : Screen(route = "changelog")

    data object Reset : Screen(route = "reset")

    data object PrivacyPolicy : Screen(route = "privacy_policy")

    data object TermsAndConditions : Screen(route = "terms_and_conditions")

    data object Update : Screen(route = "update")

    data object Backup : Screen(route = "backup")

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

    fun withArgs(vararg args: Arguments<*>): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/${arg.key}")
            }
        }
    }

    fun withArgKeys(vararg args: Arguments<*>): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/{${arg.key}}")
            }
        }
    }
}
