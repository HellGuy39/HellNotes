plugins {
    id("library-setup")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.hellguy39.hellnotes.core.domain"
}

dependencies {

    implementation(project(Modules.Core.Common))
    implementation(project(Modules.Core.Model))

    implementation(Dependencies.AndroidX.AppCompat)

    implementation(Dependencies.Kotlin.Coroutines)

    implementation(Dependencies.Hilt.Android)
    ksp(Dependencies.Hilt.Compiler)

    testImplementation(Dependencies.JUnit)
    androidTestImplementation(Dependencies.AndroidX.JUnit)
    androidTestImplementation(Dependencies.AndroidX.Espresso)
}
