package com.hellguy39.hellnotes.feature.onboarding.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

sealed class OnBoardingPage(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
) {
    data object First : OnBoardingPage(
        image = HellNotesIcons.DoubleStickyNote,
        title = HellNotesStrings.OnBoarding.FirstPageTitle,
        description = HellNotesStrings.OnBoarding.FirstPageDescription,
    )

    data object Second : OnBoardingPage(
        image = HellNotesIcons.StickyNote,
        title = HellNotesStrings.OnBoarding.SecondPageTitle,
        description = HellNotesStrings.OnBoarding.SecondPageDescription,
    )

    data object Third : OnBoardingPage(
        image = HellNotesIcons.Label,
        title = HellNotesStrings.OnBoarding.ThirdPageTitle,
        description = HellNotesStrings.OnBoarding.ThirdPageDescription,
    )

    data object Fourth : OnBoardingPage(
        image = HellNotesIcons.Notifications,
        title = HellNotesStrings.OnBoarding.FourthPageTitle,
        description = HellNotesStrings.OnBoarding.FourthPageDescription,
    )

    data object Fifth : OnBoardingPage(
        image = HellNotesIcons.DoneAll,
        title = HellNotesStrings.OnBoarding.FifthPageTitle,
        description = HellNotesStrings.OnBoarding.FifthPageDescription,
    )
}
