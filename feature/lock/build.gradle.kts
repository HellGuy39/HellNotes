import install.installAccompanist
import install.installAndroidCore
import install.installBaseProjectCore
import install.installCompose
import install.installCoroutines
import install.installHilt

plugins {
    id("com.android.library")
    kotlin("android")
    id(ProjectPlugin.DefaultConfig)
    id(ProjectPlugin.JavaCompile)
}

android {
    namespace = "com.hellguy39.hellnotes.feature.lock"

    buildTypes {
        release {
            isMinifyEnabled = true
        }
    }
}

installBaseProjectCore()
installAndroidCore()
installCoroutines()
installAccompanist()
installCompose()
installHilt()