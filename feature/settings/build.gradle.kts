plugins {
    id("feature-setup")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.hellguy39.hellnotes.feature.settings"
}

dependencies {

    implementation(project(Modules.Core.Common))
    implementation(project(Modules.Core.Ui))
    implementation(project(Modules.Core.Domain))
    implementation(project(Modules.Core.Model))

    implementation(Dependencies.AndroidX.CoreKtx)
    implementation(Dependencies.AndroidX.LifecycleKtx)
    implementation(Dependencies.AndroidX.AppCompat)

    implementation(Dependencies.Compose.Lifecycle)
    implementation(Dependencies.Compose.Activity)
    implementation(Dependencies.Compose.Ui)
    implementation(Dependencies.Compose.ToolingPreview)
    implementation(Dependencies.Compose.Material3)
    implementation(Dependencies.Compose.Navigation)
    androidTestImplementation(Dependencies.Compose.UiTestJUnit)
    debugImplementation(Dependencies.Compose.UiTooling)
    debugImplementation(Dependencies.Compose.UiTestManifest)

    implementation(Dependencies.Kotlin.Coroutines)

    implementation(Dependencies.Hilt.Android)
    ksp(Dependencies.Hilt.Compiler)
    implementation(Dependencies.Hilt.NavigationCompose)
}
