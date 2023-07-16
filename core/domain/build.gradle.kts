import install.installCoroutines
import install.installHilt

plugins {
    id("com.android.library")
    kotlin("android")
    id(ProjectPlugin.JavaCompile)
    id(ProjectPlugin.DefaultConfig)
}

android {
    namespace = "com.hellguy39.hellnotes.core.domain"

    buildTypes {
        release {
            isMinifyEnabled = true
        }
    }
}

installCoroutines()
installHilt()

dependencies {

    implementation(project(Modules.Core.Model))

    implementation(Libs.AndroidX.AppCompat)
}