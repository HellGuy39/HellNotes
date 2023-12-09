plugins {
    id("library-setup")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.hellguy39.hellnotes.core.common"
}

dependencies {
    implementation(project(Modules.Core.Model))

    implementation(Dependencies.AndroidX.CoreKtx)
    implementation(Dependencies.AndroidX.AppCompat)

    implementation(Dependencies.Kotlin.Coroutines)

    implementation(Dependencies.Hilt.Android)
    ksp(Dependencies.Hilt.Compiler)
}
