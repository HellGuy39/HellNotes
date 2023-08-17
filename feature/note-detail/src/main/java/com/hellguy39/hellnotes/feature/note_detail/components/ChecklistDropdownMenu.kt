package com.hellguy39.hellnotes.feature.note_detail.components
//
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import com.hellguy39.hellnotes.core.ui.component.menu.CustomDropdownItemSelection
//import com.hellguy39.hellnotes.core.ui.component.menu.CustomDropdownMenu
//import com.hellguy39.hellnotes.core.ui.component.menu.CustomDropdownMenuState
//import com.hellguy39.hellnotes.core.ui.resource.HellNotesIcons
//import com.hellguy39.hellnotes.core.ui.resource.HellNotesStrings
//
//@Composable
//fun ChecklistDropdownMenu(
//    state: CustomDropdownMenuState,
//    selection: ChecklistDropdownMenuSelection,
//) {
//    CustomDropdownMenu(
//        expanded = state.visible,
//        onDismissRequest = { state.dismiss() },
//        items = listOf(
//            CustomDropdownItemSelection(
//                leadingIconId = painterResource(id = HellNotesIcons.DoneAll),
//                text = stringResource(id = HellNotesStrings.MenuItem.CheckAllItems),
//                onClick = {
//                    state.dismiss()
//                    selection.onDoneAllItems()
//                }
//            ),
//            CustomDropdownItemSelection(
//                leadingIconId = painterResource(id = HellNotesIcons.RemoveDone),
//                text = stringResource(id = HellNotesStrings.MenuItem.UncheckAllItems),
//                onClick = {
//                    state.dismiss()
//                    selection.onRemoveDoneItems()
//                }
//            ),
//            CustomDropdownItemSelection(
//                leadingIconId = painterResource(id = HellNotesIcons.Delete),
//                text = stringResource(id = HellNotesStrings.MenuItem.Delete),
//                onClick = {
//                    state.dismiss()
//                    selection.onDeleteChecklist()
//                }
//            )
//        )
//    )
//}
//
//data class ChecklistDropdownMenuSelection(
//    val onDoneAllItems: () -> Unit,
//    val onRemoveDoneItems: () -> Unit,
//    val onDeleteChecklist: () -> Unit
//)