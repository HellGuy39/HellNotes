plugins {
    id("com.android.library")
    kotlin("android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.hellguy39.hellnotes.feature.lock_setup"
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Config.ComposeCompiler
    }
}

dependencies {

    implementation(project(Modules.Core.Ui))
    implementation(project(Modules.Core.Domain))
    implementation(project(Modules.Core.Model))

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
    androidTestImplementation(Libs.AndroidX.Compose.UiTestJUnit)
    debugImplementation(Libs.AndroidX.Compose.UiTooling)
    debugImplementation(Libs.AndroidX.Compose.UiTestManifest)

    implementation(Libs.Kotlin.Coroutines)

    implementation(Libs.Google.Hilt.Android)
    ksp(Libs.Google.Hilt.Compiler)
    implementation(Libs.Google.Hilt.NavigationCompose)
}