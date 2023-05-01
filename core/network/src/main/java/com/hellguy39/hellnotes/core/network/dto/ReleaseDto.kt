package com.hellguy39.hellnotes.core.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReleaseDto(
    val assets: List<ReleaseAssetDto?>?,
    val assets_url: String?,
    val author: ReleaseAuthorDto?,
    val body: String?,
    val created_at: String?,
    val draft: Boolean?,
    val html_url: String?,
    val id: Int?,
    val name: String?,
    val node_id: String?,
    val prerelease: Boolean?,
    val published_at: String?,
    val tag_name: String?,
    val tarball_url: String?,
    val target_commitish: String?,
    val upload_url: String?,
    val url: String?,
    val zipball_url: String?
)

@Serializable
data class ReleaseAssetDto(
    val browser_download_url: String?,
    val content_type: String?,
    val created_at: String?,
    val download_count: Int?,
    val id: Int?,
    val label: String?,
    val name: String?,
    val node_id: String?,
    val size: Int?,
    val state: String?,
    val updated_at: String?,
    val uploader: ReleaseUploaderDto?,
    val url: String?
)

@Serializable
data class ReleaseAuthorDto(
    val avatar_url: String?,
    val events_url: String?,
    val followers_url: String?,
    val following_url: String?,
    val gists_url: String?,
    val gravatar_id: String?,
    val html_url: String?,
    val id: Int?,
    val login: String?,
    val node_id: String?,
    val organizations_url: String?,
    val received_events_url: String?,
    val repos_url: String?,
    val site_admin: Boolean?,
    val starred_url: String?,
    val subscriptions_url: String?,
    val type: String?,
    val url: String?
)

@Serializable
data class ReleaseUploaderDto(
    val avatar_url: String?,
    val events_url: String?,
    val followers_url: String?,
    val following_url: String?,
    val gists_url: String?,
    val gravatar_id: String?,
    val html_url: String?,
    val id: Int?,
    val login: String?,
    val node_id: String?,
    val organizations_url: String?,
    val received_events_url: String?,
    val repos_url: String?,
    val site_admin: Boolean?,
    val starred_url: String?,
    val subscriptions_url: String?,
    val type: String?,
    val url: String?
)