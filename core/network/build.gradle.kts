import install.installCoroutines
import install.installHilt

plugins {
    id("com.android.library")
    kotlin("android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id(ProjectPlugin.DefaultConfig)
    id(ProjectPlugin.JavaCompile)
}

android {
    namespace = "com.hellguy39.hellnotes.core.network"

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

    implementation(Libs.Ktor.Core)
    implementation(Libs.Ktor.Android)
    implementation(Libs.Ktor.Logging)
    implementation(Libs.Ktor.ContentNegotiation)
    implementation(Libs.Ktor.JsonSerializer)

}