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
package com.hellguy39.hellnotes.di

import com.hellguy39.hellnotes.core.domain.logger.AnalyticsLogger
import com.hellguy39.hellnotes.core.domain.repository.system.AlarmScheduler
import com.hellguy39.hellnotes.core.domain.repository.system.BiometricAuthenticator
import com.hellguy39.hellnotes.core.domain.repository.system.Downloader
import com.hellguy39.hellnotes.core.domain.repository.system.InAppNotificationManager
import com.hellguy39.hellnotes.core.domain.repository.system.LanguageHolder
import com.hellguy39.hellnotes.tools.AlarmSchedulerImpl
import com.hellguy39.hellnotes.tools.AnalyticsLoggerImpl
import com.hellguy39.hellnotes.tools.BiometricAuthenticatorImpl
import com.hellguy39.hellnotes.tools.DownloaderImpl
import com.hellguy39.hellnotes.tools.InAppNotificationManagerImpl
import com.hellguy39.hellnotes.tools.LanguageHolderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {
    @Binds
    fun bindAnalyticsLogger(i: AnalyticsLoggerImpl): AnalyticsLogger

    @Binds
    fun bindAlarmScheduler(i: AlarmSchedulerImpl): AlarmScheduler

    @Binds
    fun bindBiometricAuthenticator(i: BiometricAuthenticatorImpl): BiometricAuthenticator

    @Binds
    fun bindInAppNotificationManager(i: InAppNotificationManagerImpl): InAppNotificationManager

    @Binds
    fun bindLanguageHolder(i: LanguageHolderImpl): LanguageHolder

    @Binds
    fun bindDownloader(i: DownloaderImpl): Downloader
}
