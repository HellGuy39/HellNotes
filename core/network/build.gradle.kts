plugins {
    id("library-setup")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.hellguy39.hellnotes.core.network"
}

dependencies {

    implementation(project(Modules.Core.Model))

    implementation(Dependencies.Ktor.Core)
    implementation(Dependencies.Ktor.OkHttpEngine)
    implementation(Dependencies.Ktor.Logging)
    implementation(Dependencies.Ktor.ContentNegotiation)
    implementation(Dependencies.Ktor.JsonSerializer)

    implementation(Dependencies.Kotlin.Coroutines)

    implementation(Dependencies.Hilt.Android)
    ksp(Dependencies.Hilt.Compiler)
}
