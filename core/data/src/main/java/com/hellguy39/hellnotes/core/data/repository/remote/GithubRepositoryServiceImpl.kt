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
package com.hellguy39.hellnotes.core.data.repository.remote

import com.hellguy39.hellnotes.core.domain.repository.remote.GithubRepositoryService
import com.hellguy39.hellnotes.core.model.Resource
import com.hellguy39.hellnotes.core.model.repository.remote.Release
import com.hellguy39.hellnotes.core.network.NetworkDataSource
import com.hellguy39.hellnotes.core.network.mapper.toRelease
import com.hellguy39.hellnotes.core.network.util.handleException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GithubRepositoryServiceImpl
    @Inject
    constructor(
        private val dataSource: NetworkDataSource,
    ) : GithubRepositoryService {
        override suspend fun getReleases(): Flow<Resource<List<Release>>> {
            return flow<Resource<List<Release>>> {
                val releases =
                    dataSource.getReleases()
                        .map { releaseDto -> releaseDto.toRelease() }
                emit(Resource.Success(releases))
            }
                .catch { cause -> emit(Resource.Error(handleException(cause))) }
                .onStart { emit(Resource.Loading(true)) }
                .onCompletion { emit(Resource.Loading(false)) }
        }

        override suspend fun getPrivacyPolicy(): Flow<Resource<String>> {
            return flow<Resource<String>> {
                val privacyPolicy = dataSource.getPrivacyPolicy()
                emit(Resource.Success(privacyPolicy))
            }
                .catch { cause -> emit(Resource.Error(handleException(cause))) }
                .onStart { emit(Resource.Loading(true)) }
                .onCompletion { emit(Resource.Loading(false)) }
        }

        override suspend fun getTermsAndConditions(): Flow<Resource<String>> {
            return flow<Resource<String>> {
                val termsAndConditions = dataSource.getTermsAndConditions()
                emit(Resource.Success(termsAndConditions))
            }
                .catch { cause -> emit(Resource.Error(handleException(cause))) }
                .onStart { emit(Resource.Loading(true)) }
                .onCompletion { emit(Resource.Loading(false)) }
        }
    }
