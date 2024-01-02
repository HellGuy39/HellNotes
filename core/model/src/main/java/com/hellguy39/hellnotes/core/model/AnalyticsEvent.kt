package com.hellguy39.hellnotes.core.model

data class AnalyticsEvent(
    val type: String,
    val extras: List<Param> = emptyList(),
) {
    data class Param(val key: String, val value: String)

    object Types {
        const val SCREEN_VIEW = "screen_view"
        const val SELECT_ITEM = "select_item"
        const val BUTTON_CLICK = "button_click"
    }

    object ParamKeys {
        const val SCREEN_NAME = "screen_name"
        const val BUTTON_ID = "button_id"
        const val ITEM_ID = "item_id"
        const val ITEM_NAME = "item_name"
    }

    override fun toString(): String {
        return "AnalyticsEvent(" +
                "type: $type, " +
                "params: [${extras.joinToString(",")}]" +
                ")"
    }
}