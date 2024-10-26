package com.hellguy39.hellnotes

/**
 * This is shared between :app and :benchmarks module to provide configurations type safety.
 */
enum class HellNotesBuildType(val applicationIdSuffix: String? = null) {
    DEBUG(".debug"),
    RELEASE,
}