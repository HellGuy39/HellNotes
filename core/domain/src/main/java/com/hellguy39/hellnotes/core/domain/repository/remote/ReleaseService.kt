package com.hellguy39.hellnotes.core.domain.repository.remote

import com.hellguy39.hellnotes.core.model.Release

interface ReleaseService {

    suspend fun getReleases(): List<Release>

}