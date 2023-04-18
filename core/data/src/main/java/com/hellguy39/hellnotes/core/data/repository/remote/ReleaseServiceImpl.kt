package com.hellguy39.hellnotes.core.data.repository.remote

import com.hellguy39.hellnotes.core.domain.repository.remote.ReleaseService
import com.hellguy39.hellnotes.core.model.Release
import com.hellguy39.hellnotes.core.network.NetworkDataSource
import com.hellguy39.hellnotes.core.network.mapper.toRelease
import javax.inject.Inject

class ReleaseServiceImpl @Inject constructor(
    private val dataSource: NetworkDataSource
): ReleaseService {

    override suspend fun getReleases(): List<Release> {
        return dataSource.getReleases()
            .map { releaseDto -> releaseDto.toRelease() }
    }

}