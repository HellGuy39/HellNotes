package com.hellguy39.hellnotes.feature.settings.components

//@Composable
//fun LanguageDialog(
//    isShowDialog: Boolean,
//    //events: LanguageDialogEvents
//) {
//    CustomDialog(
//        showDialog = isShowDialog,
//        onClose = {
//            // events.dismiss()
//                  },
//    ) { innerPadding ->
//        LazyColumn(
//            modifier = Modifier
//                .padding(innerPadding),
//        ) {
//            items(com.hellguy39.hellnotes.feature.language_selection.Language.languageCodes) {
//                Row(
//                    modifier = Modifier
//                        .clickable {
//                            //events.dismiss()
//                            //events.onLanguageSelected(it)
//                        },
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    val isSelected = false//it == events.getCurrentLanCode()
//
//                    Spacer(modifier = Modifier.width(16.dp))
//
//                    if (isSelected) {
//                        Icon(
//                            painter = painterResource(id = HellNotesIcons.Done),
//                            contentDescription = null
//                        )
//                    } else {
//                        Spacer(modifier = Modifier.width(24.dp))
//                    }
//                    Text(
//                        text = com.hellguy39.hellnotes.feature.language_selection.Language.getFullName(code = it),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp),
//                        style = MaterialTheme.typography.bodyMedium,
//                    )
//                }
//            }
//        }
//    }
//}
//
