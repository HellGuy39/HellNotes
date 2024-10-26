/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hellguy39.hellnotes.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReleaseDto(
    @SerialName("assets")
    val assets: List<ReleaseAssetDto?>? = null,
    @SerialName("assets_url")
    val assetsUrl: String? = null,
    @SerialName("author")
    val author: ReleaseAuthorDto? = null,
    @SerialName("body")
    val body: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("draft")
    val draft: Boolean? = null,
    @SerialName("html_url")
    val htmlUrl: String? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("node_id")
    val nodeId: String? = null,
    @SerialName("prerelease")
    val isPreRelease: Boolean? = null,
    @SerialName("published_at")
    val publishedAt: String? = null,
    @SerialName("reactions")
    val reactions: ReactionsDto? = null,
    @SerialName("tag_name")
    val tagName: String? = null,
    @SerialName("tarball_url")
    val tarballUrl: String? = null,
    @SerialName("target_commitish")
    val targetCommitish: String? = null,
    @SerialName("upload_url")
    val uploadUrl: String? = null,
    @SerialName("url")
    val url: String? = null,
    @SerialName("zipball_url")
    val zipballUrl: String? = null,
)

@Serializable
data class ReleaseAssetDto(
    @SerialName("browser_download_url")
    val browserDownloadUrl: String?,
    @SerialName("content_type")
    val contentType: String?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("download_count")
    val downloadCount: Int?,
    @SerialName("id")
    val id: Int?,
    @SerialName("label")
    val label: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("node_id")
    val nodeId: String?,
    @SerialName("size")
    val size: Int?,
    @SerialName("state")
    val state: String?,
    @SerialName("updated_at")
    val updatedAt: String?,
    @SerialName("uploader")
    val uploader: ReleaseUploaderDto?,
    @SerialName("url")
    val url: String?,
)

@Serializable
data class ReactionsDto(
    @SerialName("+1")
    val plusOne: Int? = null,
    @SerialName("-2")
    val minusOne: Int? = null,
    @SerialName("confused")
    val confused: Int? = null,
    @SerialName("eyes")
    val eyes: Int? = null,
    @SerialName("heart")
    val heart: Int? = null,
    @SerialName("hooray")
    val hooray: Int? = null,
    @SerialName("laugh")
    val laugh: Int? = null,
    @SerialName("rocket")
    val rocket: Int? = null,
    @SerialName("total_count")
    val totalCount: Int? = null,
    @SerialName("url")
    val url: String? = null,
)

@Serializable
data class ReleaseAuthorDto(
    @SerialName("avatar_url")
    val avatarUrl: String? = null,
    @SerialName("events_url")
    val eventsUrl: String? = null,
    @SerialName("followers_url")
    val followersUrl: String? = null,
    @SerialName("following_url")
    val followingUrl: String? = null,
    @SerialName("gists_url")
    val gistsUrl: String? = null,
    @SerialName("gravatar_id")
    val gravatarId: String? = null,
    @SerialName("html_url")
    val htmlUrl: String? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("login")
    val login: String? = null,
    @SerialName("node_id")
    val nodeId: String? = null,
    @SerialName("organizations_url")
    val organizationsUrl: String? = null,
    @SerialName("received_events_url")
    val receivedEventsUrl: String? = null,
    @SerialName("repos_url")
    val reposUrl: String? = null,
    @SerialName("site_admin")
    val siteAdmin: Boolean? = null,
    @SerialName("starred_url")
    val starredUrl: String? = null,
    @SerialName("subscriptions_url")
    val subscriptionsUrl: String? = null,
    @SerialName("type")
    val type: String? = null,
    @SerialName("url")
    val url: String? = null,
)

@Serializable
data class ReleaseUploaderDto(
    @SerialName("avatar_url")
    val avatarUrl: String? = null,
    @SerialName("events_url")
    val eventsUrl: String? = null,
    @SerialName("followers_url")
    val followersUrl: String? = null,
    @SerialName("following_url")
    val followingUrl: String? = null,
    @SerialName("gists_url")
    val gistsUrl: String? = null,
    @SerialName("gravatar_id")
    val gravatarId: String? = null,
    @SerialName("html_url")
    val htmlUrl: String? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("login")
    val login: String? = null,
    @SerialName("node_id")
    val nodeId: String? = null,
    @SerialName("organizations_url")
    val organizationsUrl: String? = null,
    @SerialName("received_events_url")
    val receivedEventsUrl: String? = null,
    @SerialName("repos_url")
    val reposUrl: String? = null,
    @SerialName("site_admin")
    val siteAdmin: Boolean? = null,
    @SerialName("starred_url")
    val starredUrl: String? = null,
    @SerialName("subscriptions_url")
    val subscriptionsUrl: String? = null,
    @SerialName("type")
    val type: String? = null,
    @SerialName("url")
    val url: String? = null,
)
