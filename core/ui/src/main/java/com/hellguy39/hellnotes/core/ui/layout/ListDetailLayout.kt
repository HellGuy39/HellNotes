package com.hellguy39.hellnotes.core.ui.layout

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.FoldAwareConfiguration
import com.google.accompanist.adaptive.TwoPane
import com.google.accompanist.adaptive.TwoPaneStrategy
import com.hellguy39.hellnotes.core.ui.model.HNContentType

@Composable
fun ListDetail(
    modifier: Modifier = Modifier,
    isDetailOpen: Boolean,
    onCloseDetail: () -> Unit,
    contentType: HNContentType,
    detailKey: Any?,
    list: @Composable (isDetailVisible: Boolean) -> Unit,
    detail: @Composable (isListVisible: Boolean) -> Unit,
    twoPaneStrategy: TwoPaneStrategy,
    displayFeatures: List<DisplayFeature>,
) {
    val currentIsDetailOpen by rememberUpdatedState(isDetailOpen)
    val currentShowListAndDetail by rememberUpdatedState(contentType is HNContentType.DualPane)
    val currentDetailKey by rememberUpdatedState(detailKey)

    val showList by remember {
        derivedStateOf {
            currentShowListAndDetail || !currentIsDetailOpen
        }
    }
    val showDetail by remember {
        derivedStateOf {
            currentShowListAndDetail || currentIsDetailOpen
        }
    }

    check(showList || showDetail)

    val listSaveableStateHolder = rememberSaveableStateHolder()
    val detailSaveableStateHolder = rememberSaveableStateHolder()

    val start = remember {
        movableContentOf {
            listSaveableStateHolder.SaveableStateProvider(0) {
                Box(
                    modifier = Modifier
//                        .userInteractionNotification {
//                            setIsDetailOpen(false)
//                        }
                ) {
                    list(showDetail)
                }
            }
        }
    }

    val end = remember {
        movableContentOf {
            detailSaveableStateHolder.SaveableStateProvider(currentDetailKey ?: "null") {
                Box(
                    modifier = Modifier
//                        .userInteractionNotification {
//                            setIsDetailOpen(true)
//                        }
                ) {
                    detail(showList)
                }
            }

            if (!showList) {
                BackHandler {
                    onCloseDetail()
                    //setIsDetailOpen(false)
                }
            }
        }
    }

    Box(modifier = modifier) {
        if (showList && showDetail) {
            TwoPane(
                modifier = Modifier.fillMaxSize(),
                first = {
                    start()
                },
                second = {
                    end()
                },
                strategy = twoPaneStrategy,
                displayFeatures = displayFeatures,
                foldAwareConfiguration = FoldAwareConfiguration.VerticalFoldsOnly,
            )
        } else if (showList) {
            start()
        } else {
            end()
        }
    }
}

//fun Modifier.userInteractionNotification(onInteracted: () -> Unit): Modifier {
//    return pointerInput(onInteracted) {
//        val currentContext = currentCoroutineContext()
//        awaitPointerEventScope {
//            while (currentContext.isActive) {
//                val event = awaitPointerEvent(PointerEventPass.Initial)
//                if (
//                    event.type == PointerEventType.Press || event.type == PointerEventType.Scroll
//                ) {
//                    onInteracted.invoke()
//                }
//            }
//        }
//    }
//}