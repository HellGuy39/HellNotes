import install.installCoroutines
import install.installHilt

plugins {
    id("com.android.library")
    kotlin("android")
    id(ProjectPlugin.DefaultConfig)
    id(ProjectPlugin.JavaCompile)
}
android {
    namespace = "com.hellguy39.hellnotes.core.datastore"

    buildTypes {
        release {
            isMinifyEnabled = true
        }
    }
}

installHilt()
installCoroutines()

dependencies {
    implementation(project(Modules.Core.Model))

    implementation(Libs.AndroidX.DataStore.Preferences)
}