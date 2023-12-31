package com.hellguy39.hellnotes.feature.onboarding.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings

sealed class OnBoardingPage(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
) {
    data object First : OnBoardingPage(
        image = AppIcons.DoubleStickyNote,
        title = AppStrings.OnBoarding.FirstPageTitle,
        description = AppStrings.OnBoarding.FirstPageDescription,
    )

    data object Second : OnBoardingPage(
        image = AppIcons.StickyNote,
        title = AppStrings.OnBoarding.SecondPageTitle,
        description = AppStrings.OnBoarding.SecondPageDescription,
    )

    data object Third : OnBoardingPage(
        image = AppIcons.Label,
        title = AppStrings.OnBoarding.ThirdPageTitle,
        description = AppStrings.OnBoarding.ThirdPageDescription,
    )

    data object Fourth : OnBoardingPage(
        image = AppIcons.Notifications,
        title = AppStrings.OnBoarding.FourthPageTitle,
        description = AppStrings.OnBoarding.FourthPageDescription,
    )

    data object Fifth : OnBoardingPage(
        image = AppIcons.DoneAll,
        title = AppStrings.OnBoarding.FifthPageTitle,
        description = AppStrings.OnBoarding.FifthPageDescription,
    )
}
