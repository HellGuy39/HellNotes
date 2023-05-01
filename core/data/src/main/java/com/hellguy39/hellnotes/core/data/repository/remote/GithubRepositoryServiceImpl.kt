package com.hellguy39.hellnotes.core.data.repository.remote

import com.hellguy39.hellnotes.core.domain.repository.remote.GithubRepositoryService
import com.hellguy39.hellnotes.core.model.Release
import com.hellguy39.hellnotes.core.model.Resource
import com.hellguy39.hellnotes.core.network.NetworkDataSource
import com.hellguy39.hellnotes.core.network.handleException
import com.hellguy39.hellnotes.core.network.mapper.toRelease
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GithubRepositoryServiceImpl @Inject constructor(
    private val dataSource: NetworkDataSource
): GithubRepositoryService {

    override suspend fun getReleases(): Flow<Resource<List<Release>>> {
        return flow {
            emit(Resource.Loading(true))
            val releases = dataSource.getReleases()
                .map { releaseDto -> releaseDto.toRelease() }
            emit(Resource.Success(releases))
            emit(Resource.Loading(false))
        }
            .catch { cause: Throwable ->
                emit(Resource.Error(handleException(cause)))
            }
    }

    override suspend fun getPrivacyPolicy(): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading(true))
            val privacyPolicy = dataSource.getPrivacyPolicy()
            emit(Resource.Success(privacyPolicy))
            emit(Resource.Loading(false))
        }
            .catch { cause: Throwable ->
                emit(Resource.Error(handleException(cause)))
            }
    }

    override suspend fun getTermsAndConditions(): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading(true))
            val termsAndConditions = dataSource.getTermsAndConditions()
            emit(Resource.Success(termsAndConditions))
            emit(Resource.Loading(false))
        }
            .catch { cause: Throwable ->
                emit(Resource.Error(handleException(cause)))
            }
    }

}