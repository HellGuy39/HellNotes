package com.hellguy39.hellnotes.core.ui.model

sealed class HNContentType {

    object SinglePane : HNContentType()

    object DualPane : HNContentType()
}