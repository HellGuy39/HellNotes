package com.hellguy39.hellnotes.feature.home.edit

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.component.snack.CustomSnackbarHost
import com.hellguy39.hellnotes.core.ui.model.HNContentType

@Composable
fun NoteEditRoute(
    contentType: HNContentType,
    noteId: Long,
    onCloseNoteEdit: () -> Unit,
    noteEditViewModel: NoteEditViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = noteId) {
        noteEditViewModel.send(NoteDetailUiEvent.OpenNote(noteId))
    }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val uiState by noteEditViewModel.uiState.collectAsStateWithLifecycle()
//
//    val shareDialogState = rememberDialogState()
//    val confirmDialogState = rememberDialogState()
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

    //BackHandler(onBack = navController::popBackStack)

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
//    CustomDialog(
//        state = confirmDialogState,
//        heroIcon = painterResource(id = HellNotesIcons.Delete),
//        title = stringResource(id = HellNotesStrings.Title.DeleteThisNote),
//        message = stringResource(id = HellNotesStrings.Supporting.DeleteNote),
//        onCancel = { confirmDialogState.dismiss() },
//        onAccept = {
//            confirmDialogState.dismiss()
//            noteDetailViewModel.send(NoteDetailUiEvent.DeleteNote)
//            TODO()
//            //navController.popBackStack()
//        }
//    )

//    val currentOnStop by rememberUpdatedState {
//        noteDetailViewModel.send(NoteDetailUiEvent.Minimize)
//    }
//
//    val currentOnDestroy by rememberUpdatedState {
//        noteDetailViewModel.send(NoteDetailUiEvent.CloseNote)
//    }
//
//    DisposableEffect(lifecycleOwner) {
//        val observer = LifecycleEventObserver { _, event ->
//            when(event) {
//                Lifecycle.Event.ON_STOP -> currentOnStop()
//                Lifecycle.Event.ON_DESTROY -> currentOnDestroy()
//                else -> Unit
//            }
//        }
//
//        lifecycleOwner.lifecycle.addObserver(observer)
//
//        onDispose {
//            lifecycleOwner.lifecycle.removeObserver(observer)
//        }
//    }

//    var isOpenMenuBottomSheet by rememberSaveable { mutableStateOf(false) }
//    val attachmentBottomSheetState = rememberModalBottomSheetState(
//        skipPartiallyExpanded = false
//    )
//
//    var isOpenAttachmentBottomSheet by rememberSaveable { mutableStateOf(false) }
//    val menuBottomSheetState = rememberModalBottomSheetState(
//        skipPartiallyExpanded = false
//    )

//    fun closeMenuBottomSheet() {
//        scope.launch {
//            menuBottomSheetState.hide()
//        }.invokeOnCompletion {
//            if (!menuBottomSheetState.isVisible) {
//                isOpenMenuBottomSheet = false
//            }
//        }
//    }

