plugins {
    id("hellnotes.android.library")
    id("hellnotes.hilt")
}

android {
    namespace = "com.hellguy39.hellnotes.core.domain"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.model)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.android)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
