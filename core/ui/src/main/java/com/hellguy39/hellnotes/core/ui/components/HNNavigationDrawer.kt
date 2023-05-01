package com.hellguy39.hellnotes.core.ui.components

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hellguy39.hellnotes.core.ui.WindowInfo
import com.hellguy39.hellnotes.core.ui.rememberWindowInfo

@Composable
fun HNNavigationDrawer(
    drawerState: DrawerState,
    drawerSheet: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    val windowInfo = rememberWindowInfo()

    HNModalNavigationDrawer(
        drawerState = drawerState,
        drawerSheet = drawerSheet,
        content = content
    )

//    when(windowInfo.screenWidthInfo) {
//        is WindowInfo.WindowType.Compact -> {
//            HNModalNavigationDrawer(
//                drawerState = drawerState,
//                drawerSheet = drawerSheet,
//                content = content
//            )
//        }
//        else -> {
//            HNPermanentNavigationDrawer(
//                drawerSheet = drawerSheet,
//                content = content
//            )
//        }
//    }

}

@Composable
private fun HNPermanentNavigationDrawer(
    drawerSheet: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    PermanentNavigationDrawer(
        modifier = Modifier,
        drawerContent = drawerSheet,
        content = content
    )
}

@Composable
private fun HNModalNavigationDrawer(
    drawerState: DrawerState,
    drawerSheet: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = drawerSheet,
        content = content
    )
}