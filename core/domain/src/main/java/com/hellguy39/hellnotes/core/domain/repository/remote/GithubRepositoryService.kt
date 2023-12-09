package com.hellguy39.hellnotes.core.domain.repository.remote

import com.hellguy39.hellnotes.core.model.Resource
import com.hellguy39.hellnotes.core.model.repository.remote.Release
import kotlinx.coroutines.flow.Flow

interface GithubRepositoryService {
    suspend fun getReleases(): Flow<Resource<List<Release>>>

    suspend fun getPrivacyPolicy(): Flow<Resource<String>>

    suspend fun getTermsAndConditions(): Flow<Resource<String>>
}
