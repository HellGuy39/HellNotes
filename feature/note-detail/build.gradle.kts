plugins {
    id("hellnotes.android.library")
    id("hellnotes.android.library.compose")
    id("hellnotes.hilt")
}

android {
    namespace = "com.hellguy39.hellnotes.feature.notedetail"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.domain)
    implementation(projects.core.ui)
    implementation(projects.core.model)

    implementation(libs.composeDialgs.core)
    implementation(libs.composeDialgs.calendar)
    implementation(libs.composeDialgs.clock)
}
