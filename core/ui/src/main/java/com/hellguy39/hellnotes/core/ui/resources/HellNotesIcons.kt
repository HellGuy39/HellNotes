package com.hellguy39.hellnotes.core.ui.resources

import com.hellguy39.hellnotes.core.ui.R

object HellNotesIcons {

    val PinActivated = R.drawable.ic_push_pin_filled_24
    val PinDisabled = R.drawable.ic_push_pin_24

    fun pin(isPinned: Boolean): Int {
        return if (isPinned) PinActivated else PinDisabled
    }

    val Menu = R.drawable.ic_menu_24
    val Label = R.drawable.ic_label_24
    val Delete = R.drawable.ic_delete_24
    val NoteAdd = R.drawable.ic_note_add_24
    val Add = R.drawable.ic_add_24
    val Palette = R.drawable.ic_palette_24
    val MoreVert = R.drawable.ic_more_vert_24
    val MoreHoriz = R.drawable.ic_more_horiz_24
    val Settings = R.drawable.ic_settings_24
    val Info = R.drawable.ic_info_24
    val ArrowBack = R.drawable.ic_arrow_back_24
    val StickyNote = R.drawable.ic_sticky_note_24
    val Notifications = R.drawable.ic_notifications_24
    val Share = R.drawable.ic_share_24
    val Magic = R.drawable.ic_magic_24
    val Sort = R.drawable.ic_sort_24
    val Search = R.drawable.ic_search_24
    val Done = R.drawable.ic_done_24
    val Close = R.drawable.ic_close_24
    val Edit = R.drawable.ic_edit_24
    val Alarm = R.drawable.ic_alarm_24
    val Cancel = R.drawable.ic_cancel_24
    val NewLabel = R.drawable.ic_new_label_24
    val RestoreFromTrash = R.drawable.ic_restore_from_trash_24
    val DoubleStickyNote = R.drawable.ic_double_sticky_note_24
    val Gesture = R.drawable.ic_gesture_24
    val KeyboardTab = R.drawable.ic_keyboard_tab_24
    val Checklist = R.drawable.ic_checklist_24
    val DragHandle = R.drawable.ic_drag_handle_24
    val Error = R.drawable.ic_error_24
    val BugReport = R.drawable.ic_bug_report_24
    val RestartAlt = R.drawable.ic_restart_alt_24
    val Logout = R.drawable.ic_logout_24
    val ContentCopy = R.drawable.ic_content_copy_24
    val NotificationAdd = R.drawable.ic_notification_add_24
    val Attachment = R.drawable.ic_attachment_24
    val PinDrop = R.drawable.ic_pin_drop_24
    val Image = R.drawable.ic_image_24
    val Mic = R.drawable.ic_mic_24
    val PhotoCamera = R.drawable.ic_photo_camera_24
    val Save = R.drawable.ic_save_24
    val MenuOpen = R.drawable.ic_menu_open_24

    val Archive = R.drawable.ic_archive_24
    val Unarchive = R.drawable.ic_unarchive_24
    fun archive(isArchive: Boolean): Int {
        return if (isArchive) Archive else Unarchive
    }

    val Lock = R.drawable.ic_lock_24
    val LockOpen = R.drawable.ic_lock_open_24

    val Backspace = R.drawable.ic_backspace_24
    val Language = R.drawable.ic_language_24
    val Fingerprint = R.drawable.ic_fingerprint_24
    val SecurityVerified = R.drawable.ic_security_verified_24

    val GridView = R.drawable.ic_grid_view_24
    val ListView = R.drawable.ic_list_view_24

    val Pin = R.drawable.ic_pin_24
    val Password = R.drawable.ic_password_24
    val Pattern = R.drawable.ic_pattern_24
    val SwipeRight = R.drawable.ic_swipe_right_24
    val SwipeLeft = R.drawable.ic_swipe_left_24

    val Event = R.drawable.ic_event_24
    val Schedule = R.drawable.ic_schedule_24
    val Repeat = R.drawable.ic_repeat_24

    val CheckboxChecked = R.drawable.ic_checkbox_checked_24
    val CheckboxUnchecked = R.drawable.ic_checkbox_unchecked_24

    val ArrowOutward = R.drawable.ic_arrow_outward_24
    val ArrowDropDown = R.drawable.ic_arrow_drop_down_24
    val ArrowDropUp = R.drawable.ic_arrow_drop_up_24

    fun expand(isExpanded: Boolean): Int {
        return if (isExpanded) ExpandLess else ExpandMore
    }

    val ExpandMore = R.drawable.ic_expand_more_24
    val ExpandLess = R.drawable.ic_expand_less_24

    val DoneAll = R.drawable.ic_done_all_24
    val RemoveDone = R.drawable.ic_remove_done_24

    val GitHub = R.drawable.ic_github_24
}