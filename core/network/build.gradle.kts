plugins {
    id("hellnotes.android.library")
    id("hellnotes.hilt")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.hellguy39.hellnotes.core.network"
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.bundles.ktor)
}
