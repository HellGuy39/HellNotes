plugins {
    id("hellnotes.android.library")
    id("hellnotes.android.library.compose")
    id("hellnotes.hilt")
}

android {
    namespace = "com.hellguy39.hellnotes.core.ui"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.domain)
    implementation(projects.core.model)
}
