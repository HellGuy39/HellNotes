import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin ("kapt")
    id("com.google.dagger.hilt.android")
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.hellguy39.hellnotes"
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.ApplicationId
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = 1
        versionName = "1.0-rc03"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        archivesName.set("HellNotes v$versionName")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        create("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
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
    packagingOptions {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(project(Modules.Feature.Welcome))
    implementation(project(Modules.Feature.Lock))
    implementation(project(Modules.Feature.Search))
    implementation(project(Modules.Feature.Home))
    implementation(project(Modules.Feature.LabelEdit))
    implementation(project(Modules.Feature.NoteDetail))
    implementation(project(Modules.Feature.ReminderEdit))
    implementation(project(Modules.Feature.AboutApp))
    implementation(project(Modules.Feature.Settings))
    implementation(project(Modules.Feature.LockSelection))
    implementation(project(Modules.Feature.LanguageSelection))
    implementation(project(Modules.Feature.LockSetup))
    implementation(project(Modules.Feature.LabelSelection))
    implementation(project(Modules.Feature.NoteStyleEdit))
    implementation(project(Modules.Feature.NoteSwipesEdit))

    implementation(project(Modules.Core.Ui))
    implementation(project(Modules.Core.Data))
    implementation(project(Modules.Core.Common))
    implementation(project(Modules.Core.Domain))
    implementation(project(Modules.Core.Database))
    implementation(project(Modules.Core.Datastore))
    implementation(project(Modules.Core.Model))

    implementation(Libs.AndroidX.CoreKtx)
    implementation(Libs.AndroidX.LifecycleKtx)
    implementation(Libs.AndroidX.AppCompat)
    implementation(Libs.AndroidX.Biometric)
    implementation(Libs.AndroidX.SplashScreen)
    implementation(Libs.AndroidX.ProfileInstaller)

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

    testImplementation(Libs.JUnit)
    androidTestImplementation(Libs.AndroidX.JUnit)
    androidTestImplementation(Libs.AndroidX.Espresso)

    implementation(Libs.Google.Accompanist.NavigationAnimation)
    implementation(Libs.Google.Accompanist.SystemUiController)

    implementation(Libs.AndroidX.Room.RoomKtx)
    kapt(Libs.AndroidX.Room.RoomCompiler)

    implementation(Libs.Kotlin.Coroutines)

    implementation(Libs.Google.Hilt.Android)
    kapt(Libs.Google.Hilt.Compiler)
    implementation(Libs.Google.Hilt.NavigationCompose)

    implementation(Libs.SquareUp.Moshi)

    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

}