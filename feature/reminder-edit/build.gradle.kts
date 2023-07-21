plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("java-compile-plugin")
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.hellguy39.hellnotes.feature.reminder_edit"
    compileSdk =  Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Config.composeCompiler
    }
}

dependencies {

    implementation(project(Modules.Core.Ui))
    implementation(project(Modules.Core.Domain))
    implementation(project(Modules.Core.Model))
    implementation(project(Modules.Core.Common))

    implementation(Libs.AndroidX.CoreKtx)
    implementation(Libs.AndroidX.LifecycleKtx)
    implementation(Libs.AndroidX.AppCompat)

    implementation(Libs.Google.Material)

    implementation(Libs.AndroidX.Compose.Lifecycle)
    implementation(Libs.AndroidX.Compose.Activity)
    implementation(Libs.AndroidX.Compose.Ui)
    implementation(Libs.AndroidX.Compose.ToolingPreview)
    implementation(Libs.AndroidX.Compose.Material3)
    implementation(Libs.AndroidX.Compose.Navigation)

    androidTestImplementation(Libs.Testing.UiTestJUnit)
    debugImplementation(Libs.Testing.UiTooling)
    debugImplementation(Libs.Testing.UiTestManifest)

    implementation(Libs.Google.Accompanist.NavigationAnimation)
    implementation(Libs.Google.Accompanist.SystemUiController)
    implementation(Libs.Google.Accompanist.FlowLayout)
    implementation(Libs.Google.Accompanist.Permissions)

    implementation(Libs.Kotlin.Coroutines)

    implementation(Libs.Google.Hilt.Android)
    kapt(Libs.Google.Hilt.Compiler)
    implementation(Libs.Google.Hilt.NavigationCompose)

    implementation (Libs.ThirdParty.ComposeDialogs.Core)
    implementation (Libs.ThirdParty.ComposeDialogs.Calendar)
    implementation (Libs.ThirdParty.ComposeDialogs.Clock)
}