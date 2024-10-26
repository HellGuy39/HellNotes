plugins {
    id("hellnotes.android.library")
    id("hellnotes.hilt")
    id("hellnotes.android.room")
}

android {
    namespace = "com.hellguy39.hellnotes.core.database"
}

dependencies {
    implementation(projects.core.model)
}
