package com.hellguy39.hellnotes.feature.settings.components

//@Composable
//fun SettingsScreenContent(
//    modifier: Modifier = Modifier,
//    innerPadding: PaddingValues,
//    selection: SettingsScreenSelection,
//    uiState: SettingsUiState
//) {
//    val listItemModifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
//
//    LazyColumn(
//        contentPadding = innerPadding,
//        modifier = modifier
//    ) {
//        item {
//            Column(
//                modifier = Modifier
//                    .padding(vertical = 8.dp)
//            ) {
//                HNListHeader(
//                    modifier = Modifier
//                        .padding(vertical = 8.dp, horizontal = 16.dp),
//                    title = stringResource(id = HellNotesStrings.Label.General),
//                    icon = painterResource(id = HellNotesIcons.Settings)
//                )
//                HNListItem(
//                    modifier = listItemModifier,
//                    onClick = selection.onLanguage,
//                    title = stringResource(id = HellNotesStrings.Setting.Language),
//                    subtitle =  Language.from(uiState.lanCode).getDisplayName(),
//                )
//
//                HNListItem(
//                    modifier = listItemModifier,
//                    onClick = selection.onBackup,
//                    title = stringResource(id = HellNotesStrings.MenuItem.Backup),
//                    subtitle = stringResource(
//                        id = HellNotesStrings.Subtitle.LastCopy,
//                        if (uiState.lastBackupDate == 0L) stringResource(id = HellNotesStrings.Value.Never) else DateTimeUtils.formatBest(uiState.lastBackupDate)
//                    )
//                )
//            }
//        }
//        item {
//            Column(
//                modifier = Modifier
//                    .padding(vertical = 8.dp)
//            ) {
//                HNListHeader(
//                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
//                    title = stringResource(id = HellNotesStrings.Label.Security),
//                    icon = painterResource(id = HellNotesIcons.SecurityVerified)
//                )
//
//                HNListItem(
//                    modifier = listItemModifier,
//                    onClick = selection.onLockScreen,
//                    title = stringResource(id = HellNotesStrings.Setting.ScreenLock),
//                    subtitle = uiState.securityState.lockType.getDisplayName(),
//                )
//
//                val isChecked = uiState.securityState.isUseBiometricData
//
//                HNSwitchItem(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 16.dp, horizontal = 16.dp),
//                    title = stringResource(id = HellNotesStrings.Switch.UseBiometric),
//                    checked = isChecked,
//                    enabled = uiState.isBioAuthAvailable,
//                    onClick = { selection.onUseBiometric(!isChecked) },
//                )
//            }
//        }
//        item {
//            Column(
//                modifier = Modifier
//                    .padding(vertical = 8.dp)
//            ) {
//                HNListHeader(
//                    modifier = Modifier
//                        .padding(vertical = 8.dp, horizontal = 16.dp),
//                    title = stringResource(id = HellNotesStrings.Label.Gestures),
//                    icon = painterResource(id = HellNotesIcons.Gesture)
//                )
//                HNListItem(
//                    modifier = listItemModifier,
//                    onClick = selection.onNoteSwipeEdit,
//                    title = stringResource(id = HellNotesStrings.Setting.NoteSwipes),
//                    subtitle = if (uiState.noteSwipesState.enabled)
//                        stringResource(id = HellNotesStrings.Subtitle.Enabled)
//                    else
//                        stringResource(id = HellNotesStrings.Subtitle.Disabled),
//                )
//            }
//        }
//        item {
//            Column(
//                modifier = Modifier
//                    .padding(vertical = 8.dp)
//            ) {
//                HNListHeader(
//                    modifier = Modifier
//                        .padding(vertical = 8.dp, horizontal = 16.dp),
//                    title = stringResource(id = HellNotesStrings.Label.Appearance),
//                    icon = painterResource(id = HellNotesIcons.Palette)
//                )
//
//
//                val isChecked = true
//
//                HNSwitchItem(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 16.dp, horizontal = 16.dp),
//                    title = "Material You",
//                    checked = isChecked,
//                    enabled = true,
//                    onClick = {  },
//                )
//
//                HNListItem(
//                    modifier = listItemModifier,
//                    onClick = selection.onNoteStyleEdit,
//                    title = stringResource(id = HellNotesStrings.Setting.NoteStyle),
//                    subtitle = uiState.noteStyle.getDisplayName(),
//                )
//            }
//        }
//    }
//}
//
//data class SettingsScreenSelection(
//    val onNoteSwipeEdit: () -> Unit,
//    val onNoteStyleEdit: () -> Unit,
//    val onLockScreen: () -> Unit,
//    val onLanguage: () -> Unit,
//    val onBackup: () -> Unit,
//    val onUseBiometric: (Boolean) -> Unit,
//    val onThemeToggle: (ThemeState) -> Unit,
//    val onMaterialYouEnabled: (Boolean) -> Unit
//)