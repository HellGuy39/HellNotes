plugins {
    id("hellnotes.android.library")
    id("hellnotes.hilt")
}

android {
    namespace = "com.hellguy39.hellnotes.core.datastore"
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.bundles.datastore)
}
