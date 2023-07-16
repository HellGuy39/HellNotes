import install.installCoroutines
import install.installHilt

plugins {
    id("com.android.library")
    kotlin("android")
    id(ProjectPlugin.DefaultConfig)
    id(ProjectPlugin.JavaCompile)
}

android {
    namespace = "com.hellguy39.hellnotes.core.common"

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

    implementation(Libs.AndroidX.CoreKtx)
    implementation(Libs.AndroidX.AppCompat)

}