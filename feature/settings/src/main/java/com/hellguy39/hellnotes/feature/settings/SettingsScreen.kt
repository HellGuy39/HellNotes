package com.hellguy39.hellnotes.feature.settings

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SettingsScreen(
//    onNavigationButtonClick: () -> Unit,
//    uiState: SettingsUiState,
//    selection: SettingsScreenSelection,
//) {
//    BackHandler(onBack = onNavigationButtonClick)
//
//    val appBarState = rememberTopAppBarState()
//    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(appBarState)
//
//    Scaffold(
//        modifier = Modifier
//            .fillMaxSize()
//            .nestedScroll(scrollBehavior.nestedScrollConnection),
//        content = { innerPadding ->
//            SettingsScreenContent(
//                modifier = Modifier.fillMaxSize(),
//                innerPadding = innerPadding,
//                uiState = uiState,
//                selection = selection
//            )
//        },
//        topBar = {
//            HNLargeTopAppBar(
//                scrollBehavior = scrollBehavior,
//                onNavigationButtonClick = onNavigationButtonClick,
//                title = stringResource(id = HellNotesStrings.Title.Settings)
//            )
//        }
//    )
//}