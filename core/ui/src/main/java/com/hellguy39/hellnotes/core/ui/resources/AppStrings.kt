/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hellguy39.hellnotes.core.ui.resources

import com.hellguy39.hellnotes.core.ui.R

object AppStrings {
    val AppName = R.string.app_name

    object Chip {
        val Markdown = R.string.markdown
        val ReadOnly = R.string.read_only
    }

    object ContentDescription {
        val Pin = R.string.content_description_pin
        val Back = R.string.content_description_back
        val Labels = R.string.content_description_labels
        val Reminder = R.string.content_description_reminder
        val Delete = R.string.content_description_delete
        val AddNote = R.string.content_description_add_note
        val Color = R.string.content_description_color
        val ViewType = R.string.content_description_view_type
        val More = R.string.content_description_more
        val Sort = R.string.content_description_sort
        val Search = R.string.content_description_search
        val Close = R.string.content_description_close
        val Cancel = R.string.content_description_cancel
        val Edit = R.string.content_description_edit
    }

    object Hint {
        val Title = R.string.hint_title
        val Note = R.string.hint_note
        val Search = R.string.hint_search
        val Label = R.string.hint_label
        val Message = R.string.hint_message
        val CreateNewLabel = R.string.hint_create_new_label
        val AddNewItem = R.string.hint_add_new_item
        val Item = R.string.hint_item
        val NewChecklist = R.string.hint_new_checklist
    }

    object MenuItem {
        val Reminders = R.string.menu_item_reminders
        val Settings = R.string.menu_item_settings
        val AboutApp = R.string.menu_item_about_app
        val ManageLabels = R.string.menu_item_manage_labels

        val Labels = R.string.menu_item_labels
        val Rename = R.string.menu_item_rename
        val Delete = R.string.menu_item_delete
        val DeleteForever = R.string.menu_item_delete_forever
        val Restore = R.string.menu_item_restore
        val PlainText = R.string.menu_item_plain_text
        val TxtFile = R.string.menu_item_txt_file
        val Share = R.string.menu_item_share
        val Color = R.string.menu_item_color
        val WhatsNew = R.string.menu_item_whats_new
        val CreateNewLabel = R.string.menu_item_create_new_label
        val EmptyTrash = R.string.menu_item_empty_trash
        val TakeAPhoto = R.string.menu_item_take_a_photo
        val Image = R.string.menu_item_image
        val Recording = R.string.menu_item_recording
        val Place = R.string.menu_item_place
        val Checklist = R.string.menu_item_checklist

        val None = R.string.menu_item_none
        val Pin = R.string.menu_item_pin
        val Password = R.string.menu_item_password
        val Pattern = R.string.menu_item_pattern
        val Slide = R.string.menu_item_slide

        val DoesNoteRepeat = R.string.menu_item_does_not_repeat
        val Daily = R.string.menu_item_daily
        val Weekly = R.string.menu_item_weekly
        val Monthly = R.string.menu_item_monthly

        val Outlined = R.string.menu_item_outlined
        val Elevated = R.string.menu_item_elevated

        val Archive = R.string.menu_item_archive
        val CheckAllItems = R.string.menu_item_check_all_items
        val UncheckAllItems = R.string.menu_item_uncheck_all_items

        val Changelog = R.string.menu_item_changelog
        val LicenseAgreement = R.string.menu_item_license_agreement
        val PrivacyPolicy = R.string.menu_item_privacy_policy
        val TermsAndConditions = R.string.menu_item_terms_and_conditions
        val ProvideFeedback = R.string.menu_item_provide_feedback
        val RateOnPlayStore = R.string.menu_item_rate_on_play_store
        val Reset = R.string.menu_item_reset
        val CheckForUpdates = R.string.menu_item_check_for_updates
        val MakeACopy = R.string.menu_item_make_a_copy
        val Backup = R.string.menu_item_backup
    }

