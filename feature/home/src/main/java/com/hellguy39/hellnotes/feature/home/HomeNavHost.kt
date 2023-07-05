package com.hellguy39.hellnotes.feature.home

//@OptIn(ExperimentalAnimationApi::class)
//@Composable
//fun HomeNavHost(
//    homeViewModel: HomeViewModel = hiltViewModel()
//) {
//    val localNavController = rememberAnimatedNavController()
//
//    val navBackStackEntry by localNavController.currentBackStackEntryAsState()
//    val currentDestination = navBackStackEntry?.destination
//
//    val navItems = getHomeNavigationItems(
//        onItemClick = { item ->
//            localNavController.navigate(item.screen.route) {
//                popUpTo(localNavController.graph.findStartDestination().id)
//                launchSingleTop = true
//            }
//        }
//    )
//
//    val windowInfo = rememberWindowInfo()
//
//    val navHost = remember {
//        movableContentOf<PaddingValues> { innerPadding ->
//            AnimatedNavHost(
//                modifier = Modifier,
//                navController = localNavController,
//                startDestination = Screen.Startup.route
//            ) {
//                composable(Screen.NoteList.route) {
//
//                    val isExpandedWindowsSize = when (windowInfo.screenWidthInfo) {
//                        is WindowInfo.WindowType.Compact -> false
//                        else -> true
//                    }
//
//                    val noteListViewModel: NoteListViewModel = hiltViewModel()
//
//                    if (isExpandedWindowsSize) {
//
//                    } else {
//
//                    }
//
//                    Row(
//                        modifier = Modifier.padding(innerPadding)
//                    ) {
//
//                    }
//                }
//
//                composable(Screen.Reminders.route) {
//
//                }
//            }
//        }
//    }
//
//    when(windowInfo.screenWidthInfo) {
//        is WindowInfo.WindowType.Compact -> {
//            BottomNavigationBarLayout(
//                navItems = navItems,
//                currentDestination = currentDestination,
//                content = { innerPadding -> navHost(innerPadding) },
//                onNewNoteFabClick = {  }
//            )
//        }
//        is WindowInfo.WindowType.Medium -> {
//            NavigationRailLayout(
//                navItems = navItems,
//                currentDestination = currentDestination,
//                content = { navHost(PaddingValues()) },
//                onNewNoteFabClick = {  }
//            )
//        }
//        is WindowInfo.WindowType.Expanded -> {
//            NavigationDrawerLayout(
//                navItems = navItems,
//                currentDestination = currentDestination,
//                content = { navHost(PaddingValues()) },
//                onNewNoteFabClick = {  }
//            )
//        }
//    }
//}