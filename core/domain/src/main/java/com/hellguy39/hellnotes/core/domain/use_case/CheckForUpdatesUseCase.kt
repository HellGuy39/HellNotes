package com.hellguy39.hellnotes.core.domain.use_case

import com.hellguy39.hellnotes.core.domain.ProjectInfoProvider
import com.hellguy39.hellnotes.core.domain.repository.remote.GithubRepositoryService
import com.hellguy39.hellnotes.core.model.Release
import com.hellguy39.hellnotes.core.model.util.Resource
import javax.inject.Inject

class CheckForUpdatesUseCase @Inject constructor(
    private val githubRepositoryService: GithubRepositoryService
) {

    suspend operator fun invoke() {

//        githubRepositoryService.getReleases().collect { resource ->
//            when(resource) {
//                is Resource.Success -> {
//                    val releases = resource.data ?: return@collect
//                    val latestRelease = releases.getLatest()
//
//                    val currentVersion = ProjectInfoProvider.appConfig.versionName
//                    val latestVersion = latestRelease.tag_name ?: return@collect
//
//                    if (compareVersions(currentVersion, latestVersion)) {
//                        // latest version
//
//                    } else {
//                        // needs update
//
//                    }
//
//                    return@collect
//                }
//                is Resource.Error -> {
//
//                    return@collect
//                }
//                else -> Unit
//            }
//        }

    }

    private fun List<Release>.getLatest(): Release {
        return sortedBy { release -> release.published_at }.first()
    }

    private fun compareVersions(currentVersion: String, latestVersion: String): Boolean {
        val currentVersionInt = currentVersion.toNumericString().toNumericString().toInt()
        val latestVersionInt = latestVersion.toNumericString().toInt()
        return currentVersionInt == latestVersionInt
    }

    private fun String.toNumericString() = filter { char -> char.isDigit() }

}