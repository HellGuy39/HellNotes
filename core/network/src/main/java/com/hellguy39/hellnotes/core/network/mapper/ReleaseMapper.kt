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
    assets_url = assets_url,
    author = author?.toReleaseAuthor(),
    body = body,
    created_at = created_at,
    draft = draft,
    html_url = html_url,
    id = id,
    name = name,
    node_id = node_id,
    prerelease = prerelease,
    published_at = published_at,
    tag_name = tag_name,
    tarball_url = tarball_url,
    target_commitish = target_commitish,
    upload_url = upload_url,
    url = url,
    zipball_url = zipball_url,
)

private fun ReleaseAssetDto.toReleaseAsset() = ReleaseAsset(
    browser_download_url = browser_download_url,
    content_type = content_type,
    created_at = created_at,
    download_count = download_count,
    id = id,
    label = label,
    name = name,
    node_id = node_id,
    size = size,
    state = state,
    updated_at = updated_at,
    uploader = uploader?.toReleaseUploader(),
    url = url,
)

private fun ReleaseAuthorDto.toReleaseAuthor() = ReleaseAuthor(
    avatar_url = avatar_url,
    events_url = events_url,
    followers_url = followers_url,
    following_url = following_url,
    gists_url = gists_url,
    gravatar_id = gravatar_id,
    html_url = html_url,
    id = id,
    login = login,
    node_id = node_id,
    organizations_url = organizations_url,
    received_events_url = received_events_url,
    repos_url = repos_url,
    site_admin = site_admin,
    starred_url = starred_url,
    subscriptions_url = subscriptions_url,
    type = type,
    url = url,
)

private fun ReleaseUploaderDto.toReleaseUploader() = ReleaseUploader(
    avatar_url = avatar_url,
    events_url = events_url,
    followers_url = followers_url,
    following_url = following_url,
    gists_url = gists_url,
    gravatar_id = gravatar_id,
    html_url = html_url,
    id = id,
    login = login,
    node_id = node_id,
    organizations_url = organizations_url,
    received_events_url = received_events_url,
    repos_url = repos_url,
    site_admin = site_admin,
    starred_url = starred_url,
    subscriptions_url = subscriptions_url,
    type = type,
    url = url,
)