//    fun closeAttachmentBottomSheet() {
//        scope.launch {
//            attachmentBottomSheetState.hide()
//        }.invokeOnCompletion {
//            if (!attachmentBottomSheetState.isVisible) {
//                isOpenAttachmentBottomSheet = false
//            }
//        }
//    }
//
//    val toast = Toast.makeText(context, context.getString(HellNotesStrings.Toast.ComingSoon), Toast.LENGTH_SHORT)
//
//    val menuBottomSheetItems = listOf(
//        BottomSheetMenuItemHolder(
//            title = stringResource(id = HellNotesStrings.MenuItem.Delete),
//            icon = painterResource(id = HellNotesIcons.Delete),
//            onClick = {
//                closeMenuBottomSheet()
//                confirmDialogState.show()
//            }
//        ),
//        BottomSheetMenuItemHolder(
//            title = stringResource(id = HellNotesStrings.MenuItem.MakeACopy),
//            icon = painterResource(id = HellNotesIcons.ContentCopy),
//            onClick = {
//                closeMenuBottomSheet()
//                noteDetailViewModel.send(NoteDetailUiEvent.CopyNote(
//                    onCopied = { id ->
//                        TODO()
////                        navController.popBackStack()
////                        navController.navigateToNoteDetail(id)
//                    }
//                ))
//            }
//        ),
//        BottomSheetMenuItemHolder(
//            title = stringResource(id = HellNotesStrings.MenuItem.Share),
//            icon = painterResource(id = HellNotesIcons.Share),
//            onClick = {
//                closeMenuBottomSheet()
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
//            }
//        ),
//        BottomSheetMenuItemHolder(
//            title = "Color",
//            icon = painterResource(id = HellNotesIcons.Palette),
//            onClick = {
//                closeMenuBottomSheet()
//                colorPickerState.show()
//            }
//        )
//    )
//
//    val attachmentSheetItems = listOf(
//        BottomSheetMenuItemHolder(
//            title = stringResource(id = HellNotesStrings.MenuItem.TakeAPhoto),
//            icon = painterResource(id = HellNotesIcons.PhotoCamera),
//            onClick = {
//                closeAttachmentBottomSheet()
//                toast.show()
//            }
//        ),
//        BottomSheetMenuItemHolder(
//            title = stringResource(id = HellNotesStrings.MenuItem.Image),
//            icon = painterResource(id = HellNotesIcons.Image),
//            onClick = {
//                closeAttachmentBottomSheet()
//                toast.show()
//            }
//        ),
//        BottomSheetMenuItemHolder(
//            title = stringResource(id = HellNotesStrings.MenuItem.Recording),
//            icon = painterResource(id = HellNotesIcons.Mic),
//            onClick = {
//                closeAttachmentBottomSheet()
//                toast.show()
//            }
//        ),
//        BottomSheetMenuItemHolder(
//            title = stringResource(id = HellNotesStrings.MenuItem.Place),
//            icon = painterResource(id = HellNotesIcons.PinDrop),
//            onClick = {
//                closeAttachmentBottomSheet()
//                toast.show()
//            }
//        ),
//        BottomSheetMenuItemHolder(
//            title = stringResource(id = HellNotesStrings.MenuItem.Checklist),
//            icon = painterResource(id = HellNotesIcons.Checklist),
//            onClick = {
//                closeAttachmentBottomSheet()
//                noteDetailViewModel.send(NoteDetailUiEvent.AddChecklist)
//            }
//        ),
//        BottomSheetMenuItemHolder(
//            title = stringResource(id = HellNotesStrings.MenuItem.Labels),
//            icon = painterResource(id = HellNotesIcons.Label),
//            onClick = {
//                closeAttachmentBottomSheet()
//                uiState.let { state ->
//                    if (state is NoteDetailUiState.Success) {
//                        TODO()
//                        //navController.navigateToLabelSelection(state.wrapper.note.id)
//                    }
//                }
//            }
//        )
//    )

//    if (isOpenMenuBottomSheet) {
//        ModalBottomSheet(
//            modifier = Modifier,
//            onDismissRequest = { isOpenMenuBottomSheet = false },
//            sheetState = menuBottomSheetState,
//        ) {
//            menuBottomSheetItems.forEach { item ->
//                HNListItem(
//                    modifier = listItemModifier,
//                    title = item.title,
//                    iconSize = 24.dp,
//                    heroIcon = item.icon,
//                    onClick = item.onClick
//                )
//            }
//        }
//    }

//    if (isOpenAttachmentBottomSheet) {
//        ModalBottomSheet(
//            modifier = Modifier,
//            onDismissRequest = { isOpenAttachmentBottomSheet = false },
//            sheetState = attachmentBottomSheetState,
//        ) {
//            attachmentSheetItems.forEach { item ->
//                HNListItem(
//                    modifier = listItemModifier,
//                    title = item.title,
//                    iconSize = 24.dp,
//                    heroIcon = item.icon,
//                    onClick = item.onClick
//                )
//            }
//        }
//    }

    NoteEditScreen(
        noteEditViewModel = noteEditViewModel,
        contentType = contentType,
        snackbarHost = { CustomSnackbarHost(state = snackbarHostState) },
        uiState = uiState,
        onCloseNoteEdit = onCloseNoteEdit
    )
}