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
