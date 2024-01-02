plugins {
    id("library-setup")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.hellguy39.hellnotes.core.ui"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Configuration.composeCompiler
    }
}

dependencies {

    implementation(project(Modules.Core.Common))
    implementation(project(Modules.Core.Domain))
    implementation(project(Modules.Core.Model))

    implementation(Dependencies.AndroidX.CoreKtx)
    implementation(Dependencies.AndroidX.LifecycleKtx)
    implementation(Dependencies.AndroidX.AppCompat)

    implementation(Dependencies.Google.Material)

    implementation(Dependencies.Compose.Lifecycle)
    implementation(Dependencies.Compose.Activity)
    implementation(Dependencies.Compose.Ui)
    implementation(Dependencies.Compose.ToolingPreview)
    implementation(Dependencies.Compose.Material3)
    implementation(Dependencies.Compose.Navigation)
    implementation(Dependencies.Compose.LiveData)
    androidTestImplementation(Dependencies.Compose.UiTestJUnit)
    debugImplementation(Dependencies.Compose.UiTooling)
    debugImplementation(Dependencies.Compose.UiTestManifest)

    implementation(Dependencies.Accompanist.Permissions)

    implementation(Dependencies.Hilt.Android)
    ksp(Dependencies.Hilt.Compiler)
}
