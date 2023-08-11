import install.installCoroutines
import install.installHilt

plugins {
    id("org.jetbrains.kotlin.plugin.serialization")
    id(ProjectPlugin.Library)
}

android {
    namespace = "com.hellguy39.hellnotes.core.network"
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