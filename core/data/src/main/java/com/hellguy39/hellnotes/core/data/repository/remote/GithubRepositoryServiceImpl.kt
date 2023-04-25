package com.hellguy39.hellnotes.core.data.repository.remote

import com.hellguy39.hellnotes.core.domain.repository.remote.GithubRepositoryService
import com.hellguy39.hellnotes.core.model.Release
import com.hellguy39.hellnotes.core.model.Resource
import com.hellguy39.hellnotes.core.network.NetworkDataSource
import com.hellguy39.hellnotes.core.network.mapper.toRelease
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GithubRepositoryServiceImpl @Inject constructor(
    private val dataSource: NetworkDataSource
): GithubRepositoryService {

    override suspend fun getReleases(): Flow<Resource<List<Release>>> {
        return flow {
            emit(Resource.Loading(true))
            dataSource.getReleases(
                onSuccess = { releasesDto ->
                    val releases = releasesDto.map { releaseDto -> releaseDto.toRelease() }
                    emit(Resource.Success(releases))
                },
                onException = { message ->
                    emit(Resource.Error(message = message, data = emptyList()))
                }
            )
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getPrivacyPolicy(): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading(true))

            dataSource.getPrivacyPolicy(
                onSuccess = { privacyPolicy ->
                    emit(Resource.Success(privacyPolicy))
                },
                onException = { message ->
                    emit(Resource.Error(message = message, data = ""))
                }
            )

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getTermsAndConditions(): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading(true))

            dataSource.getTermsAndConditions(
                onSuccess = { termsAndConditions ->
                    emit(Resource.Success(termsAndConditions))
                },
                onException = { message ->
                    emit(Resource.Error(message = message, data = ""))
                }
            )

            emit(Resource.Loading(false))
        }
    }

}