    object Title {
        val Notes = R.string.title_notes
        val Trash = R.string.title_trash
        val Archive = R.string.title_archive
        val Reminders = R.string.title_reminders
        val Settings = R.string.title_settings
        val AboutApp = R.string.title_about_app
        val Labels = R.string.title_labels
        val Repeat = R.string.title_repeat
        val NewReminder = R.string.title_new_reminder
        val EditReminder = R.string.title_edit_reminder
        val Share = R.string.title_share
        val ChooseANewLockScreen = R.string.title_choose_a_new_lock_screen
        val Language = R.string.title_language
        val SetAPin = R.string.title_set_a_pin
        val ReEnterYourPin = R.string.title_re_enter_your_pin
        val SetAPassword = R.string.title_set_a_password
        val ReEnterYourPassword = R.string.title_re_enter_your_password
        val DeleteThisNote = R.string.title_delete_this_note
        val DeleteThisLabel = R.string.title_delete_this_label
        val RestoreThisNote = R.string.title_restore_this_note
        val EmptyTrash = R.string.title_empty_the_trash
        val NoteStyle = R.string.title_note_style
        val NoteSwipe = R.string.title_note_swipes
        val PrivacyPolicy = R.string.title_privacy_policy
        val Changelog = R.string.title_changelog
        val TermsAndConditions = R.string.title_terms_and_conditions
        val Reset = R.string.title_reset
        val LicenseAgreement = R.string.title_license_agreement
        val AuthRequired = R.string.title_auth_required
        val Backup = R.string.title_backup
        val Selected = R.string.title_selected
        val EnterPin = R.string.title_enter_pin
        val EnterPassword = R.string.title_enter_password
        val Unlocked = R.string.title_unlocked
        val Attention = R.string.title_attention
        val RenameLabel = R.string.title_rename_label
    }

    object Body {
        val NewNote = R.string.body_new_note
        val Reset = R.string.body_reset
        val Backup = R.string.body_backup
    }

    object Supporting {
        val PinMustBeAtLeast4Digits = R.string.supporting_pin_must_be_at_least_4_digits
        val PasswordMustBeAtLeast4Characters = R.string.supporting_password_must_be_at_least_4_characters
        val ShareNote = R.string.supporting_share_note
        val DeleteNote = R.string.supporting_delete_note
        val RestoreNote = R.string.supporting_restore_note
        val DeleteLabel = R.string.supporting_delete_label
        val EmptyTrash = R.string.supporting_empty_trash
        val WrongPin = R.string.supporting_wrong_pin
        val WrongPassword = R.string.supporting_wrong_password
    }

    object Value {
        val Never = R.string.value_never
    }

    object Label {
        val Pinned = R.string.label_pinned
        val Others = R.string.label_others
        val Upcoming = R.string.label_upcoming
        val Checklist = R.string.label_checklist
        val Reminder = R.string.label_reminder
        val Archive = R.string.label_archive
        val Security = R.string.label_security
        val Labels = R.string.label_labels
        val General = R.string.label_general
        val Gestures = R.string.label_gestures
        val Appearance = R.string.label_appearance
        val SwipeRight = R.string.label_swipe_right
        val SwipeLeft = R.string.label_swipe_left
    }

    object Lan {
        val Russian = R.string.lan_ru
        val English = R.string.lan_en
        val German = R.string.lan_de
        val French = R.string.lan_fr
        val SystemDefault = R.string.lan_system_default
    }

    object Button {
        val Done = R.string.btn_done
        val RequestPermission = R.string.btn_request_permission
        val Create = R.string.btn_create
        val Edit = R.string.btn_edit
        val Delete = R.string.btn_delete
        val Enter = R.string.btn_enter
        val Undo = R.string.btn_undo
        val Save = R.string.btn_save
        val Cancel = R.string.btn_cancel
        val Accept = R.string.btn_accept
        val Confirm = R.string.btn_confirm
        val Next = R.string.btn_next
        val Clear = R.string.btn_clear
        val Biometrics = R.string.btn_biometrics
        val Rename = R.string.btn_rename
        val Skip = R.string.btn_skip
        val Finish = R.string.btn_finish
        val Reset = R.string.btn_reset
        val TryAgain = R.string.btn_try_again
        val CreateBackup = R.string.btn_create_backup
        val RestoreFromBackup = R.string.btn_restore_from_backup
        val Restore = R.string.btn_restore
        val OpenARelease = R.string.btn_open_a_release

        fun finish(isFinished: Boolean): Int {
            return if (isFinished) Finish else Next
        }
    }

    object Action {
        val NotificationDone = R.string.action_notification_done
        val NewNote = R.string.action_new_note
        val Reminders = R.string.action_reminders
        val Archive = R.string.action_archive
        val Trash = R.string.action_trash
        val Create = R.string.action_create
        val Edit = R.string.action_edit
    }

