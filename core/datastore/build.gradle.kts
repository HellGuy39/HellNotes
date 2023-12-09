plugins {
    id("library-setup")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.hellguy39.hellnotes.core.datastore"
}

dependencies {

    implementation(project(Modules.Core.Model))

    implementation(Dependencies.Kotlin.Coroutines)

    implementation(Dependencies.Hilt.Android)
    ksp(Dependencies.Hilt.Compiler)

    implementation(Dependencies.DataStore.Preferences)
}
