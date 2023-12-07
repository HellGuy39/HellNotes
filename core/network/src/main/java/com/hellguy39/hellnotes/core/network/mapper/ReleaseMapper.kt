package com.hellguy39.hellnotes.core.network.mapper

import com.hellguy39.hellnotes.core.model.repository.remote.Release
import com.hellguy39.hellnotes.core.model.repository.remote.ReleaseAsset
import com.hellguy39.hellnotes.core.model.repository.remote.ReleaseAuthor
import com.hellguy39.hellnotes.core.model.repository.remote.ReleaseUploader
import com.hellguy39.hellnotes.core.network.dto.ReleaseAssetDto
import com.hellguy39.hellnotes.core.network.dto.ReleaseAuthorDto
import com.hellguy39.hellnotes.core.network.dto.ReleaseDto
import com.hellguy39.hellnotes.core.network.dto.ReleaseUploaderDto

fun ReleaseDto.toRelease() = Release(
    assets = assets?.map { releaseAssetDto -> releaseAssetDto?.toReleaseAsset() },
    assetsUrl = assetsUrl,
    author = author?.toReleaseAuthor(),
    body = body,
    createdAt = createdAt,
    draft = draft,
    htmlUrl = htmlUrl,
    id = id,
    name = name,
    nodeId = nodeId,
    isPreRelease = isPreRelease,
    publishedAt = publishedAt,
    tagName = tagName,
    tarballUrl = tarballUrl,
    targetCommitish = targetCommitish,
    uploadUrl = uploadUrl,
    url = url,
    zipballUrl = zipballUrl,
)

private fun ReleaseAssetDto.toReleaseAsset() = ReleaseAsset(
    browserDownloadUrl = browserDownloadUrl,
    contentType = contentType,
    createdAt = createdAt,
    downloadCount = downloadCount,
    id = id,
    label = label,
    name = name,
    nodeId = nodeId,
    size = size,
    state = state,
    updatedAt = updatedAt,
    uploader = uploader?.toReleaseUploader(),
    url = url,
)

private fun ReleaseAuthorDto.toReleaseAuthor() = ReleaseAuthor(
    avatarUrl = avatarUrl,
    eventsUrl = eventsUrl,
    followersUrl = followersUrl,
    followingUrl = followingUrl,
    gistsUrl = gistsUrl,
    gravatarId = gravatarId,
    htmlUrl = htmlUrl,
    id = id,
    login = login,
    nodeId = nodeId,
    organizationsUrl = organizationsUrl,
    receivedEventsUrl = receivedEventsUrl,
    reposUrl = reposUrl,
    siteAdmin = siteAdmin,
    starredUrl = starredUrl,
    subscriptionsUrl = subscriptionsUrl,
    type = type,
    url = url,
)

private fun ReleaseUploaderDto.toReleaseUploader() = ReleaseUploader(
    avatarUrl = avatarUrl,
    eventsUrl = eventsUrl,
    followersUrl = followersUrl,
    followingUrl = followingUrl,
    gistsUrl = gistsUrl,
    gravatarId = gravatarId,
    htmlUrl = htmlUrl,
    id = id,
    login = login,
    nodeId = nodeId,
    organizationsUrl = organizationsUrl,
    receivedEventsUrl = receivedEventsUrl,
    reposUrl = reposUrl,
    siteAdmin = siteAdmin,
    starredUrl = starredUrl,
    subscriptionsUrl = subscriptionsUrl,
    type = type,
    url = url,
)