package com.hellguy39.hellnotes.feature.note_detail.navigations
//
//import androidx.navigation.*
//import androidx.navigation.compose.composable
//import com.hellguy39.hellnotes.core.ui.model.ArgumentDefaultValues
//import com.hellguy39.hellnotes.core.ui.model.ArgumentKeys
//import com.hellguy39.hellnotes.core.ui.model.Screen
//
//fun NavGraphBuilder.noteDetailScreen(
//    navController: NavController
//) {
//    composable(
//        route = Screen.NoteDetail.withArgKeys(ArgumentKeys.NoteId),
//        arguments = listOf(
//            navArgument(name = ArgumentKeys.NoteId) {
//                type = NavType.LongType
//                defaultValue = ArgumentDefaultValues.NewNote
//            }
//        )
//    ) {
//        //NoteDetailRoute(navController)
//    }
//}