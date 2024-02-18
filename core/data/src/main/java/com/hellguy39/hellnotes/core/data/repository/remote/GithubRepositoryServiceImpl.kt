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
