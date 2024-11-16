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
    val zipballUrl: String?,
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
    val url: String?,
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
    val url: String?,
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
    val url: String?,
)
