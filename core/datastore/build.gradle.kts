import install.installCoroutines
import install.installHilt

plugins {
    id("com.android.library")
    kotlin("android")
    id(ProjectPlugin.Library)
}

android {
    namespace = "com.hellguy39.hellnotes.core.datastore"
}

installHilt()
installCoroutines()

dependencies {
    implementation(project(Modules.Core.Model))

    implementation(Libs.DataStore.Preferences)
}