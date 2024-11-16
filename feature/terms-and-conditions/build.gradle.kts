plugins {
    id("hellnotes.android.library")
    id("hellnotes.android.library.compose")
    id("hellnotes.hilt")
}

android {
    namespace = "com.hellguy39.hellnotes.feature.termsandconditions"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.domain)
    implementation(projects.core.ui)
    implementation(projects.core.model)

    implementation(libs.composeRichText.commonMark)
    implementation(libs.composeRichText.uiMaterial3)
    implementation(libs.composeRichText.ui)
}
