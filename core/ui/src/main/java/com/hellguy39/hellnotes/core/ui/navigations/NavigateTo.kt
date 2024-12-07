/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hellguy39.hellnotes.core.ui.navigations

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.ui.state.GraphState
import com.hellguy39.hellnotes.core.ui.state.lifecycleIsResumed

fun defaultNavOptions() = navOptions { launchSingleTop = true }

fun GraphState.navigateTo(
    from: NavBackStackEntry,
    route: String,
    navOptions: NavOptions = defaultNavOptions(),
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = route,
            navOptions = navOptions,
        )
    }
}

fun GraphState.navigateToNoteDetail(
    from: NavBackStackEntry,
    noteId: Long? = Arguments.NoteId.emptyValue,
    navOptions: NavOptions = defaultNavOptions(),
) {
    val noteIdArg = noteId ?: Arguments.NoteId.emptyValue
    navigateTo(from, Screen.NoteDetail.withArgs(noteIdArg.toString()), navOptions)
}

fun GraphState.navigateToReminderEdit(
    from: NavBackStackEntry,
    noteId: Long? = Arguments.NoteId.emptyValue,
    reminderId: Long? = Arguments.ReminderId.emptyValue,
    navOptions: NavOptions = defaultNavOptions(),
) {
    val noteIdArg = noteId ?: Arguments.NoteId.emptyValue
    val reminderIdArg = reminderId ?: Arguments.ReminderId.emptyValue

    navigateTo(from, Screen.ReminderEdit.withArgs(noteIdArg.toString(), reminderIdArg.toString()), navOptions)
}

fun GraphState.navigateToLabelSelection(
    from: NavBackStackEntry,
    noteId: Long? = Arguments.NoteId.emptyValue,
    navOptions: NavOptions = defaultNavOptions(),
) {
    val noteIdArg = noteId ?: Arguments.NoteId.emptyValue

    navigateTo(from, Screen.LabelSelection.withArgs(noteIdArg.toString()), navOptions)
}

fun GraphState.navigateToSearch(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions(),
) {
    navigateTo(from, Screen.Search.route, navOptions)
}

fun GraphState.navigateToLabelEdit(
    from: NavBackStackEntry,
    action: String,
    navOptions: NavOptions = defaultNavOptions(),
) {
    navigateTo(from, Screen.LabelEdit.withArgs(action), navOptions)
}
