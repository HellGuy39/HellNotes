plugins {
    id("hellnotes.android.library")
    id("hellnotes.hilt")
}

android {
    namespace = "com.hellguy39.hellnotes.core.common"

    defaultConfig {
        buildConfigField("String", "APPLICATION_ID", "\"${Configuration.applicationId}\"")
    }
}

dependencies {
    implementation(projects.core.model)
}
