package com.hellguy39.hellnotes.core.network

internal object HttpRoutes {

    private const val REPOSITORY_BASE_URL = "https://api.github.com/repos/hellguy39/hellnotes"
    private const val RAW_BASE_URL = "https://raw.githubusercontent.com/hellguy39/hellnotes"

    const val RELEASES = "$REPOSITORY_BASE_URL/releases"

    const val PRIVACY_POLICY = "$RAW_BASE_URL/master/PRIVACY_POLICY.md"
    const val TERMS_AND_CONDITIONS = "$RAW_BASE_URL/master/TERMS_AND_CONDITIONS.md"

}