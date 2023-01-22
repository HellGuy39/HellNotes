package com.hellguy39.hellnotes.feature.home.note_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

//@Composable
//fun ListConfiguration(
//    sortMenuEvents: SortMenuEvents,
//    isShowSortMenu: Boolean,
//    //onListStyleChange: () -> Unit
//) {
////    val sortName = when(uiState.sorting) {
////        is Sorting.DateOfLastEdit -> stringResource(id = HellNotesStrings.Text.DateOfLastEdit)
////        is Sorting.DateOfCreation -> stringResource(id = HellNotesStrings.Text.DateOfCreation)
////    }
////
//////    val listStyleIcon = if(uiState.listStyle == ListStyle.Column)
//////        painterResource(id = HellNotesIcons.GridView)
//////    else
//////        painterResource(id = HellNotesIcons.ListView)
////
////    Row(
////        modifier = Modifier.fillMaxWidth(),
////        //horizontalArrangement = Arrangement.SpaceBetween
////    ) {
////        TextButton(
////            onClick = { sortMenuEvents.show() },
////        ) {
////
////            Icon(
////                painter = painterResource(id = HellNotesIcons.Sort),
////                contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Sort)
////            )
////            Text(
////                text = stringResource(id = HellNotesStrings.Text.SortBy, sortName),
////                modifier = Modifier
////                    .padding(horizontal = 4.dp),
////                style = MaterialTheme.typography.bodyMedium
////            )
////            SortDropdownMenu(
////                expanded = isShowSortMenu,
////                events = sortMenuEvents,
////                currentSorting = uiState.sorting
////            )
////
////        }
////        Spacer(Modifier.weight(1f))
//////        IconButton(
//////            onClick = { onListStyleChange() }
//////        ) {
//////            Icon(
//////                painter = listStyleIcon,
//////                contentDescription = stringResource(id = HellNotesStrings.ContentDescription.ViewType)
//////            )
//////        }
////    }
//}