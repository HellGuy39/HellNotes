package com.hellguy39.hellnotes.feature.onboarding

import androidx.compose.runtime.Composable
import com.hellguy39.hellnotes.core.ui.components.dialog.FullScreenDialog

@Composable
fun OnBoardingFullScreenDialog(
    isShowDialog: Boolean,
    onFinish: () -> Unit,
) {
    FullScreenDialog(
        isShowingDialog = isShowDialog,
        dismissOnBackPress = true,
        onDismissRequest = { onFinish() },
    ) {
        OnBoardingRoute(onFinish)
    }
}