    object Snack {
        val NoteMovedToTrash = R.string.snack_note_moved_to_trash
        val NoteArchived = R.string.snack_note_archived
        val NoteUnarchived = R.string.snack_note_unarchived

        fun noteArchived(isArchived: Boolean) = if (isArchived) NoteArchived else NoteUnarchived

        fun notePinned(isPinned: Boolean) = if (isPinned) NotePinned else NoteUnpinned

        val NoteHasBeenCopied = R.string.snack_note_has_been_copied
        val NotePinned = R.string.snack_note_pinned
        val NoteUnpinned = R.string.snack_note_unpinned
        val BackupCreated = R.string.snack_backup_created
        val StorageRestored = R.string.snack_storage_restored
        val RemindTimeIsTooLate = R.string.snack_remind_time_is_too_late
        val LabelAlreadyExist = R.string.snack_label_already_exist
        val LabelCannotBeEmpty = R.string.snack_label_cannot_be_empty
        val NothingToShare = R.string.snack_nothing_to_share
    }

    object Setting {
        val ScreenLock = R.string.stg_screen_lock
        val Language = R.string.stg_language
        val NoteStyle = R.string.stg_note_style
        val NoteSwipes = R.string.stg_note_swipes
    }

    object Switch {
        val UseBiometricTitle = R.string.switch_use_biometric_title
        val UseNoteSwipesTitle = R.string.switch_use_note_swipes_title
        val AutoBackupTitle = R.string.switch_auto_backup_title
        val AutoBackupSubtitle = R.string.switch_auto_backup_subtitle
    }

    object Notification {
        val ReminderTitle = R.string.ntf_reminder_title
        val ReminderEmptyMessage = R.string.ntf_reminder_empty_message
        val ReminderChannelDescription = R.string.ntf_reminder_channel_description
    }

    object Checkbox {
        val ClearDatabaseTitle = R.string.checkbox_clear_the_database_title
        val ClearDatabaseSubtitle = R.string.checkbox_clear_the_database_subtitle
        val ResetSettingsTitle = R.string.checkbox_reset_settings_title
        val ResetSettingsSubtitle = R.string.checkbox_reset_settings_subtitle
    }

    object OnBoarding {
        val FirstPageTitle = R.string.on_boarding_first_page_title
        val FirstPageDescription = R.string.on_boarding_first_page_description
        val SecondPageTitle = R.string.on_boarding_second_page_title
        val SecondPageDescription = R.string.on_boarding_second_page_description
        val ThirdPageTitle = R.string.on_boarding_third_page_title
        val ThirdPageDescription = R.string.on_boarding_third_page_description
        val FourthPageTitle = R.string.on_boarding_fourth_page_title
        val FourthPageDescription = R.string.on_boarding_fourth_page_description
        val FifthPageTitle = R.string.on_boarding_fifth_page_title
        val FifthPageDescription = R.string.on_boarding_fifth_page_description
    }

    object Subtitle {
        fun enabled(isEnabled: Boolean): Int {
            return if (isEnabled) Enabled else Disabled
        }

        val Enabled = R.string.subtitle_enabled
        val Disabled = R.string.subtitle_disabled
        val CurrentLockScreen = R.string.subtitle_current_lock_screen
        val UncheckedItems = R.string.subtitle_unchecked_items
        val UncheckedItem = R.string.subtitle_unchecked_item
        val Checklist = R.string.subtitle_checklist
        val Checklists = R.string.subtitle_checklists
        val DateOfCreation = R.string.subtitle_date_of_creation
        val DateOfLastEdit = R.string.subtitle_date_of_last_edit
        val Edited = R.string.subtitle_edited
        val SelectActions = R.string.subtitle_select_actions
        val LastCopy = R.string.subtitle_last_copy
        val Options = R.string.subtitle_options
    }

    object Tip {
        val AutoDeleteTrash = R.string.tip_auto_delete_trash
    }

    object Placeholder {
        val NoNotesInTrash = R.string.placeholder_no_notes_in_trash
        val Empty = R.string.placeholder_empty
        val NothingWasFound = R.string.placeholder_nothing_was_found
        val FailedToLoadData = R.string.placeholder_failed_to_load_data
        val LabelSelection = R.string.placeholder_label_selection
    }

    object Permission {
        val NotificationRationale = R.string.permission_notification_rationale
        val NotificationDefault = R.string.permission_notification_default
    }

    object Toast {
        val ComingSoon = R.string.toast_coming_soon
    }
}
