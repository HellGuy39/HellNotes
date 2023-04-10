package com.hellguy39.hellnotes.feature.on_boarding.util

import androidx.annotation.DrawableRes
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First : OnBoardingPage(
        image = HellNotesIcons.DoubleStickyNote,
        title = "Добро пожаловать!",
        description = "Здесь вы можете хранить заметки, списки задач и многое другое. Давайте начнем!"
    )

    object Second : OnBoardingPage(
        image = HellNotesIcons.StickyNote,
        title = "Создайте свою первую заметку",
        description = "Начните создавать заметки прямо сейчас! Вы можете добавлять заголовки, текст, изображения и многое другое."
    )

    object Third : OnBoardingPage(
        image = HellNotesIcons.Label,
        title = "Организуйте свои заметки",
        description = "Вы можете создавать категории и теги, чтобы упорядочить свои заметки, а также использовать функцию поиска для быстрого доступа."
    )

    object Fourth : OnBoardingPage(
        image = HellNotesIcons.Notifications,
        title = "Настройте напоминания и уведомления",
        description = "Настройте уведомления, чтобы не пропустить важные задачи или события. Мы отправим вам напоминание в удобное время."
    )

    object Fifth : OnBoardingPage(
        image = HellNotesIcons.DoneAll,
        title = "Приступим?",
        description = "Теперь вы знаете ключевые функции приложения. Кажется, самое время приступать к работе."
    )

}
