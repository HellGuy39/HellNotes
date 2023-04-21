package com.hellguy39.hellnotes.core.domain.repository.remote

import com.hellguy39.hellnotes.core.model.Release
import com.hellguy39.hellnotes.core.model.util.Resource
import kotlinx.coroutines.flow.Flow

interface GithubRepositoryService {

    suspend fun getReleases(): Flow<Resource<List<Release>>>

    suspend fun getPrivacyPolicy(): Flow<Resource<String>>

    suspend fun getTermsAndConditions(): Flow<Resource<String>>

}