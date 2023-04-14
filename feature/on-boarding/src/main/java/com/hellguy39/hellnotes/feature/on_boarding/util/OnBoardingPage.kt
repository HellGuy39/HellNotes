package com.hellguy39.hellnotes.feature.on_boarding.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

sealed class OnBoardingPage(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val description: Int
) {
    object First : OnBoardingPage(
        image = HellNotesIcons.DoubleStickyNote,
        title = HellNotesStrings.OnBoarding.FirstPageTitle,
        description = HellNotesStrings.OnBoarding.FirstPageDescription
    )

    object Second : OnBoardingPage(
        image = HellNotesIcons.StickyNote,
        title = HellNotesStrings.OnBoarding.SecondPageTitle,
        description = HellNotesStrings.OnBoarding.SecondPageDescription
    )

    object Third : OnBoardingPage(
        image = HellNotesIcons.Label,
        title = HellNotesStrings.OnBoarding.ThirdPageTitle,
        description = HellNotesStrings.OnBoarding.ThirdPageDescription
    )

    object Fourth : OnBoardingPage(
        image = HellNotesIcons.Notifications,
        title = HellNotesStrings.OnBoarding.FourthPageTitle,
        description = HellNotesStrings.OnBoarding.FourthPageDescription
    )

    object Fifth : OnBoardingPage(
        image = HellNotesIcons.DoneAll,
        title = HellNotesStrings.OnBoarding.FifthPageTitle,
        description = HellNotesStrings.OnBoarding.FifthPageDescription
    )

}
