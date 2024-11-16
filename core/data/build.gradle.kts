plugins {
    id("hellnotes.android.library")
    id("hellnotes.hilt")
}

android {
    namespace = "com.hellguy39.hellnotes.core.data"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.domain)
    implementation(projects.core.database)
    implementation(projects.core.datastore)
    implementation(projects.core.model)
    implementation(projects.core.network)
}
