package com.hellguy39.hellnotes.feature.home.edit

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.component.dialog.HNAdaptiveDialog
import com.hellguy39.hellnotes.core.ui.component.dialog.HNAlertDialog
import com.hellguy39.hellnotes.core.ui.component.snack.CustomSnackbarHost
import com.hellguy39.hellnotes.core.ui.model.HNContentType
import com.hellguy39.hellnotes.core.ui.resource.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resource.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.edit.components.AttachmentBottomSheet
import com.hellguy39.hellnotes.feature.home.edit.components.AttachmentBottomSheetSelection
import com.hellguy39.hellnotes.feature.home.edit.components.MenuBottomSheet
import com.hellguy39.hellnotes.feature.home.edit.components.MenuBottomSheetSelection

@Composable
fun NoteEditRoute(
    contentType: HNContentType,
    noteId: Long,
    onCloseNoteEdit: () -> Unit,
    noteEditViewModel: NoteEditViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = noteId) {
        noteEditViewModel.send(NoteEditUiEvent.OpenNote(noteId))
    }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val uiState by noteEditViewModel.uiState.collectAsStateWithLifecycle()
//
//    val shareDialogState = rememberDialogState()
//    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
//    val colorPickerState = rememberSheetState()

//    val templateColors = MultipleColors.ColorsInt(
//        Color.Red.toArgb(),
//        Color.Green.toArgb(),
//        Color.Yellow.toArgb(),
//    )

//    ColorDialog(
//        state = colorPickerState,
//        selection = ColorSelection(
//            selectedColor = null,
//            onSelectColor = {  },
//        ),
//        config = ColorConfig(
//            templateColors = templateColors,
//            defaultDisplayMode = ColorSelectionMode.CUSTOM,
//            allowCustomColorAlphaValues = false
//        ),
//    )
//
//    fun onShare(type: ShareType) {
//        uiState.let { state ->
//            if (state is NoteDetailUiState.Success) {
//                ShareUtils.share(
//                    context = context,
//                    note = state.wrapper.note,
//                    type = type
//                )
//            }
//        }
//    }

    BackHandler(onBack = onCloseNoteEdit)

//    CustomDialog(
//        state = shareDialogState,
//        heroIcon = painterResource(id = HellNotesIcons.Share),
//        title = stringResource(id = HellNotesStrings.Title.Share),
//        message = stringResource(id = HellNotesStrings.Supporting.ShareNote),
//        onCancel = { shareDialogState.dismiss() },
//        content = {
//            val listItemModifier = Modifier.padding(16.dp)
//            Spacer(modifier = Modifier.height(8.dp))
//            HNListItem(
//                modifier = listItemModifier,
//                title = stringResource(id = HellNotesStrings.MenuItem.TxtFile),
//                onClick = {
//                    shareDialogState.dismiss()
//                    onShare(ShareType.TxtFile)
//                },
//            )
//            HNListItem(
//                modifier = listItemModifier,
//                title = stringResource(id = HellNotesStrings.MenuItem.PlainText),
//                onClick = {
//                    shareDialogState.dismiss()
//                    onShare(ShareType.PlainText)
//                },
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//        }
//    )
//

    HNAdaptiveDialog(
        isOpen = uiState.noteEditDialogState.reminderEditDialogState.isOpen,
        onClose = {},
    ) {
        Text("Hello")
    }

    HNAlertDialog(
        isOpen = uiState.noteEditDialogState.isDeleteAlertDialogOpen,
        heroIcon = painterResource(id = HellNotesIcons.Delete),
        title = stringResource(id = HellNotesStrings.Title.DeleteThisNote),
        message = stringResource(id = HellNotesStrings.Supporting.DeleteNote),
        onClose = { noteEditViewModel.send(NoteEditUiEvent.OpenDeleteAlertDialog(false)) },
        onCancel = { noteEditViewModel.send(NoteEditUiEvent.OpenDeleteAlertDialog(false)) },
        onAccept = {
            noteEditViewModel.send(NoteEditUiEvent.OpenDeleteAlertDialog(false))
            noteEditViewModel.send(NoteEditUiEvent.DeleteNote)
            onCloseNoteEdit()
        }
    )

    val toast = remember {
        Toast.makeText(context, context.getString(HellNotesStrings.Toast.ComingSoon), Toast.LENGTH_SHORT)
    }

    AttachmentBottomSheet(
        uiState = uiState,
        onDismiss = { noteEditViewModel.send(NoteEditUiEvent.OpenAttachmentBottomSheet(false)) },
        selection = AttachmentBottomSheetSelection(
            onTakeAPhoto = { toast.show() },
            onAddImage = { toast.show() },
            onAddRecording = { toast.show() },
            onAddPlace = { toast.show() },
            onAddChecklist = { noteEditViewModel.send(NoteEditUiEvent.AddChecklist) },
            onAddLabel = { noteEditViewModel.send(NoteEditUiEvent.AddLabel)
//            uiState.let { state ->
//                if (state is NoteDetailUiState.Success) {
//                    TODO()
//                    //navController.navigateToLabelSelection(state.wrapper.note.id)
//                }
//            }
            },
        )
    )

    MenuBottomSheet(
        uiState = uiState,
        onDismiss = { noteEditViewModel.send(NoteEditUiEvent.OpenMenuBottomSheet(false)) },
        selection = MenuBottomSheetSelection(
            onShare = {
                noteEditViewModel.send(NoteEditUiEvent.Share)
//                uiState.let { state ->
//                    if (state is NoteDetailUiState.Success) {
//                        if (state.wrapper.note.isNoteValid()) {
//                            shareDialogState.show()
//                        } else {
//                            snackbarHostState.showDismissableSnackbar(
//                                scope = scope,
//                                message = context.getString(HellNotesStrings.Snack.NothingToShare),
//                                duration = SnackbarDuration.Short
//                            )
//                        }
//                    }
//                }
            },
            onDelete = {
                noteEditViewModel.send(NoteEditUiEvent.OpenDeleteAlertDialog(true))
            },
            onMakeACopy = {
                noteEditViewModel.send(NoteEditUiEvent.MakeACopy)
//                noteDetailViewModel.send(NoteDetailUiEvent.CopyNote(
//                    onCopied = { id ->
//                        TODO()
////                        navController.popBackStack()
////                        navController.navigateToNoteDetail(id)
//                    }
//                ))
            },
            onColor = {
                noteEditViewModel.send(NoteEditUiEvent.OpenColorDialog(true))
                //colorPickerState.show()
            }
        )
    )

    NoteEditScreen(
        noteEditViewModel = noteEditViewModel,
        contentType = contentType,
        snackbarHost = { CustomSnackbarHost(state = snackbarHostState) },
        uiState = uiState,
        onCloseNoteEdit = onCloseNoteEdit
    )
}