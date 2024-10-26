plugins {
    id("hellnotes.android.library")
    id("hellnotes.android.library.compose")
    id("hellnotes.hilt")
}

android {
    namespace = "com.hellguy39.hellnotes.feature.aboutapp"

    defaultConfig {
        buildConfigField("String", "VERSION_NAME", "\"${Configuration.versionName}\"")
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.domain)
    implementation(projects.core.ui)
    implementation(projects.core.model)
}
