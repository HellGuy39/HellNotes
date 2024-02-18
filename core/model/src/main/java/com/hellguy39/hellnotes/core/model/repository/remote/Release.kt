package com.hellguy39.hellnotes.core.model.repository.remote

data class Release(
    val assets: List<ReleaseAsset?>?,
    val assetsUrl: String?,
    val author: ReleaseAuthor?,
    val body: String?,
    val createdAt: String?,
    val draft: Boolean?,
    val htmlUrl: String?,
    val id: Int?,
    val name: String?,
    val nodeId: String?,
    val isPreRelease: Boolean?,
    val publishedAt: String?,
    val tagName: String?,
    val tarballUrl: String?,
    val targetCommitish: String?,
    val uploadUrl: String?,
    val url: String?,
    val zipballUrl: String?
)

data class ReleaseAsset(
    val browserDownloadUrl: String?,
    val contentType: String?,
    val createdAt: String?,
    val downloadCount: Int?,
    val id: Int?,
    val label: String?,
    val name: String?,
    val nodeId: String?,
    val size: Int?,
    val state: String?,
    val updatedAt: String?,
    val uploader: ReleaseUploader?,
    val url: String?
)

data class ReleaseAuthor(
    val avatarUrl: String?,
    val eventsUrl: String?,
    val followersUrl: String?,
    val followingUrl: String?,
    val gistsUrl: String?,
    val gravatarId: String?,
    val htmlUrl: String?,
    val id: Int?,
    val login: String?,
    val nodeId: String?,
    val organizationsUrl: String?,
    val receivedEventsUrl: String?,
    val reposUrl: String?,
    val siteAdmin: Boolean?,
    val starredUrl: String?,
    val subscriptionsUrl: String?,
    val type: String?,
    val url: String?
)

data class ReleaseUploader(
    val avatarUrl: String?,
    val eventsUrl: String?,
    val followersUrl: String?,
    val followingUrl: String?,
    val gistsUrl: String?,
    val gravatarId: String?,
    val htmlUrl: String?,
    val id: Int?,
    val login: String?,
    val nodeId: String?,
    val organizationsUrl: String?,
    val receivedEventsUrl: String?,
    val reposUrl: String?,
    val siteAdmin: Boolean?,
    val starredUrl: String?,
    val subscriptionsUrl: String?,
    val type: String?,
    val url: String?
)